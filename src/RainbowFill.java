import java.awt.*;

public class RainbowFill implements PatternFill {
    @Override
    public int paint(int x, int y) {
        return Color.HSBtoRGB(x * 0.0015f + y * 0.0015f, 1f, 1f);
    }
}
