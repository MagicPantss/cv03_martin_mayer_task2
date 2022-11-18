public class SolidFill implements PatternFill {
    private int color;

    public SolidFill(int color) {
        this.color = color;
    }

    @Override
    public int paint(int x, int y) {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

