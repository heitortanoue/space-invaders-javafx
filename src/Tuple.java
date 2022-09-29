public class Tuple {
    private int x;
    private int y;
    
    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void deslocar(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    public double distancia(Tuple p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }
    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
