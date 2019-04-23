package com.chatbot.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.chatbot.R;
import com.chatbot.helper.Tools;

import java.util.List;

public class SpeechToTextActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_speech);
            findViewById(R.id.btn_listening).setOnClickListener(this);
            tvResult = findViewById(R.id.tv_speech_content);

    }

//    date=20, datetime=4, none=0, number=2, numberDecimal=8194, numberPassword=18, numberSigned=4098, phone=3, text=1, textAutoComplete=65537, textAutoCorrect=32769, textCapCharacters=4097, textCapSentences=16385, textCapWords=8193, textEmailAddress=33, textEmailSubject=49, textFilter=177, textImeMultiLine=262145, textLongMessage=81, textMultiLine=131073, textNoSuggestions=524289, textPassword=129, textPersonName=97, textPhonetic=193, textPostalAddress=113, textShortMessage=65, textUri=17, textVisiblePassword=145, textWebEditText=161, textWebEmailAddress=209, textWebPassword=225, time=36

    private static final int SPEECH_REQUEST_CODE = 100;

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }catch (ActivityNotFoundException ex)
        {
            Tools.showToast(this,"Ok google Not found in this device");
        }
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            if(tvResult.getText().toString().equals(getString(R.string.speech_placeholder)))
                tvResult.setText(spokenText);
            else
           tvResult.setText(new StringBuffer().append(tvResult.getText().toString()).append("\n").append(spokenText).toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_listening)
        {
            displaySpeechRecognizer();
        }
    }
}
