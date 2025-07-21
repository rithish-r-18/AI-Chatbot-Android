package com.example.aistudybuddy;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "Authorization: Bearer sk-proj-xxxxxxxxxx", //add yours
            "Content-Type: application/json"
    })
    @POST("https://api.openai.com/v1/chat/completions")
    Call<OpenAIResponse> getAIResponse(@Body Map<String, Object> body);
}
