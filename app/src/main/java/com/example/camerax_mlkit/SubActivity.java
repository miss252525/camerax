package com.example.camerax_mlkit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // Intentから受け取ったテキストを取得
        String receivedText = getIntent().getStringExtra("text");

        // EditTextに表示
        EditText textView = findViewById(R.id.editText);
        textView.setText(receivedText);

        findViewById(R.id.retake).setOnClickListener(
                view -> {
                    Intent intent = new Intent(SubActivity.this, CameraXLivePreviewActivity.class);
                    startActivity(intent);
                }
        );

        findViewById(R.id.stop_camera).setOnClickListener(
                view ->{
                    Intent intent = new Intent(SubActivity.this, MainActivity.class);
                    startActivity(intent);

                }
        );
    }
}