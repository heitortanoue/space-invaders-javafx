import java.util.ArrayList;

public class Console extends Tela {
    private char space = ' ';
    private char[][] screen;

    public Console() {
        super();
        this.screen = new char[this.getAlturaTela() + 2][this.getLarguraTela() + 2];
        this.clear();
    }

    public void clear() {
        for (int i = 0; i < this.screen.length; i++) {
            for (int j = 0; j < this.screen[i].length; j++) {
                this.screen[i][j] = this.space;
            }
        }
    }

    public void printBorders() {
        // "shifta" a tela para baixo e para direita
        for (int i = this.screen.length - 1; i > 0; i--) {
            for (int j = this.screen[i].length - 1; j > 0; j--) {
                this.screen[i][j] = this.screen[i - 1][j - 1];
            }
        }

        for (int i = 0; i < this.screen.length; i++) {
            this.screen[i][0] = '|';
            this.screen[i][this.screen[i].length - 1] = '|';
        }
        for (int i = 0; i < this.screen[0].length; i++) {
            this.screen[0][i] = '-';
            this.screen[this.screen.length - 1][i] = '-';
        }
        this.screen[0][0] = '.';
        this.screen[0][this.screen[0].length - 1] = '.';
        this.screen[this.screen.length - 1][0] = '\'';
        this.screen[this.screen.length - 1][this.screen[0].length - 1] = '\'';
    }


    public void print() {
        // System.out.print("\033[2J\033[1;1H");
        this.printBorders();

        // imprime conteudo
        for (int i = 0; i < this.screen.length; i++) {
            for (int j = 0; j < this.screen[i].length; j++) {
                System.out.print(this.screen[i][j]);
            }
            System.out.println();
        }
        this.clear();
    }

    public void setPixel(Tuple pos, char c) {
        if (pos.getX() >= 0 && pos.getX() < this.getLarguraTela() &&
        pos.getY() >= 0 && pos.getY() < this.getAlturaTela()) {
            this.screen[pos.getY()][pos.getX()] = c;
        }
    }

    public void escreverNaTela(Tuple pos, String str) {
        for (int i = 0; i < str.length(); i++) {
            this.setPixel(pos, str.charAt(i));
            pos.deslocar(1, 0);
        }
    }

    public void setPixels(ArrayList<Tuple> pixels, char c) {
        for (Tuple pixel : pixels) {
            this.setPixel(pixel, c);
        }
    }
}
