/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plosquad;

import java.util.Random;
import javax.media.opengl.GL;

public class zver1 {
    static int rg = 1, lg = 1;
    public void setGran(int i, int j){
        rg = j;
        lg = i;
    }
    Random r = new Random();
    int[] c = {0 , 0};
    int[] co = {0, 0};
    float cf[] = {0, 0};
    int oldHod = 1;
    //Прорисовка заполненных квадратов
    public void draw1(GL gl, long ms){
        //c[0] = ((cf[0] - co[0]) * (int)ms / 500) + co[0];
        //c[1] = ((cf[1] - co[1]) * (int)ms / 500) + co[1];
        //Для плавного движения
        computeCoord(ms);
        gl.glColor3f(0.5f, 0.5f, 0.7f);
        gl.glBegin(GL.GL_QUAD_STRIP);
        gl.glVertex3f(cf[0], cf[1], 0);
        gl.glVertex3f(cf[0], cf[1], 1);
        gl.glVertex3f(cf[0] + 1, cf[1], 0);
        gl.glVertex3f(cf[0] + 1, cf[1], 1);
        gl.glVertex3f(cf[0] + 1, cf[1] + 1, 0);
        gl.glVertex3f(cf[0] + 1, cf[1] + 1, 1);
        gl.glVertex3f(cf[0], cf[1] + 1, 0);
        gl.glVertex3f(cf[0], cf[1] + 1, 1);
        gl.glVertex3f(cf[0], cf[1], 0);
        gl.glVertex3f(cf[0], cf[1], 1);
        gl.glEnd();
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3f(cf[0], cf[1], 1);
        gl.glVertex3f(cf[0] + 1, cf[1], 1);
        gl.glVertex3f(cf[0] + 1, cf[1] + 1, 1);
        gl.glVertex3f(cf[0], cf[1] + 1, 1);
        gl.glEnd();
    } 
    //Прорисовка контуров квадтаров
    public void draw2(GL gl){
        gl.glColor3f(0.4f, 0.4f, 0.5f);
        gl.glBegin(GL.GL_QUAD_STRIP);
        gl.glVertex3f(cf[0], cf[1], 0);
        gl.glVertex3f(cf[0], cf[1], 1);
        gl.glVertex3f(cf[0] + 1, cf[1], 0);
        gl.glVertex3f(cf[0] + 1, cf[1], 1);
        gl.glVertex3f(cf[0] + 1, cf[1] + 1, 0);
        gl.glVertex3f(cf[0] + 1, cf[1] + 1, 1);
        gl.glVertex3f(cf[0], cf[1] + 1, 0);
        gl.glVertex3f(cf[0], cf[1] + 1, 1);
        gl.glVertex3f(cf[0], cf[1], 0);
        gl.glVertex3f(cf[0], cf[1], 1);
        gl.glEnd();
    }
    public void doTurn(int mm[][]){
        Boolean out = false;
        //Если нет возможности хода, остаёмся на месте
        if(!canMove(mm, c[0], c[1]))
            return;
        //co - предыдущие координаты, c - текущие
        co[0] = c[0];
        co[1] = c[1];
        while(!out){
            int hod;
            if(r.nextInt(5) % 2 == 0)
                hod = oldHod;
            else 
                hod = r.nextInt(4);
            if(hod == 0 && canMoveN(mm, hod)){
                c[0]++;
                out = true;
            }
            else if(hod == 1 && canMoveN(mm, hod)){
                c[1]++;
                out = true;
            }
            else if(hod == 2 && canMoveN(mm, hod)){
                c[0]--;
                out = true;
            }
            else if(hod == 3 && canMoveN(mm, hod)){
                c[1]--;
                out = true;
            }
            oldHod = hod;
        }
        
        mm[co[0]][co[1]] = 0;
        mm[c[0]][c[1]] = 1;
    }
    protected void computeCoord(long ms){
        cf[0] = (c[0] - co[0]) * (ms / 100f) + co[0];
        cf[1] = (c[1] - co[1]) * (ms / 100f) + co[1];
    }
    //Проверка возможности хода в определнном направлении
    private Boolean canMoveN(int mm[][], int r){
        if(r == 0 && c[0] < lg - 1){
            return checkMatr(mm, c[0] + 1, c[1]);
        }
        else if(r == 1 && c[1] < rg - 1){
            return checkMatr(mm, c[0], c[1] + 1);
        }
        else if(r == 2 && c[0] > 0){
            return checkMatr(mm, c[0] - 1, c[1]);
        }
        else if(r == 3 && c[1] > 0){
            return checkMatr(mm, c[0], c[1] - 1);
        }
        return false;
    }
    //Следующая функция проверяет, может животное двигаться вообще
    private Boolean canMove(int mm[][], int i, int j){
        Boolean out = false;
        for(int k = 0; k < 4; k++){
            if(canMoveN(mm, k))
                out = true;
        }
        return out;
    }
    //проверка доступности ячейки матрицы для перехода
    private Boolean checkMatr(int mm[][], int i, int j){
        if(mm[i][j] == 0)
            return true;
        else
            return false;
    }
}
