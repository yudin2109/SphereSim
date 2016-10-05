import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Drawer extends Canvas implements Runnable {
    Vector Ox;
    Vector Oy;
    Vector Oz;
    Vector r = new Vector(100, -100, 100);
    Vector v = new Vector(0, 0, 0);
    Vector a = new Vector(0, 100, 0);
    Matrix mat_x, mat_y, mat_z;
    Vector s_img;
    int radius = 20;
    double angle_y = 30;
    double angle_x = -30;
    double angle_z = 0;
    int X = 40;
    Object Circle = new Object(r, v, a, radius);
    private boolean running;

    public static int WIDTH = 1000;
    public static int HEIGHT = 800;
    public static String NAME = "Sphere Simulation";

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;


    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void run() {
        long lastTime = System.currentTimeMillis();
        long delta;

        init();

        while(running) {
            delta = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            simulate(delta);
            render();
            update(delta);
        }
    }

    public void init() {
        addKeyListener(new KeyInputHandler());
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        mat_x = new Matrix(1, 0, 0, 0, Math.cos(angle_x * Math.PI / 180), -Math.sin(angle_x * Math.PI / 180), 0, Math.sin(angle_x * Math.PI / 180), Math.cos(angle_x * Math.PI / 180));
        mat_y = new Matrix(Math.cos(angle_y * Math.PI / 180), 0, Math.sin(angle_y * Math.PI / 180), 0, 1, 0, -Math.sin(angle_y * Math.PI / 180), 0, Math.cos(angle_y * Math.PI / 180));
        mat_z = new Matrix(Math.cos(angle_z * Math.PI / 180), -Math.sin(angle_z * Math.PI / 180), 0, Math.sin(angle_z * Math.PI / 180), Math.cos(angle_z * Math.PI / 180), 0, 0, 0, 1);
        s_img = mat_z._mul(mat_x._mul(mat_y._mul(Circle.r)));
        Ox = mat_z._mul(mat_x._mul(mat_y._mul(new Vector(300, 0, 0))));
        Oy = mat_z._mul(mat_x._mul(mat_y._mul(new Vector(0, -300, 0))));
        Oz = mat_z._mul(mat_x._mul(mat_y._mul(new Vector(0, 0, 300))));
        g.setColor(Color.LIGHT_GRAY);
        int[] x_s = {WIDTH / 2, (int)Ox.x + WIDTH / 2, (int)Ox.x + WIDTH / 2 + (int)Oz.x, (int)Oz.x + WIDTH / 2};
        int[] y_s = {HEIGHT / 2, (int)Ox.y + HEIGHT / 2, (int)Ox.y + (int)Oz.y + HEIGHT / 2, (int)Oz.y + HEIGHT / 2};
        g.fillPolygon(x_s, y_s, 4);
        g.setColor(Color.BLACK);
        g.drawLine(WIDTH/ 2, HEIGHT / 2, (int)Ox.x + WIDTH / 2, (int)Ox.y + HEIGHT / 2);
        g.drawLine(WIDTH/ 2, HEIGHT / 2, (int)Oy.x + WIDTH / 2, (int)Oy.y + HEIGHT / 2);
        g.drawLine(WIDTH/ 2, HEIGHT / 2, (int)Oz.x + WIDTH / 2, (int)Oz.y + HEIGHT / 2);
        g.drawString("X", (int)Ox.x + WIDTH / 2, (int)Ox.y + HEIGHT / 2);
        g.drawString("Y", (int)Oy.x + WIDTH / 2, (int)Oy.y + HEIGHT / 2);
        g.drawString("Z", (int)Oz.x + WIDTH / 2, (int)Oz.y + HEIGHT / 2);
        g.drawString("X angle:" + (int)angle_x, 850 , 350);
        g.drawString("Y angle:" + (int)angle_y, 850 , 380);
        g.drawString("x coord:" + Circle.r.x, 850 , 410);
        g.drawString("y coord:" + (-(int)Circle.r.y), 850 , 440);
        g.drawString("z coord:" + Circle.r.z, 850 , 470);
        g.drawString("x speed:" + Circle.v.x, 850 , 500);
        g.drawString("y speed:" + (-(int)Circle.v.y), 850 , 530);
        g.drawString("z speed:" + Circle.v.z, 850 , 560);

        g.fillOval((int)s_img.x + WIDTH / 2, (int)s_img.y + HEIGHT / 2, (int)Circle.rad, (int)Circle.rad);
        g.dispose();
        bs.show();
    }
    public void simulate(double delta){
        Circle.v = Circle.v._add(Circle.a._mul(delta / 500));
        Circle.r = Circle.r._add(Circle.v._mul(delta / 500));

        if ((Circle.r.y + Circle.rad> 0) && (Circle.v.y > 0)){
            Circle.v.y *= -1;
        }
    }
    public void update(long delta) {
        if (leftPressed == true) {
            angle_y -= 0.3;
        }
        if (rightPressed == true) {
            angle_y += 0.3;
        }
        if (upPressed == true) {
            angle_x += 0.3;
        }
        if (downPressed == true) {
            angle_x -= 0.3;
        }

    }


    public static void main(String[] args) {
        Drawer sim = new Drawer();
        sim.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JFrame frame = new JFrame(Drawer.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(sim, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        sim.start();
    }

    private class KeyInputHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
        }
    }
}