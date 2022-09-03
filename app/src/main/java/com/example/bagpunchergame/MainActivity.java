package com.example.bagpunchergame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity{

    SharedPreferences sp;
    private static int hits = 0;
    private static int passiveHitsTimer = 60000;
    private static int punchWorth = 1;
    private static int passivePunch = 1;
    ImageButton bagButton;
    Animation scaleUp, scaleDown, hitAnimation, punchAnimation;
    Button comboButton, achievementsButton, storeButton;
    private EditText hitVal, passiveVal, passiveTimer;

    public static int getHits(){
        return hits;
    }
    public static void decreaseHits(int x){
        hits = hits - x;

    }
    public static void increaseHitWorth(){
        punchWorth++;
    }
    public static int getPunchWorth(){
        return punchWorth;
    }

    public static void decreaseHitIncrementer(){
        passiveHitsTimer -= 5000;
    }
    public static int getHitTimer(){
        return passiveHitsTimer/100;
    }

    public static void increasePassiveHit() {
        passivePunch++;
    }
    public static int getPassiveHit(){
        return passivePunch;
    }


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shared Preference data
        sp = getSharedPreferences("upgrades", MODE_PRIVATE);
        sp.edit().putInt("hitValue", punchWorth);
        sp.edit().putInt("passiveValue", passivePunch);
        sp.edit().putInt("passiveTimer", passiveHitsTimer);



        // passive increase to total score
        TextView totalScore = findViewById(R.id.scoreBox);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                hits += passivePunch;
                runOnUiThread(() -> totalScore.setText(String.valueOf(hits)));
                Timer timer2 = new Timer();
                timer2.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (passiveHitsTimer != sp.getInt("passiveTimer", passiveHitsTimer)) {
                            passiveHitsTimer = sp.getInt("passiveTimer", passiveHitsTimer);
                        }
                    }
                }, 1000, 1000);
            }
        }, passiveHitsTimer, passiveHitsTimer);

        // Animation for the buttons on the screen
        comboButton = findViewById(R.id.upgrades);
        achievementsButton = findViewById(R.id.achievementsButton);
        storeButton = findViewById(R.id.store);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        comboButton.setOnClickListener((v) -> {
            Intent intent = new Intent(this, comboWindow.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
                comboButton.startAnimation(scaleDown);
                comboButton.startAnimation(scaleUp);
            sp.edit().putInt("totalHits", hits);
        });

        storeButton.setOnClickListener((v) -> {
            Intent intent = new Intent(this, comboWindow.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
                storeButton.startAnimation(scaleDown);
                storeButton.startAnimation(scaleUp);
        });

        achievementsButton.setOnTouchListener((v, event) -> {
            Intent intent = new Intent(this, comboWindow.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
                achievementsButton.startAnimation(scaleDown);
                achievementsButton.startAnimation(scaleUp);

            return true;
        });

        // Animation on every click of the button
        bagButton = findViewById(R.id.punchingBagHitMarker);
        hitAnimation = AnimationUtils.loadAnimation(this, R.anim.hit_animation);
        punchAnimation = AnimationUtils.loadAnimation(this,R.anim.punch_worth);


        bagButton.setOnClickListener(v -> {
            bagButton.startAnimation(hitAnimation);
            hits = hits + punchWorth;
            totalScore.setText(String.valueOf(hits));
            sp.edit().putInt("totalHits", hits);

        });

    }


}