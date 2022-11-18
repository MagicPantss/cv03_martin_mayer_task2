import java.util.List;

public class PolygonRasterizer {

    public void draw(Polygon polygon, LineRasterizer linerRaster, int color, Raster raster){
        List<Point> points = polygon.getPoints();
        for (int i = 1; i < points.size(); i++) {
            Point previous = points.get(i - 1);
            Point newest = points.get(i);
            linerRaster.drawLine(previous, newest, color, raster);
        }
        Point last = points.get(points.size() - 1);
        Point first = points.get(0);
        linerRaster.drawLine(last, first, color, raster);
    }
}
