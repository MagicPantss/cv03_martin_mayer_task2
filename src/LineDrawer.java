public class LineDrawer implements LineRasterizer {

    @Override
    public void drawLine(Point point1, Point point2, int color, Raster raster) {
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
                point2 = pointTmp;
                }
            for (int x = point1.getX(); x <= point2.getX(); x++){
                int y = (int) Math.round(k*x+q);
                raster.setPixel(x, y, color);

            }
        } else {
            if (point2.getY() < point1.getY()) {
                Point pointTmp = point1;
                point1 = point2;
                point2 = pointTmp;
                }


            for(int y = point1.getY(); y <= point2.getY(); y++){
                //y = kx+q
                int x = (int) Math.round((y-q)/k);
                raster.setPixel(x, y, color);
            }
        }
    }
}
