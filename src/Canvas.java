import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Canvas {

    private JFrame frame;
    private JPanel panel;
    private Point point1 = null;
    private RasterBufferedImage img;
    private LineRasterizer lineRasterizer = new LineDrawer();

    public Canvas(int width, int height) {

        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new RasterBufferedImage(width, height);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point1 = new Point(e.getX(), e.getY());
                System.out.println(point1);
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                clear();
                Point point2 = new Point(e.getX(), e.getY());
                lineRasterizer.drawLine(point1, point2, 0XFFFFFF, img);
                present(panel.getGraphics());
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == 'c'){
                    clear();
                    present(panel.getGraphics());
                }
            }
        });

    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        img.present(graphics);
    }

    public void draw() {
        clear();
    }

    public void start() {
        draw();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}