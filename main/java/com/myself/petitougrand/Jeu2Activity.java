package com.myself.petitougrand;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jeu2Activity extends AppCompatActivity {

    List<Integer> jeu = new ArrayList<>();
    List<Integer> jeu1 = new ArrayList<>();
    List<Integer> jeu2 = new ArrayList<>();
    List<Integer> def = new ArrayList<>();
    List<Integer> hold = new ArrayList<>(); // pile des cartes gagnées
    int a1,c1,a2,c2;
    static int card,pioche;  //card = carte à afficher par le renderer;  pioche=carte à afficher par la défausse
    GLSurfaceView drawing1,drawing2,defausse,echelle;
    MyRenderer1 mRenderer1;
    MyRenderer2 mRenderer2;
    MyRenderer3 mRenderer3;
    MyScaleRenderer scaleRenderer;
    TextView text1,text2;
    static boolean p1,p2;// qui joue?
    static boolean go; // le jeu est-il terminé?
    static int v1,v2; // quel joueur gagne?

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_jeu2);

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

        pioche = jeu.get(0);
        jeu.remove(0);

        for (int i = 0; i < jeu.size(); i++) {
            if (i % 2 == 1) {
                jeu1.add(jeu.get(i));
            } else {
                jeu2.add(jeu.get(i));
            }
        }
        go = false;

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);

        drawing1 = findViewById(R.id.j1);
        drawing1.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        drawing1.setEGLContextClientVersion(3);
        mRenderer1 = new MyRenderer1();
        drawing1.setRenderer(mRenderer1);
        drawing1.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        drawing2 = findViewById(R.id.j2);
        drawing2.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        drawing2.setEGLContextClientVersion(3);
        mRenderer2 = new MyRenderer2();
        drawing2.setRenderer(mRenderer2);
        drawing2.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        defausse = findViewById(R.id.def);
        defausse.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        defausse.setEGLContextClientVersion(3);
        mRenderer3 = new MyRenderer3();
        defausse.setRenderer(mRenderer3);
        defausse.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        
        echelle = findViewById(R.id.echelle);
        echelle.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        echelle.setEGLContextClientVersion(3);
        scaleRenderer = new MyScaleRenderer();
        echelle.setRenderer(scaleRenderer);
        echelle.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        
        echelle.requestRender();

        p1 = true;
        p2 = false;
        defausse.requestRender();

        // DEBUG
        //a1 = carte affichée du joueur 1
        //a2 = carte affichée du joueur 2
        //c1 = carte pariée par le joueur 1
        //c2 = carte pariée par le joueur 2


        if (p1) {
            c1 = jeu1.get(0);
            Log.d("poug", " a1 : " + pioche);
            Log.d("poug", " c1 : " + c1);
        } else {
            c2 = jeu2.get(0);
            Log.d("poug", " a2 : " + pioche);
            Log.d("poug", " c2 : " + c2);
        }
    }

    public void jouer() {
        if (p1) {
            if (pioche==0){a1 = jeu1.get(0);
            jeu1.remove(0);}
            else{a1=def.get(def.size()-1);}
            text1.setText("J1 : \n"+jeu1.size()+" / 30 cartes \n restantes");
            hold.add(a1);
            card = a1;
            if (jeu1.size() > 0) {
                c1 = jeu1.get(0);
                Log.d("poug", " a1 : " + a1);
                Log.d("poug", " c1 : " + c1);
            } else {
                ender();
            }
        } else {
            if (pioche==0){a2 = jeu2.get(0);
                jeu2.remove(0);}
            else{a2=def.get(def.size()-1);}
            text2.setText("J2 : \n"+jeu2.size()+" / 30 cartes \n restantes");
            hold.add(a2);
            card = a2;
            if (jeu2.size() > 0) {
                c2 = jeu2.get(0);
                Log.d("poug", " a2 : " + a2);
                Log.d("poug", " c2 : " + c2);
            } else {
                ender();
            }
        }
        drawing1.requestRender();
        drawing2.requestRender();

    }

    public void ender(){ // fin du jeu
        if(jeu1.size()==0){
            v1=100;
            v2=10;
        }else{
            v1=10;
            v2=100;
        }
        card=0;
        go=true;
        drawing1.requestRender();
        drawing2.requestRender();
        if(v1==100){
            text1.setText("J1 a \ngagné");
            text2.setText("J2 a \nperdu");
        }
        else{
            text1.setText("J1 a \nperdu");
            text2.setText("J2 a \ngagné");
        }
    }

    public void arreter(View view){ // joueur force la fin de son tour
        for (int i = 0; i < hold.size(); i++) {
            def.add(hold.get(0));
            hold.remove(0);
        }
        if (p1) {
            jeu1.add(c1);
            p1=false;
            p2=true;
            def.add(a1);
        }
        else{
            jeu2.add(c2);
            p1=true;
            p2=false;
            def.add(a2);
        }
        pioche=def.get(def.size()-1);
        defausse.requestRender();
        jouer();
    }

    public void parierP(View view){
        if(pioche!=0){
            if(p1){
                a1=pioche;
                c1=jeu1.get(0);
            }else{
                a2=pioche;
                c2=jeu2.get(0);
            }
            pioche=0;
        }
        if(p1){
            boolean res = (c1 > a1) ? true : false;
            parier(res);
        }else{
             boolean res = (c2 > a2) ? true : false;
            parier(res);
        }
    }

    public void parierM(View view){
        if(pioche!=0){
            if(p1){
                a1=pioche;
                c1=jeu1.get(0);
            }else{
                a2=pioche;
                c2=jeu2.get(0);
            }
            pioche=0;
        }
        if(p1){
            boolean res = (c1 < a1) ? true : false;
            parier(res);
        }else{
            boolean res = (c2 < a2) ? true : false;
            parier(res);
        }
    }

    public void parierE(View view){
        if(pioche!=0){
            if(p1){
                a1=pioche;
                c1=jeu1.get(0);
            }else{
                a2=pioche;
                c2=jeu2.get(0);
            }
            pioche=0;
        }
        if(p1){
            boolean res = (c1 == a1) ? true : false;
            parier(res);
        }else{
            boolean res = (c2 == a2) ? true : false;
            parier(res);
        }
    }

    public void parier(boolean res) {
        if (res == true) {
            if (p1) {
                drawing1.requestRender();
                a1 = c1;
            } else {
                drawing2.requestRender();
                a2 = c2;
            }
            jouer();
        } else {
            if (p1) {
                for (int i = 0; i < hold.size(); i++) {
                    jeu1.add(hold.get(0));
                    hold.remove(0);
                }
            } else {
                for (int i = 0; i < hold.size(); i++) {
                    jeu2.add(hold.get(0));
                    hold.remove(0);
                }
            }
            p1 = !p1;
            p2 = !p2;
            jouer();
        }
    }

        public void gotomenu(View view){
        finish();
    }
}
