package com.example.bagpunchergame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.TimerTask;

public class speedbagWindow extends AppCompatActivity {

    int scoreAccumulated = 0;
    int playTimer = 30000;
    Button speedbag;
    ImageButton backButton;
    TextView score, directions;
    Animation sbStart;


    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speedbag_layout);

        sbStart = AnimationUtils.loadAnimation(this, R.anim.display_punch_worth);
        speedbag = findViewById(R.id.speedbagButton);
        score = findViewById(R.id.score);
        backButton = findViewById(R.id.backButton);
        directions = findViewById(R.id.pageDirections);

        directions.startAnimation(sbStart);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        speedbag.setOnTouchListener((v,e)->{
            scoreAccumulated++;
            score.setText(String.valueOf(scoreAccumulated));
            return false;
        });
    }


}
