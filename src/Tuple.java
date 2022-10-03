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

    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Tuple clone() {
        return new Tuple(this.getX(), this.getY());
    }

    public boolean equals ( Tuple t) {
        return (this.getX() == t.getX() && this.getY() == t.getY());
    }

    public Tuple relativoAoChao(Console c) {
        this.y = c.getAlturaTela() - this.y;
        return this;
    }
}
