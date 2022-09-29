public class Console {
    private char space = ' ';
    private char[][] screen;

    public Console(int width, int height) {
        this.screen = new char[height][width];
        this.clear();
    }

    public void clear() {
        for (int i = 0; i < this.screen.length; i++) {
            for (int j = 0; j < this.screen[i].length; j++) {
                this.screen[i][j] = this.space;
            }
        }
    }

    public void print() {
        for (int i = 0; i < this.screen.length; i++) {
            for (int j = 0; j < this.screen[i].length; j++) {
                System.out.print(this.screen[i][j]);
            }
            System.out.println();
        }
        this.clear();
    }

    public void setPixel(Tuple pos, char c) {
        this.screen[pos.getY()][pos.getX()] = c;
    }

    public void setPixels(List<Tuple> pixels, char c) {
        for (Tuple pixel : pixels) {
            this.setPixel(pixel, c);
        }
    }
}
