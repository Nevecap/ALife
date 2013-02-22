/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plosquad;

import javax.media.opengl.GL;

/**
 *
 * @author 1
 */
public class Surface {
    float h = 0.02f;
    int N = 100;
    int M = 100;
    float ots = 0.02f;
    public Surface(int height, int width){
        M = height + 1;
        N = width + 1;
    }
    public void draw1(GL gl){
        ots = 0.02f;
        //gl.glColor3f(0.9f, 0.8f, 0.2f);
        float[] rgba = {0.9f, 0.8f, 0.2f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
        for(int i = 0; i < M - 1; i++){
            gl.glBegin(GL.GL_QUAD_STRIP);
            for(int j = 0; j < N; j++){
                gl.glNormal3i(0, 0, 1);
                gl.glVertex3i(i, j, 0);
                gl.glVertex3i(i + 1, j, 0);
            }
            gl.glEnd();
        }
        //gl.glColor3f(0.9f, 0.8f, 0.2f);
        //float[] rgba2 = {0.9f, 0.8f, 0.2f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
        //Далее рисуем бортики
        gl.glBegin(GL.GL_QUAD_STRIP);
        for(int i = 0; i < M; i++){
            gl.glNormal3i(0, 1, 0);
            gl.glVertex3f(i, -ots, 0);
            gl.glVertex3f(i, -ots, 0.5f);
        }
        
        for(int j = 0; j < N; j++){
            gl.glNormal3i(-1, 0, 0);
            gl.glVertex3f(M - 1 + ots, j, 0);
            gl.glVertex3f(M - 1 + ots, j, 0.5f);
        }

        for(int i = M - 1; i > -1; i--){
            gl.glNormal3i(0, 1, 0);
            gl.glVertex3f(i, N - 1 + ots, 0);
            gl.glVertex3f(i,  N - 1 + ots, 0.5f);
        }
        
        for(int j = N - 1; j > -1; j--){
            gl.glNormal3i(-1, 0, 0);
            gl.glVertex3f(-ots, j, 0);
            gl.glVertex3f(-ots, j, 0.5f);
        }
        gl.glEnd();
        
    }
    public void draw2(GL gl){
        ots = 0.04f;
        //gl.glColor3f(0.9f, 0.1f, 0.1f);
        float[] rgba = {0.9f, 0.1f, 0.1f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
        for(int i = 0; i < M - 1; i++){
            gl.glBegin(GL.GL_QUAD_STRIP);
            for(int j = 0; j < N; j++){
                gl.glVertex3f(i, j, h);
                gl.glVertex3f(i + 1, j, h);
            }
            gl.glEnd();
        }  
        
        gl.glBegin(GL.GL_QUAD_STRIP);
        for(int i = 0; i < M; i++){
            gl.glVertex3f(i, -ots, 0);
            gl.glVertex3f(i, -ots, 0.5f);
        }
        
        for(int j = 0; j < N; j++){
            gl.glVertex3f(M - 1 + ots, j, 0);
            gl.glVertex3f(M - 1 + ots, j, 0.5f);
        }

        for(int i = M - 1; i > -1; i--){
            gl.glVertex3f(i, N - 1 + ots, 0);
            gl.glVertex3f(i,  N - 1 + ots, 0.5f);
        }
        
        for(int j = N - 1; j > -1; j--){
            gl.glVertex3f(-ots, j, 0);
            gl.glVertex3f(-ots, j, 0.5f);
        }
        gl.glEnd();
    }
}
