/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plosquad;

import javax.media.opengl.GL;

/**
 *
 * @author Евгений
 */
public class Rast {
    int cf[] = new int[2];
    public Rast(int [][] matr, int i, int j){
        cf[0] = i;
        cf[1] = j;
        matr[i][j] = 1;
    }
    public void draw1(GL gl){
        //Для плавного движения
        float[] rgba = {0.2f, 0.7f, 0.2f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
        gl.glBegin(GL.GL_QUAD_STRIP);
        gl.glVertex3i(cf[0], cf[1], 0);
        gl.glVertex3i(cf[0], cf[1], 1);
        gl.glVertex3i(cf[0] + 1, cf[1], 0);
        gl.glVertex3i(cf[0] + 1, cf[1], 1);
        gl.glVertex3i(cf[0] + 1, cf[1] + 1, 0);
        gl.glVertex3i(cf[0] + 1, cf[1] + 1, 1);
        gl.glVertex3i(cf[0], cf[1] + 1, 0);
        gl.glVertex3i(cf[0], cf[1] + 1, 1);
        gl.glVertex3i(cf[0], cf[1], 0);
        gl.glVertex3i(cf[0], cf[1], 1);
        gl.glEnd();
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3i(cf[0], cf[1], 1);
        gl.glVertex3i(cf[0] + 1, cf[1], 1);
        gl.glVertex3i(cf[0] + 1, cf[1] + 1, 1);
        gl.glVertex3i(cf[0], cf[1] + 1, 1);
        gl.glEnd();
    } 
    //Прорисовка контуров квадтаров
    public void draw2(GL gl){
        float[] rgba = {0.1f, 0.5f, 0.1f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
        gl.glBegin(GL.GL_QUAD_STRIP);
        gl.glVertex3i(cf[0], cf[1], 0);
        gl.glVertex3i(cf[0], cf[1], 1);
        gl.glVertex3i(cf[0] + 1, cf[1], 0);
        gl.glVertex3i(cf[0] + 1, cf[1], 1);
        gl.glVertex3i(cf[0] + 1, cf[1] + 1, 0);
        gl.glVertex3i(cf[0] + 1, cf[1] + 1, 1);
        gl.glVertex3i(cf[0], cf[1] + 1, 0);
        gl.glVertex3i(cf[0], cf[1] + 1, 1);
        gl.glVertex3i(cf[0], cf[1], 0);
        gl.glVertex3i(cf[0], cf[1], 1);
        gl.glEnd();
    }
    
    public int doTurn(int mm[][]){
        if(mm[cf[0]][cf[1]] == 1)
            return 0;
        else
            return 1;
    }
}
