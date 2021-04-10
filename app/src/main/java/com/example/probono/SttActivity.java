package com.example.probono;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;


public class SttActivity extends AppCompatActivity implements TextToSpeech.OnInitListener
{

    Button sttStart;
    TextView textView;
    public static Context mContext;
    private Object TextView;
    final int PERMISSION = 1;

    Intent sttIntent;
    SpeechRecognizer mRecognizer;
    TextToSpeech tts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stt);
        mContext = this;
        textView = (TextView) findViewById(R.id.sttResult);
        Button sttBtn = (Button) findViewById(R.id.sttStart);

        // 퍼미션 체크
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        // STT, TTS 로드
        speechInit();
        //Button Click Event 설정
        sttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                speechStart();
            }
        });
    }

    private void speechInit() {

        // stt 객체 생성, 초기화
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        // tts 객체 생성, 초기화
        tts = new TextToSpeech(SttActivity.this, this); }

        public void speechStart() {
        tts.speak("주문하시겠습니까?",TextToSpeech.QUEUE_FLUSH,null);
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext); // 음성인식 객체
        mRecognizer.setRecognitionListener(listener); // 음성인식 리스너 등록
        mRecognizer.startListening(sttIntent); }


        private RecognitionListener listener = new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) {
                Toast.makeText(getApplicationContext(), "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show(); }
            @Override public void onBeginningOfSpeech() { }
            @Override public void onRmsChanged(float rmsdB) { }
            @Override public void onBufferReceived(byte[] buffer) { }
            @Override public void onEndOfSpeech() { }
            @Override public void onError(int error) {
                String message; switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        message = "오디오 에러";
                        break;
                        case SpeechRecognizer.ERROR_CLIENT:
                        message = "클라이언트 에러";
                        break;
                        case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        message = "퍼미션 없음";
                        break;
                        case SpeechRecognizer.ERROR_NETWORK:
                        message = "네트워크 에러";
                        break;
                        case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        message = "네트웍 타임아웃";
                        break;
                        case SpeechRecognizer.ERROR_NO_MATCH:
                        message = "찾을 수 없음";
                        break;
                        case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        message = "RECOGNIZER가 바쁨";
                        break;
                        case SpeechRecognizer.ERROR_SERVER:
                        message = "서버가 이상함";
                        break;
                        case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        message = "말하는 시간초과";
                        break;
                        default: message = "알 수 없는 오류임";
                        break;
                } String guideStr = "에러가 발생하였습니다.";


                Toast.makeText(getApplicationContext(), guideStr + message, Toast.LENGTH_SHORT).show();
                funcVoiceOut(guideStr); }

            private void funcVoiceOut(String guideStr) {
            }

            @Override public void onResults(Bundle results)
            {
                ArrayList<String> matches =
                 results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String resultStr = "";
                for (int i = 0; i < matches.size(); i++) {
                BreakIterator txtInMsg = null;
                txtInMsg.setText(matches.get(i));
                resultStr += matches.get(i); }

                if(resultStr.length() < 1) return;
                resultStr = resultStr.replace(" ", "");
                moveActivity(resultStr);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
    };

    private void replyAnswer(String input, TextView textView){

        try {
            if (input.equals("네")) {
                textView.append("메뉴 혹은 가게이름을 말씀해주세요.\n");
                tts.speak("메뉴 혹은 가게이름을 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if (input.equals("응")) {
                textView.append("메뉴 혹은 가게이름을 말씀해주세요.\n");
                tts.speak("메뉴 혹은 가게이름을 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if (input.equals("어")) {
                textView.append("메뉴 혹은 가게이름을 말씀해주세요.\n");
                tts.speak("메뉴 혹은 가게이름을 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if (input.equals("아니")) {
                textView.append("메인화면으로 돌아가겠습니다.\n");
                tts.speak("메인화면으로 돌아가겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else{
                textView.append("죄송합니다. 다시 말씀해주시겠어요?");
                tts.speak("죄송합니다. 다시 말씀해주시겠어요?",TextToSpeech.QUEUE_FLUSH,null);
            }
        } catch(Exception e){
            toast(e.toString());
        }

    }

    private void toast(String toString) {
    }

    public void moveActivity(String resultStr) {
            if(resultStr.indexOf("주문내역") > -1) {
                String guideStr = "주문내역으로 넘어갑니다.";
                Toast.makeText(getApplicationContext(), guideStr, Toast.LENGTH_SHORT).show();
                funcVoiceOut(guideStr);
                Intent intent = new Intent(getApplicationContext(), OrderlistActivity.class);
                startActivity(intent);
            }
            else if (resultStr.indexOf("아니") > -1) {
                String guideStr = "메인화면으로 돌아가겠습니다.";
                Toast.makeText(getApplicationContext(), guideStr, Toast.LENGTH_SHORT).show();
                funcVoiceOut(guideStr);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        }


        public void funcVoiceOut(String OutMsg){
            if(OutMsg.length()<1)return;
            if(!tts.isSpeaking()) {
                tts.speak(OutMsg, TextToSpeech.QUEUE_FLUSH, null);
            }
        }

        @Override
        public void onInit(int status){
            if (status == TextToSpeech.SUCCESS) { tts.setLanguage(Locale.KOREAN);
                tts.setPitch(1); } else { Log.e("TTS", "초기화 실패"); } }

        @Override
        protected void onDestroy() {
            if (tts != null) { tts.stop();tts.shutdown(); }
            if(mRecognizer!=null){ mRecognizer.destroy();
                mRecognizer.cancel();
                mRecognizer=null; }
            super.onDestroy();
        }

}


