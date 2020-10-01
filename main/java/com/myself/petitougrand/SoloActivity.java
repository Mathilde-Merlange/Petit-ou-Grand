package com.myself.petitougrand;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoloActivity extends AppCompatActivity {

    List<Integer> jeu = new ArrayList<>();
    int c, a;
    static int cardsolo;
    GLSurfaceView draws,scale;
    MySoloRenderer mRenderer;
    MyScaleRenderer sRenderer;
    TextView nb;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_solo);

        int[] cartes = {1, 1, 1, 1, 1, 1, 1, 1,
                2, 2, 2, 2, 2, 2, 2, 2, 2,
                3, 3, 3, 3, 3, 3, 3, 3, 3,
                4, 4, 4, 4, 4, 4, 4, 4, 4,
                5, 5, 5, 5, 5, 5, 5, 5, 5,
                6, 6, 6, 6, 6, 6, 6, 6, 6,
                7, 7, 7, 7, 7, 7, 7, 7};

        for (int i = 0; i < cartes.length; ++i) {
            jeu.add(cartes[i]);
        }
        Collections.shuffle(jeu);

        nb=findViewById(R.id.nb);

        draws = findViewById(R.id.dessin);;
        draws.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Création d'un context OpenGLES 2.0
        draws.setEGLContextClientVersion(3);
        // Création du renderer qui va être lié au conteneur View créé
        mRenderer = new MySoloRenderer();
        draws.setRenderer(mRenderer);
        draws.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


        scale = findViewById(R.id.scale);;
        scale.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Création d'un context OpenGLES 2.0
        scale.setEGLContextClientVersion(3);
        // Création du renderer qui va être lié au conteneur View créé
        sRenderer = new MyScaleRenderer();
        scale.setRenderer(sRenderer);
        scale.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        scale.requestRender();

        jouer();
    }

    public void jouer() {
        a = jeu.get(0);
        jeu.remove(0);
        cardsolo=a;
        nb.setText(jeu.size()+"/60");
        if (jeu.size() > 0) {
            c = jeu.get(0);
            Log.d("poug"," a : "+a);
            Log.d("poug"," c : "+c);
        }else{
            gotomenu();
        }
    }

    public void parierP(View view) {
        boolean res = (c > a) ? true : false;
        parier(res);
    }


    public void parierM(View view) {
        boolean res = (c < a) ? true : false;
        parier(res);
    }

    public void parierE(View view) {
        boolean res = (c == a) ? true : false;
        parier(res);
    }

    public void parier(boolean res){
        if (res==true) {
            draws.requestRender();
            a = c;
            jouer();
        } else {
            gotomenu();
        }
    }

    public void arreter(View view) {
        gotomenu();
    }

    public void gotomenu(){
        finish();
    }
}