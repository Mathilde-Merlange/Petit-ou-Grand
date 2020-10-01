package com.myself.petitougrand;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.myself.petitougrand.shapes.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer2 implements GLSurfaceView.Renderer{
    private static final String TAG = "MyGLRenderer";
    private Square   mSquare;
    private Triangle mTriangle;
    private Circle mCircle;
    private Diamond mDiamond;
    private Cross mCross;
    private Hexagon mHexagon;
    private Octogon mOctogon;
    private Attente mAttente;

    int card,v2;
    boolean p2,go;

    // Les matrices habituelles Model/View/Projection

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];

    private float[] mPosition = {0.0f, 0.0f};

    /* Première méthode équivalente à la fonction init en OpenGLSL */

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // la couleur du fond d'écran
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        mSquare   = new Square(mPosition);
        mTriangle = new Triangle(mPosition);
        mCircle = new Circle(mPosition);
        mDiamond = new Diamond(mPosition);
        mCross = new Cross(mPosition);
        mHexagon = new Hexagon(mPosition);
        mOctogon = new Octogon(mPosition);
        mAttente = new Attente(mPosition);
    }

    /* Deuxième méthode équivalente à la fonction Display */
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16]; // pour stocker une matrice

        // glClear rien de nouveau on vide le buffer de couleur et de profondeur */
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        /* on utilise une classe Matrix (similaire à glm) pour définir nos matrices P, V et M*/

        /* Pour le moment on va utiliser une projection orthographique
           donc View = Identity
         */

        /*pour positionner la caméra mais ici on n'en a pas besoin*/

        // Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.setIdentityM(mViewMatrix, 0);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        Matrix.setIdentityM(mModelMatrix, 0);

        /* Pour définir une translation on donne les paramètres de la translation
        et la matrice (ici mModelMatrix) est multipliée par la translation correspondante
         */
        Matrix.translateM(mModelMatrix, 0, mPosition[0], mPosition[1], 0);

        Log.d("Renderer", "mSquarex" + Float.toString(mPosition[0]));
        Log.d("Renderer", "mSquarey" + Float.toString(mPosition[1]));

        /* scratch est la matrice PxVxM finale */
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);

        /* on appelle la méthode dessin */
        card=Jeu2Activity.card;
        p2=Jeu2Activity.p2;
        go=Jeu2Activity.go;
        v2=Jeu2Activity.v2;

        if (go) {
            mAttente.draw(scratch);
        }else {

            if (p2 == false) {
                mAttente.draw(scratch);
            } else {
                switch (card) {
                    case 1:
                        mCircle.draw(scratch);
                        break;
                    case 2:
                        mTriangle.draw(scratch);
                        break;
                    case 3:
                        mDiamond.draw(scratch);
                        break;
                    case 4:
                        mHexagon.draw(scratch);
                        break;
                    case 5:
                        mCross.draw(scratch);
                        break;
                    case 6:
                        mSquare.draw(scratch);
                        break;
                    case 7:
                        mOctogon.draw(scratch);
                        break;
                }
            }
        }

    }


    /* équivalent au Reshape en OpenGLSL */
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        /* ici on aurait pu se passer de cette méthode et déclarer
        la projection qu'à la création de la surface !!
         */
        GLES30.glViewport(0, 0, width, height);
        Matrix.orthoM(mProjectionMatrix, 0, -10.0f, 10.0f, -10.0f, 10.0f, -1.0f, 1.0f);

    }

    /* La gestion des shaders ... */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES30.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES30.GL_FRAGMENT_SHADER)
        int shader = GLES30.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }


    /* Les méthodes nécessaires à la manipulation de la position finale du carré */
    public void setPosition(float x, float y) {
        /*mSquarePosition[0] += x;
        mSquarePosition[1] += y;*/
        mPosition[0] = x;
        mPosition[1] = y;

    }

    public float[] getPosition() {
        return mPosition;
    }

}
