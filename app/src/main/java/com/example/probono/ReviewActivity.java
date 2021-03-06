package com.example.probono;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

import javax.xml.transform.Result;

public class ReviewActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    TextView number;
    TextView review1;
    TextView review2;
    TextView stt;


    final int PERMISSION = 1;
    Context cThis;
    String LogTT = "[STT]";

    //음성인식용
    Intent SttIntent;
    SpeechRecognizer mRecognizer;

    //음성출력용
    TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cThis = this;
        setContentView(R.layout.activity_review);


        //review

        number =(TextView) findViewById(R.id.number);
        review1 = (TextView) findViewById(R.id.review1);
        review2 = (TextView) findViewById(R.id.review2);
        stt = (TextView) findViewById(R.id.stt);
 /*
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMain();
            }
        });
        Intent intent = getIntent();
        processIntent(intent);

         */

                //stt
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




            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    FuncVoiceOut("해당 가게의 별점 1점부터 5점 중에 말씀해주세요.");
                    stt.setText("해당 가게의 별점 1점부터 5점 중에 말씀해주세요.\n");
                    if (ContextCompat.checkSelfPermission(cThis, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ReviewActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);

                    } else {
                        try {
                            mRecognizer.startListening(SttIntent);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }

                    }
                }


            },1000);

        }));
    }
/*
 //review
    private void processIntent(Intent intent){
        if(intent != null){
            float rating = intent.getFloatExtra("rating",0.0f);
            number.setText((int) rating);
        }
    }

    public void returnToMain(){
        String contents=number.getText().toString();
        CharSequence ratingbarupdate = number.getText();
        Intent intent = new Intent();
        intent.putExtra("contents",contents);
        intent.putExtra("ratingbarupdate",ratingbarupdate);

        setResult(RESULT_OK,intent);
        finish();
    }
*/

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
            review1.setText(rs[0] + "\r\n" + review1.getText());
            review2.setText(rs[0] + "\r\n" + review2.getText());
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


        if (VoiceMsg.indexOf("1점") > -1) {
            number.setText("1\n");
            FuncVoiceOut("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.");
            stt.setText("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.\n");
            //final CharSequence numstars=number.getText();
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }

        else if (VoiceMsg.indexOf("2점") > -1) {
            number.setText("2\n");
            FuncVoiceOut("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.");
            stt.setText("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.\n");
           // final CharSequence numstars=number.getText();
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }

        else if (VoiceMsg.indexOf("3점") > -1) {
            number.setText("3\n");
            FuncVoiceOut("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.");
            stt.setText("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.\n");
            //final CharSequence numstars=number.getText();
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }

        else if (VoiceMsg.indexOf("4점") > -1) {
            number.setText("4\n");
            FuncVoiceOut("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.");
            stt.setText("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.\n");
           // final CharSequence numstars=number.getText();
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }

        else if (VoiceMsg.indexOf("5점") > -1) {
            number.setText("5\n");
            FuncVoiceOut("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.");
            stt.setText("해당 가게 음식이 맛있었다면 네 아니라면 아니요 라고 말씀해주세요.\n");
           // final CharSequence numstars=number.getText();
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }

        else if (VoiceMsg.indexOf("저장") > -1) {
            FuncVoiceOut("리뷰 내용을 저장합니다.");
            stt.setText("리뷰 내용을 저장합니다.\n");
            //returnToMain();
            Intent intent = new Intent(getApplicationContext(), RelistActivity.class);
            startActivity(intent);
        }
        else if (VoiceMsg.indexOf("네") > -1) {
            review1.setText("맛있어요.\n");
            FuncVoiceOut("해당 가게에 만족하셨다면 만족 아니라면 불만족이라고 말씀해주세요.");
            stt.setText("해당 가게에 만족하셨다면 만족 아니라면 불만족이라고 말씀해주세요.\n");
            //returnToMain();
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);

        }
        else if (VoiceMsg.indexOf("아니요") > -1) {
            review1.setText("맛없어요.\n");
            FuncVoiceOut("해당 가게에 만족하셨다면 만족 아니라면 불만족이라고 말씀해주세요.");
            stt.setText("해당 가게에 만족하셨다면 만족 아니라면 불만족이라고 말씀해주세요.\n");
            //returnToMain();
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }
        else if (VoiceMsg.indexOf("만족") > -1) {
            review2.setText("만족해요.\n");
            FuncVoiceOut("리뷰를 저장하시겠으면 저장이라고 말씀해주세요.");
            stt.setText("리뷰를 저장하시겠으면 저장이라고 말씀해주세요.\n");
            //returnToMain();
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }
        else if (VoiceMsg.indexOf("불만족") > -1) {
            review2.setText("불만족해요.\n");
            FuncVoiceOut("리뷰를 저장하시겠으면 저장이라고 말씀해주세요.");
            stt.setText("리뷰를 저장하시겠으면 저장이라고 말씀해주세요.\n");
            //returnToMain();
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(SttIntent);
        }
        else {
           FuncVoiceOut("죄송합니다. 다시 말씀해주시겠어요?");
           stt.setText("죄송합니다. 다시 말씀해주시겠어요?\n");
           mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
           mRecognizer.setRecognitionListener(listener);
           mRecognizer.startListening(SttIntent);
        }
    }


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
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }


}