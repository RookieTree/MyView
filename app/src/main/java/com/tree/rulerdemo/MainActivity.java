package com.tree.rulerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RecordWaveView recordWaveView;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordWaveView = findViewById(R.id.record_wave_view);
        start = findViewById(R.id.start_move);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordWaveView.locateTo(2000);
            }
        });

    }
}