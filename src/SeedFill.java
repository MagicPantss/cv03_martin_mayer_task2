import java.awt.image.Raster;

public class SeedFill implements Filler {

    private RasterBufferedImage raster;
    private int x, y;
    private int backgroundColor;
    private PatternFill fillColor;

    public SeedFill(RasterBufferedImage raster, int x, int y, PatternFill fillColor, int backgroundColor) {
        this.raster = raster;
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void fill() {
        seedFill(x, y);
    }

    private void seedFill(int x, int y) {
        int pixelColor = raster.getPixel(x, y);
        if (pixelColor != backgroundColor)
            return;
        raster.setPixel(x, y, fillColor.paint(x, y));
        seedFill(x, y - 1);
        seedFill(x, y + 1);
        seedFill(x + 1, y);
        seedFill(x - 1, y);
    }
}
