package plosquad;
import javax.media.opengl.*;
import com.sun.opengl.util.*;
import com.sun.opengl.util.j2d.TextRenderer;
import javax.swing.JFrame;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.glu.GLUquadric;
/**
 *
 * @author Евгений
 */
public class PlosQuad extends GLCanvas implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

      /** Serial version UID. */
    private static final long serialVersionUID = 1L;
    private int m = 30, n = 30;
    /** The GL unit (helper class). */
    private GLU glu;

    /** The frames per second setting. */
    private int fps = 400;
    //private float cX = -10, cY = 5, cZ = -20;
    private float eX = 0, eY = 0, eZ = 0;
    private double alpha = 0;
    private double betta = 0;
    private float R = 50f;
    private float hs[][];
    /** The OpenGL animator. */
    private FPSAnimator animator;
  private float view_rotx = 1f, view_roty = (float)(225 * Math.PI / 180f), view_rotz = 0.0f;
  private int gear1, gear2, gear3;
  private float angle = 0.0f;
  private int x0, y0;
  private int zvNum = 10;
  private int prevMouseX, prevMouseY;
 // private ReadHf rmf;// = new ReadHf("C:/Documents and Settings/1/Рабочий стол/Uluru2/Новая папка (5)/S/523One/Other/hMap.bin");
  private boolean mouseRButtonDown = false;
    private boolean mouseMButtonDown;
    private long t0;
private long t1;
long t2;
private int fpsC;
private int frames;
private TextRenderer textRenderer;
private Boolean true1 = true;
private int rastCount;
private Random r = new Random();
//private Rast[] rast = new Rast[10];
ArrayList <Rast> rast = new ArrayList(10);
  

    /**
     * A new mini starter.
     * 
     * @param capabilities The GL capabilities.
     * @param width The window width.
     * @param height The window height.
     */
    public PlosQuad(GLCapabilities capabilities, int width, int height) {
        addGLEventListener(this);
    }

    /**
     * @return Some standard GL capabilities (with alpha).
     */
    private static GLCapabilities createGLCapabilities() {
        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);
       // capabilities.set
        return capabilities;
    }

    /**
     * Sets up the screen.
     * 
     * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
     */
    int fpsCount = 0;
    public void init(GLAutoDrawable drawable) {
        drawable.setGL(new DebugGL(drawable.getGL()));
        final GL gl = drawable.getGL();

        // Enable z- (depth) buffer for hidden surface removal. 
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);

        // Enable smooth shading.
        gl.glShadeModel(GL.GL_SMOOTH);

        // Define "clear" color.
        gl.glClearColor(0.4f, 0.4f, 0.4f, 1f);

        // We want a nice perspective.
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        
        drawable.addMouseListener(this);
        drawable.addMouseMotionListener(this);
        drawable.addKeyListener(this);  
        // Create GLU.
        glu = new GLU();
        // Start animator.
        animator = new FPSAnimator(this, fps);
        animator.start();
        t0 = 0;
        frames = 0;
        textRenderer = new TextRenderer(new Font("Default", Font.PLAIN, 20));
        for(int i = 0; i < zvNum; i++){
            zv[i] = new zver1();
            
        }
        if(zvNum > 0)
            zv[0].setGran(m, n);
        //char[][] s = new char[m][n];
        System.out.println(gl.glGetString(gl.GL_VENDOR));
        System.out.println(gl.glGetString(gl.GL_RENDERER));
        System.out.println(gl.glGetString(gl.GL_VERSION)); 
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                mainMatr[i][j] = 0;
        for(int i = 0; i < 10; i++){
            rast.add(new Rast(mainMatr, 2 * i, 3 * i));
        }
        rastCount = 0;
    }
    zver1 zv[] = new zver1[zvNum];
    Surface surf1 = new Surface(m, n);
    int mainMatr[][] = new int[m][n];
    /**
     * The only method that you should implement by yourself.
     * 
     * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
     */
    
    public void display(GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }
        final GL gl = drawable.getGL();
        frames++;
        t1 = System.currentTimeMillis();
        
        
        if(t1 - t2 >= 100){
            for(int i = 0; i < zvNum; i++)
                zv[i].doTurn(mainMatr);
            t2 = t1;
        }
        if (t1 - t0 >= 1000) {
            fpsC = frames;
            //System.out.println(fpsC);
            t0 = t1;
            frames = 0;
            rastCount++;
            if(rastCount > 5){
                rastCount = 0;
                int m2, n2;
                m2 = r.nextInt(m);
                n2 = r.nextInt(n);
                if(mainMatr[m2][n2] == 0){
                    rast.add(new Rast(mainMatr, m2, n2));
                }
            }
            //z1.doTurn();
            
        }
        // Clear screen.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        // Set camera.
        setCamera(gl, glu, 
                eX + (float) (R * Math.sin(view_rotx) * Math.sin(view_roty)),//x
                eY + (float) (R * Math.sin(view_rotx) * Math.cos(view_roty)),//y 
                (float)(R * Math.cos(view_rotx)),//z 
                eX, eY, eZ);
        //В цикле проходим все точки массива и соединяем их линиями
        gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);
        textRenderer.beginRendering(drawable.getWidth(), drawable.getHeight());
        textRenderer.setColor(0.1f, 1f, 0.1f, 1f);
        textRenderer.draw(String.valueOf(fpsC), 0, drawable.getHeight() - 20);
        textRenderer.endRendering();
        surf1.draw1(gl);
        for(int i = 0; i < zvNum; i++)
            zv[i].draw1(gl, t1 - t2);
        for(int i = 0; i <rast.size(); i++){
            rast.get(i).draw1(gl);
        }
  
        gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_LINE);
        surf1.draw2(gl);
        for(int i = 0; i < zvNum; i++)
            zv[i].draw2(gl);
        for(int i = 0; i < rast.size(); i++)
            rast.get(i).draw2(gl);
    }

    /**
     * Resizes the screen.
     * 
     * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable,
     *      int, int, int, int)
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL gl = drawable.getGL();
        gl.glViewport(0, 0, width, height);
    }

    /**
     * Changing devices is not supported.
     * 
     * @see javax.media.opengl.GLEventListener#displayChanged(javax.media.opengl.GLAutoDrawable,
     *      boolean, boolean)
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

    /**
     * @param gl The GL context.
     * @param glu The GL unit.
     * @param distance The distance from the screen.
     */
    private void setCamera(GL gl, GLU glu, float x, float y, float z, float ax, float ay, float az) {
        // Change to projection matrix.
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(x, y, z, ax, ay, az, 0, 0, 1);
        //glu.glu

        // Change back to model view matrix.
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * Starts the JOGL mini demo.
     * 
     * @param args Command line args.
     */
    public final static void main(String[] args) {
        GLCapabilities capabilities = createGLCapabilities();
        PlosQuad canvas = new  PlosQuad(capabilities, 1000, 800);
        JFrame frame = new JFrame("PlosQuad v1.4");
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        canvas.requestFocus();
    }
    
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {
    prevMouseX = e.getX();
    prevMouseY = e.getY();
    if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
      mouseRButtonDown = true;
    }
    if ((e.getModifiers() & e.BUTTON2_MASK) != 0) {
      mouseMButtonDown = true;
    }
  }
    
  public void mouseReleased(MouseEvent e) {
    if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
      mouseRButtonDown = false;
    }
    if ((e.getModifiers() & e.BUTTON2_MASK) != 0) {
      mouseMButtonDown = false;
    }
  }
    
  public void mouseClicked(MouseEvent e) {}
    
  // Methods required for the implementation of MouseMotionListener
  public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    if(mouseRButtonDown){
        Dimension size = e.getComponent().getSize();
    

        float thetaY = 3.14f * ( (float)(x-prevMouseX)/(float)size.width);
        float thetaX = 3.14f * ( (float)(prevMouseY-y)/(float)size.height);
    
        prevMouseX = x;
        prevMouseY = y;

        view_rotx -= thetaX;
        view_roty -= thetaY;
        if(view_rotx > 1.57) {
            view_rotx = 1.57f;
        }
        if(view_rotx< 0.02) {
            view_rotx = 0.02f;
        }
    }
    else if(mouseMButtonDown){
        R -= ( (float)(prevMouseY-y));
        if(R < 10)
            R = 10;
        prevMouseY = y; 
    }
    else{
        
        eX += (float)(prevMouseY-y) * Math.sin(view_roty) * 0.1f;
        eY += (float)(prevMouseY-y) * Math.cos(view_roty) * 0.1f;
        
        eX += (float)(x-prevMouseX) * Math.sin(view_roty + (90 * Math.PI / 180f)) * 0.1f;
        eY += (float)(x-prevMouseX) * Math.cos(view_roty + (90 * Math.PI / 180f)) * 0.1f;
        prevMouseX = x;
        prevMouseY = y;
    }
    //System.out.println(view_rotx);
  }
    
  public void mouseMoved(MouseEvent e) {}
  public void keyPressed(KeyEvent e) {
      //Здесь считываем клавиши и перемещаем точку взгляда камеры по xy.
      if(e.getKeyCode() == 87){//w
          eX -= Math.sin(view_roty);
          eY -= Math.cos(view_roty);
      }
      else if(e.getKeyCode() == 65){//a
          eX += Math.sin(view_roty + (90 * Math.PI / 180f));
          eY += Math.cos(view_roty + (90 * Math.PI / 180f));
      }
      else if(e.getKeyCode() == 83){//s
          eX += Math.sin(view_roty);
          eY += Math.cos(view_roty);
      }
      else if(e.getKeyCode() == 68){//d
          eX -= Math.sin(view_roty + (90 * Math.PI / 180f));
          eY -= Math.cos(view_roty + (90 * Math.PI / 180f));
      }
  }
  public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
     
    }
    
}
