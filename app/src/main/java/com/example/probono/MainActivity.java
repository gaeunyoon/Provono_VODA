package com.example.probono;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.telephony.VisualVoicemailService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    final int PERMISSION = 1;
    Context cThis;
    String LogTT = "[STT]";

    //음성인식용
    Intent SttIntent;
    SpeechRecognizer mRecognizer;

    //음성출력용
    TextToSpeech tts;

    //화면처리용
    Button sttBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cThis = this;

        setContentView(R.layout.activity_main);

        SttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        SttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        SttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(cThis);
        mRecognizer.setRecognitionListener(listener);

        //음성출력 생성, 리스너 초기화
        tts = new TextToSpeech(cThis, (status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }

            //Button Click Event 설정

            textView = (TextView) findViewById(R.id.sttResult);

            System.out.println("음성인식 시작");
            FuncVoiceOut("안녕하세요 보다입니다. 주문을 하시려면 주문 , 주문내역을 보시려면 주문내역이라고 말씀해주세요.");
            textView.setText("안녕하세요 보다입니다. 주문을 하시려면 주문 , 주문내역을 보시려면 주문내역이라고 말씀해주세요.\n");
            if (ContextCompat.checkSelfPermission(cThis, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);

            } else {
                try {
                    mRecognizer.startListening(SttIntent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }

        }));
    }


    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(), "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int error) {
            String message;
            switch (error) {
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
                default:
                    message = "알 수 없는 오류임";
                    break;
            }
            String guideStr = "에러가 발생하였습니다.";


            Toast.makeText(getApplicationContext(), guideStr + message, Toast.LENGTH_SHORT).show();
            FuncVoiceOut(guideStr);
        }


        @Override
        public void onResults(Bundle results) {
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);

            Log.i(LogTT, "입력값 : " + rs[0]);
            textView.setText(rs[0] + "\r\n" + textView.getText());
            FuncVoiceOrderCheck(rs[0]);

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };


    private void FuncVoiceOrderCheck(String VoiceMsg) {

        if (VoiceMsg.length() < 1) return;
        VoiceMsg = VoiceMsg.replace(" ", "");


        if (VoiceMsg.indexOf("주문") > -1) {
            FuncVoiceOut("한식 분식 치킨 디저트 중에 드시고 싶은 종류를 말씀해주세요.");
            textView.setText("한식 분식 치킨 디저트 중에 드시고 싶은 종류를 말씀해주세요.\n");
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }

        else if (VoiceMsg.indexOf("한식") > -1 ) {
            FuncVoiceOut("한식으로 넘어갑니다.");
            textView.setText("한식으로 넘어갑니다.\n");
            Intent intent = new Intent(getApplicationContext(), KoreanActivity.class);
            startActivity(intent);
        }

        else if (VoiceMsg.indexOf("분식") > -1) {
            FuncVoiceOut("분식으로 넘어갑니다.");
            textView.setText("분식으로 넘어갑니다.\n");
            Intent intent = new Intent(getApplicationContext(), TtoActivity.class);
            startActivity(intent);
        }

        else if (VoiceMsg.indexOf("치킨") > -1) {
            FuncVoiceOut("치킨으로 넘어갑니다.");
            textView.setText("치킨으로 넘어갑니다.\n");
            Intent intent = new Intent(getApplicationContext(), ChickenActivity.class);
            startActivity(intent);
        }

        else if (VoiceMsg.indexOf("디저트") > -1) {
            FuncVoiceOut("디저트로 넘어갑니다.");
            textView.setText("디저트로 넘어갑니다.\n");
            Intent intent = new Intent(getApplicationContext(), DessertActivity.class);
            startActivity(intent);
        }

        else if (VoiceMsg.indexOf("주문내역") > -1) {
            FuncVoiceOut("주문내역으로 넘어갑니다.");
            textView.setText("주문내역으로 넘어갑니다.\n");
            Intent intent = new Intent(getApplicationContext(), OrderlistActivity.class);
            startActivity(intent);

        }else{
            tts.speak("죄송합니다. 다시 말씀해주시겠어요?", TextToSpeech.QUEUE_FLUSH, null);
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }

    }

/*
    public void moveActivity(String textView) {
        if (textView.indexOf("주문내역") > -1) {
            String guideStr = "주문내역으로 넘어갑니다.";
            Toast.makeText(getApplicationContext(), guideStr, Toast.LENGTH_SHORT).show();
            FuncVoiceOut(guideStr);
            Intent intent = new Intent(getApplicationContext(), OrderlistActivity.class);
            startActivity(intent);
        } else if (textView.indexOf("아니") > -1) {
            String guideStr = "메인화면으로 돌아가겠습니다.";
            Toast.makeText(getApplicationContext(), guideStr, Toast.LENGTH_SHORT).show();
            FuncVoiceOut(guideStr);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else {
            String guideStr = "죄송합니다. 다시 말씀해주시겠어요?";
            Toast.makeText(getApplicationContext(), guideStr, Toast.LENGTH_SHORT).show();
            FuncVoiceOut(guideStr);

        }

    }

*/

    private void FuncVoiceOut(String OutMsg) {
        if (OutMsg.length() < 1) return;

        tts.setPitch(1.5f);
        tts.setSpeechRate(1.0f);
        tts.speak(OutMsg, TextToSpeech.QUEUE_FLUSH, null);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        if (mRecognizer != null) {
            mRecognizer.destroy();
            mRecognizer.cancel();
            mRecognizer = null;
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.KOREA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                sttBtn.setEnabled(true);

            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }


}