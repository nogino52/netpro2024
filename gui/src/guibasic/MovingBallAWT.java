package guibasic;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

//配列で5つのボールを動かしてみよう

public class MovingBallAWT {
    public static void main(String[] args) {
        FFrame f = new FFrame();
        f.setSize(500, 500);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.show();
    }


    static class FFrame extends Frame implements Runnable {

        Thread th;
        Ball[] balls;

        private boolean enable = true;
        private int counter = 0;

        FFrame() {
            th = new Thread(this);
            th.start();

            balls = new Ball[5];

            var random = new Random();

            for (int i = 0; i < balls.length; i++) {
                balls[i] = new Ball();
                balls[i].setPosition(random.nextInt(400), random.nextInt(400));
                balls[i].setDelta(1 + i, 1 + i);
                balls[i].setR(10 + i * 5);
                balls[i].setColor(new Color(255 - i * 50, 0, 0));
            }
        }

        public void run() {
            while (enable) {

                try {
                    Thread.sleep(10);
                    counter++;
                    if (counter >= 2000) enable = false;
                } catch (InterruptedException e) {
                }

                for (int i = 0; i < balls.length; i++) {
                    balls[i].move(counter, 100, 100, 400, 400);
                }

                repaint();  // paint()メソッドが呼び出される
            }
        }


        public void paint(Graphics g) {
            for (int i = 0; i < balls.length; i++) {
                balls[i].draw(g);
            }
        }

        // Ball というインナークラスを作る
        class Ball {


            int x;
            int y;
            int dx;
            int dy;
            int r; // 半径
            Color c = Color.RED;

            int xDir = 1;  // 1:+方向  -1: -方向
            int yDir = 1;

            void setColor(Color c) {
                this.c = c;
            }


            void move(int counter, int xMin, int yMin, int xMax, int yMax) {
                r = (int)Math.round(Math.sin(counter / 50.0) * 10) + 15;

                if ((xDir == 1) && (x + r >= xMax)) {
                    xDir = -1;
                }
                if ((xDir == -1) && (x - r <= xMin)) {
                    xDir = 1;
                }

                if ((yDir == 1) && (y + r >= yMax)) {
                    yDir = -1;
                }
                if ((yDir == -1) && (y - r <= yMin)) {
                    yDir = 1;
                }

                x += xDir == 1 ? dx : -dx;
                y += yDir == 1 ? dy : -dy;
            }


            void setPosition(int x, int y) {
                this.x = x;
                this.y = y;
            }

            void setDelta(int dx, int dy) {
                this.dx = dx;
                this.dy = dy;
            }

            void setR(int r) {
                this.r = r;
            }

            void draw(Graphics g) {
                g.setColor(c);
                g.fillOval(x - r, y - r, 2 * r, 2 * r);  // rは半径なので2倍にする
            }

        }//innner class Ball end

    }

}
