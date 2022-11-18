import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScanLine implements Filler {
    private final LineRasterizer lineRasterizer;
    private final PolygonRasterizer polygonRasterizer;
    private final Polygon polygon;
    private final RasterBufferedImage img;
    private final PatternFill fillColor;
    private final int outlineColor;

    public ScanLine(LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer, Polygon polygon, RasterBufferedImage img, PatternFill fillColor, int outlineColor) {
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        this.polygon = polygon;
        this.img = img;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
    }

    @Override
    public void fill() {
        scanLine();
    }

    private void scanLine() {
        // init seznamu hran
        List<Edge> edges = new ArrayList<>();

        // projdu pointy a vytvořím z nich hrany
        for (int i = 0; i < polygon.getCount(); i++) {
            int nextIndex = (i + 1) % polygon.getCount();
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint(nextIndex);
            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            // Pokud je horizontální, ignoruju
            if (edge.isHorizontal())
                continue;
            edge.orientate();
            // Přidám hranu do seznamu
            edges.add(edge);
        }

        // Najít yMin, yMax
        int yMin = polygon.getPoint(0).getY();
        int yMax = polygon.getPoint(polygon.getCount() - 1).getY();

        for (Point p : polygon.getPoints()) {
            if (yMin > p.getY()) {
                yMin = p.getY();
            }
            if (yMax < p.getY()) {
                yMax = p.getY();
            }
        }

        // Pro Y od yMin po yMax
        for (int y = yMin; y <= yMax; y++) {
            List<Integer> intersections = new ArrayList<>();
            for (Edge edge : edges) {
                if (edge.isIntersection(y)) {
                    intersections.add(edge.getIntersection(y));
                }
            }

            intersections.sort(Comparator.comparingInt(value -> value));
            System.out.println(intersections);
            for (int i = 1; i < intersections.size(); i += 2) {
                for (int j = intersections.get(i - 1); j <= intersections.get(i); j++) {
                    img.setPixel(j, y, fillColor.paint(j, y));
                }
            }


            polygonRasterizer.draw(polygon, lineRasterizer, outlineColor, img);
        }
    }

}
