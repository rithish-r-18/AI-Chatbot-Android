AI Study Buddy 👩‍💼🧠

AI Study Buddy is a simple and smart Android chatbot app built using Java, powered by OpenAI's GPT-3.5 Turbo, and enhanced with Text-to-Speech (TTS) for voice-based responses.

🚀 Features

🧐 Ask questions to GPT-3.5 and get intelligent responses

🎤 Voice output using Android TextToSpeech

🛋️ Chat UI using RecyclerView

⏳ Rate-limit safe with button cooldown and delays

🔐 API key hidden securely via BuildConfig

💡 Tech Stack

Java (Android)

OpenAI API (gpt-3.5-turbo)

Retrofit2 for HTTP networking

RecyclerView for message layout

TextToSpeech for voice replies

![Chat UI](Pictures/Screenshot_2025-07-21-12-57-22-27_62a85d92cf38bbcfb13afa70d6f39992.jpg)

🔧 Setup Instructions

1. Clone the Repository

git clone https://github.com/your-username/AI-Study-Buddy.git
cd AI-Study-Buddy

2. Add Your OpenAI API Key

In your local.properties file:

OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxx

⚠️ DO NOT share or push this key to GitHub.

3. Sync and Run

Open project in Android Studio

Click Run or build the APK

📂 Project Structure

├── ApiClient.java
├── ApiService.java
├── ChatAdapter.java
├── ChatMessage.java
├── MainActivity.java
├── OpenAIResponse.java
├── res/layout/activity_main.xml

✉️ License

This project is open-source and available under the MIT License.

✨ Contribution

Pull requests are welcome. For major changes, please open an issue first to discuss.

🙏 Acknowledgments

OpenAI GPT API
Owner :  Rithish R

