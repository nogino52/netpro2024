import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FacesAWTMain {
    public static void main(String[] args) {
        var faceAwtMain = new FacesAWTMain();
        faceAwtMain.Show();
    }

    public void Show() {
        var faces = new FaceObj[3][3];
        for (int i = 0; i < faces.length; i++) {
            for (int j = 0; j < faces[i].length; j++) {
                faces[i][j] = new FaceObj(j, i);
            }
        }

        var faceGridFrame = new FaceGridFrame(faces, 600, 600);
        faceGridFrame.setSize(600, 600);
        faceGridFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            System.exit(0);}});
        faceGridFrame.setVisible(true);
    }

    private class FaceGridFrame extends Frame {
        private FaceObj[][] faces;
        private int width;
        private int height;

        public FaceGridFrame(FaceObj[][] faces, int width, int height) {
            super("Faces");

            this.width = width;
            this.height = height;

            this.faces = faces;
        }

        public void paint(Graphics g) {
            var faceWidth = width / faces.length;
            var faceHeight = height / faces[0].length;

            for (int i = 0; i < faces.length; i++) {
                for (int j = 0; j < faces[i].length; j++) {
                    faces[i][j].draw(
                        i * faceWidth,
                        j * faceHeight,
                        faceWidth,
                        faceHeight,
                        g
                    );
                }
            }
        }
    }

    private class FaceObj {
        private static final Color[] SKIN_COLORS = new Color[] {
            new Color(255, 235, 225), // 色白
            new Color(250, 190, 150), // 褐色
            new Color(234, 201, 255), // 異色肌
        };
        private static final Color[] EYE_COLORS = new Color[] {
            Color.BLACK,
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.ORANGE,
            Color.GRAY
        };

        private int row;
        private int col;

        public FaceObj(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void draw(int x, int y, int width, int height, Graphics g) {
            drawSkin(x, y, width, height, g);
            var center = new int[] {x + width / 2, y + height / 2};

            drawEye(center[0] - width/5, center[1], width/5, height/5, g);
            drawEye(center[0] + width/5, center[1], width/5, height/5, g);

            drawMouse(center[0], center[1]+height/3, width/3, height/5, g);
        }

        private void drawSkin(int x, int y, int width, int height, Graphics g) {
            g.setColor(SKIN_COLORS[row]);

            var center = new int[] {x + width / 2, y + height / 2};
            var radius = Math.min(width, height)*8/20;

            g.fillOval(center[0] - radius, center[1] - radius, radius * 2, radius * 2);
        }

        private void drawMouse(int centerX, int centerY, int width, int height, Graphics g) {
            switch (row) {
                case 0:
                    g.setColor(Color.PINK);
                    g.fillArc(centerX - width/2, centerY - height/2, width, height, 0, 180);
                    break;
                case 2:
                    g.setColor(Color.PINK);
                    g.fillArc(centerX - width/2, centerY - height, width, height, 180, 180);
                    break;
                default:
                    g.setColor(Color.BLACK);
                    g.drawLine(centerX - width/2, centerY, centerX + width/2, centerY);
                    break;
            }
        }

        private void drawEye(int centerX, int centerY, int width, int height, Graphics g) {
            // 白目の円
            g.setColor(Color.WHITE);
            drawOvalCenter(centerX, centerY, width, height, g);

            // 眼球の円
            var eyePosition = new int[] {centerX + width/5 * (row - 1), centerY + height/5 * (col - 1)};
            g.setColor(EYE_COLORS[(row * 3 + col) % EYE_COLORS.length]);
            var blackEyeRadius = Math.min(width, height) / 2;
            drawOvalCenter(eyePosition[0], eyePosition[1], blackEyeRadius, blackEyeRadius, g);

            // まぶたの矩形
            g.setColor(SKIN_COLORS[row]);
            var eyelidHeight = height * col / 3;
            g.fillRect(centerX - width/2, centerY - height/2, width, eyelidHeight);
        }

        private void drawOvalCenter(int centerX, int centerY, int width, int height, Graphics g) {
            g.fillOval(centerX - width/2, centerY - height/2, width, height);
        }
    }
}