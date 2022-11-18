import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CanvasPolygon {

    private JFrame frame;
    private JPanel panel;
    private Point point1 = null;
    private RasterBufferedImage img;
    private LineRasterizer lineRasterizer = new LineDrawer();
    private PolygonRasterizer polygonRasterizer = new PolygonRasterizer();
    private Polygon polygon = new Polygon();
    private Filler seedFill;
    private SolidFill solidFill;

    public CanvasPolygon(int width, int height) {


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
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    clear();
                    Point point1 = new Point(e.getX(), e.getY());
                    polygon.addPoint(point1);
                    polygonRasterizer.draw(polygon, lineRasterizer, 0XFF6600, img);
                }
                System.out.println(e.getButton());
                if (e.getButton() == MouseEvent.BUTTON3) {
                    Filler seedFill = new SeedFill(img, e.getX(), e.getY(), new SolidFill(0x03bafc),0x2f2f2f);
                    seedFill.fill();
                }
                present(panel.getGraphics());
                super.mouseReleased(e);
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                    clear();
                    polygonRasterizer.draw(polygon, lineRasterizer, 0XFF6600, img);
                    if (polygon.getPoints().size() > 0) {
                        Point point2 = new Point(e.getX(), e.getY());
                        lineRasterizer.drawLine(polygon.getPoints().get(polygon.getPoints().size() - 1), point2, 0XFF6600, img);
                    }
                present(panel.getGraphics());
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'c') {
                    clear();
                    polygon.getPoints().clear();
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
        SwingUtilities.invokeLater(() -> new CanvasPolygon(800, 600).start());
    }

}


