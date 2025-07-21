// MainActivity.java
package com.example.aistudybuddy;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText userInput;
    Button sendBtn;
    RecyclerView chatRecycler;
    ChatAdapter chatAdapter;
    List<ChatMessage> messageList = new ArrayList<>();
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.userInput);
        sendBtn = findViewById(R.id.sendBtn);
        chatRecycler = findViewById(R.id.chatRecycler);
        chatAdapter = new ChatAdapter(messageList);
        chatRecycler.setLayoutManager(new LinearLayoutManager(this));
        chatRecycler.setAdapter(chatAdapter);

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });

        sendBtn.setOnClickListener(v -> {
            String question = userInput.getText().toString().trim();
            if (!question.isEmpty()) {
                addMessage(new ChatMessage(question, true));
                userInput.setText("");
                sendBtn.setEnabled(false); // disable send button to avoid spamming

                // Optional delay to avoid 429
                new Handler().postDelayed(() -> {
                    callOpenAI(question);
                }, 2000); // 2-second delay
            }
        });
    }

    private void callOpenAI(String prompt) {
        ApiService api = ApiClient.getClient().create(ApiService.class);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("model", "gpt-3.5-turbo");
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", prompt));
        jsonMap.put("messages", messages);

        api.getAIResponse(jsonMap).enqueue(new Callback<OpenAIResponse>() {
            @Override
            public void onResponse(Call<OpenAIResponse> call, Response<OpenAIResponse> response) {
                sendBtn.setEnabled(true); // re-enable button
                if (response.isSuccessful() && response.body() != null) {
                    String reply = response.body().getChoices().get(0).getMessage().getContent().trim();
                    addMessage(new ChatMessage(reply, false));
                    tts.speak(reply, TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.e("OPENAI_ERROR", "Code: " + response.code() + "\nBody: " + error);
                        showError("API error " + response.code());
                    } catch (Exception e) {
                        e.printStackTrace();
                        showError("API error (read failed)");
                    }
                }
            }

            @Override
            public void onFailure(Call<OpenAIResponse> call, Throwable t) {
                sendBtn.setEnabled(true); // re-enable button
                showError("Failed: " + t.getMessage());
            }
        });
    }

    private void addMessage(ChatMessage msg) {
        messageList.add(msg);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecycler.scrollToPosition(messageList.size() - 1);
    }

    private void showError(String error) {
        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
