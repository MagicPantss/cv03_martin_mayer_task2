public class DashedLine implements LineRasterizer {

    private int dashLength;
    private int spaceLength;

    public DashedLine(int dashLength, int spaceLength) {
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
    }

    public void setDashLength(int dashLength) {
        this.dashLength = dashLength;
    }

    public void setSpaceLength(int spaceLength) {
        this.spaceLength = spaceLength;
    }

    @Override
    public void drawLine(Point point1, Point point2, int color, Raster raster) {
        int space = 0;
        int length = 0;
        double k;
        if (point1.getX() == point2.getX()){
            k = Integer.MAX_VALUE;
        }else {
            k = (point2.getY() - point1.getY())/(double) (point2.getX() - point1.getX());
        }
        double q = point1.getY() - k * point1.getX();

        if (Math.abs(point1.getY() - point2.getY()) < Math.abs(point1.getX() - point2.getX())){
            if (point2.getX() < point1.getX()) {
                Point pointTmp = point1;
                point1 = point2;
                point2 = pointTmp;//vyměň koncové body
            }
            for (int x = point1.getX(); x <= point2.getX(); x++){
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= dashLength) {
                    int y = (int) Math.round(k * x + q);
                    raster.setPixel(x, y, color);
                    length++;
                } else {
                    space++;
                }

            }
        } else {
            if (point2.getY() < point1.getY()) {
                Point pointTmp = point1;
                point1 = point2;
                point2 = pointTmp; //vyměň koncové body
            }
            //řídící osa je Y

            for(int y = point1.getY(); y <= point2.getY(); y++){
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= dashLength) {
                    int x = (int) Math.round((y - q) / k);
                    raster.setPixel(x, y, color);
                    length++;
                } else {
                    space++;
                }
            }
        }
    }
}