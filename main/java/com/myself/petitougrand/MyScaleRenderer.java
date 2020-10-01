package com.myself.petitougrand;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.myself.petitougrand.scale.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

// renderer utilisé pour l'échelle des formes

public class MyScaleRenderer implements GLSurfaceView.Renderer{
    private static final String TAG = "MyGLRenderer";
    private Square mSquare;
    private Triangle mTriangle;
    private Circle mCircle;
    private Diamond mDiamond;
    private Cross mCross;
    private Hexagon mHexagon;
    private Octogon mOctogon;

    // Les matrices habituelles Model/View/Projection

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];

    public float[] SPosition = {3.0f, 5.0f};
    public float[] TPosition = {-6.0f, 4f};
    public float[] CPosition = {-7.0f, -4.0f};
    public float[] DPosition = {-4.0f, -4.0f};
    public float[] KPosition = {0.0f, -4.0f};
    public float[] HPosition = {-2.0f, 4.0f};
    public float[] OPosition = {6.0f, -4.0f};

    /* Première méthode équivalente à la fonction init en OpenGLSL */

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // la couleur du fond d'écran
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        mSquare   = new Square(SPosition);
        mTriangle = new Triangle(TPosition);
        mCircle = new Circle(CPosition);
        mDiamond = new Diamond(DPosition);
        mCross = new Cross(KPosition);
        mHexagon = new Hexagon(HPosition);
        mOctogon = new Octogon(OPosition);
    }

    /* Deuxième méthode équivalente à la fonction Display */
    public void onDrawFrame(GL10 unused) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        drawSquare();
        drawDiamond();
        drawCircle();
        drawCross();
        drawHexagon();
        drawOctogon();
        drawTriangle();
    }

    public void drawDiamond() {
        float[] scratch = new float[16];
        Matrix.setIdentityM(mViewMatrix,0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix, 0, DPosition[0], DPosition[1], 0);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
        mDiamond.draw(scratch);
    }

    public void drawSquare() {
        float[] scratch = new float[16];
        Matrix.setIdentityM(mViewMatrix,0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix, 0, SPosition[0], SPosition[1], 0);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
        mSquare.draw(scratch);
    }

    public void drawCircle() {
        float[] scratch = new float[16];
        Matrix.setIdentityM(mViewMatrix,0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix, 0, CPosition[0], CPosition[1], 0);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
        mCircle.draw(scratch);
    }

    public void drawCross() {
        float[] scratch = new float[16];
        Matrix.setIdentityM(mViewMatrix,0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix, 0, KPosition[0], KPosition[1], 0);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
        mCross.draw(scratch);
    }

    public void drawHexagon() {
        float[] scratch = new float[16];
        Matrix.setIdentityM(mViewMatrix,0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix, 0, HPosition[0], HPosition[1], 0);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
        mHexagon.draw(scratch);
    }

    public void drawOctogon() {
        float[] scratch = new float[16];
        Matrix.setIdentityM(mViewMatrix,0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix, 0, OPosition[0], OPosition[1], 0);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
        mOctogon.draw(scratch);
    }

    public void drawTriangle() {
        float[] scratch = new float[16];
        Matrix.setIdentityM(mViewMatrix,0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix, 0, TPosition[0], TPosition[1], 0);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
        mTriangle.draw(scratch);
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
       // mPosition[0] = x;
        //mPosition[1] = y;

    }

    /*public float[] getPosition() {
        return mPosition;
    }*/

}
