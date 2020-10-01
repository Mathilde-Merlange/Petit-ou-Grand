package com.myself.petitougrand;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

// Menu du jeu
public class MainActivity extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
    }

    public void jeu1j(View view){
        Intent intent = new Intent(this, SoloActivity.class);
        Log.d("poug","jeu solo");
        startActivity(intent);
    }

    public void jeu2j(View view){
        Intent intent = new Intent(this, Jeu2Activity.class);
        Log.d("poug","jeu adversaire");
        startActivity(intent);
    }

    public void quitterJeu(View view){
        finish();
    }
}
