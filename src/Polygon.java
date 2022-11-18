import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Point> points = new ArrayList<>();

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point){
        points.add(point);
    }

    public int getCount() {
        return points.size();
    }

    public Point getPoint(int index) {
        return points.get(index);
    }
}
