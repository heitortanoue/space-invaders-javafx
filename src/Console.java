import java.util.ArrayList;

/**Classe para objetos do tipo Console, que é o que organiza as impressões no terminal.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Console extends Tela {
    private char space = ' ';
    private char[][] screen;

    public Console() {
        super();
        this.screen = new char[this.getAlturaTela() + 2][this.getLarguraTela() + 2];
        this.clear();
    }

    /** Limpa o conteúdo do Console.
    */
    public void clear() {
        for (int i = 0; i < this.screen.length; i++) {
            for (int j = 0; j < this.screen[i].length; j++) {
                this.screen[i][j] = this.space;
            }
        }
    }

    /** Coloca no Console uma borda.
    */
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

    /** Imprime o conteúdo do Console na tela.
     */
    public void print() {
        // Deixa a animção do console mais fluida
        System.out.print("\033[2J\033[1;1H");
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

    
    /** Coloca um caractere no Console.
     * @param pos Tuple - Posição do caractere
     * @param c char - Caractere a ser colocado
     */
    public void setPixel(Tuple pos, char c) {
        if (pos.getX() >= 0 && pos.getX() < this.getLarguraTela() &&
        pos.getY() >= 0 && pos.getY() < this.getAlturaTela()) {
            this.screen[(int) pos.getY()][(int) pos.getX()] = c;
        }
    }

    /** Coloca um caractere em uma lista de posições.
     * @param pixels ArrayList.Tuple - Lista de posições
     * @param c char - Caractere a ser colocado
     */
    public void setPixels(ArrayList<Tuple> pixels, char c) {
        for (Tuple pixel : pixels) {
            this.setPixel(pixel, c);
        }
    }

    /** Coloca uma String no Console.
     * @param pos Tuple - Posição do caractere
     * @param str String - String a ser impressa
     */
    public void escreverNaTela(Tuple pos, String str) {
        for (int i = 0; i < str.length(); i++) {
            this.setPixel(pos, str.charAt(i));
            pos.deslocar(1, 0);
        }
    }

    /** Coloca o caractere '@' no Console na posição da Entidade passada por parâmetro.
     * @param e Entidade - Entidade a ser explodida.
     */
    public void explodir (Entidade e) {
        this.setPixel(e.getPos(), '@');
    }

    
    /** Coloca uma String no Console (centralizado na tela).
     * @param y int - Altura do caractere
     * @param str String - String a ser impressa
     */
    public void escreverNaTelaCentralizado ( int y, String str ) {
        int x = (this.getLarguraTela() - str.length()) / 2;
        this.escreverNaTela(new Tuple(x, y), str);
    }
    
    /** Coloca o conteúdo da tela de vitoria no Console.
     * @param n Nave - Nave da qual sera impresso os pontos
     */
    public void vitoria ( Nave n ) {
        int y = this.getAlturaTela() / 2;
        this.escreverNaTelaCentralizado(y - 3, "PARABÉNS!!");
        this.escreverNaTelaCentralizado(y - 2, "VOCÊ VENCEU!!");
        this.escreverNaTelaCentralizado(y, "Pontos: " + n.getPontos());
    }

    
    /** Coloca o conteúdo da tela de derrota no Console.
     * @param n Nave - Nave da qual sera impresso os pontos
     */
    public void derrota ( Nave n ) {
        int y = this.getAlturaTela() / 2;
        this.escreverNaTelaCentralizado(y - 3, "GAME OVER");
        this.escreverNaTelaCentralizado(y - 2, "VOCÊ PERDEU!!");
        this.escreverNaTelaCentralizado(y, "Pontos: " + n.getPontos());
    }

    /** Coloca o conteúdo da tela de instrucoes no Console.
     */
    public void instrucoes () {
        int y = this.getAlturaTela() / 2;
        this.escreverNaTelaCentralizado(y - 3, "INSTRUÇÕES");
        this.escreverNaTelaCentralizado(y - 2, "Use A e D para mover a nave");
        this.escreverNaTelaCentralizado(y - 1, "Use 'L' para atirar");
        this.escreverNaTelaCentralizado(y, "Pressione P para começar");
    }

    /** Coloca o conteúdo da tela de inicio no Console.
     */
    public void telaInicial () {
        int y = this.getAlturaTela() / 2;
        this.escreverNaTelaCentralizado(y - 3, "SPACE INVADERS");
        this.escreverNaTelaCentralizado(y - 2, "Por: Heitor Tanoue");
        this.escreverNaTelaCentralizado(y, "Pressione P para continuar");
    }
}
