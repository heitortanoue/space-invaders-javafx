package ElementosDoSistema;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;

/** Classe para objetos do tipo Exercito, um grupo de aliens dispostos em uma matriz.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Exercito {
    public Alien[][] exercito;
    private Nave naveRef;

    private ArrayList<Tiro> tiros = new ArrayList<Tiro>();
    private int numTirosMax = 2;

    private Tuple velocidadeExercito;
    private double velocidadeSalva;
    private int direcao;
    private double _assistForDown = 0;

    public Exercito (Nave naveRef) {
        this.naveRef = naveRef;
        this.exercito = new Alien[11][5];
        // this.numTiros = 0;
        this.direcao = 1;
        this.velocidadeSalva = .5;
        this.velocidadeExercito = new Tuple(velocidadeSalva, 0);
        this.mobilizarExercito();
    }

    
    /** Getter para o atributo tiros.
     * @return ArrayList.Tiro - Lista de todos os tiros na tela.
     */
    public ArrayList<Tiro> getTiros() {
        return this.tiros;
    }

    public int getTamanhoExercito() {
        return this.exercito.length * this.exercito[0].length;
    }
    
    /** Adiciona um Alien na matriz de aliens.
     * @param linha int - Linha da matriz a ser adicionado o Alien.
     * @param coluna int - Coluna da matriz a ser adicionado o Alien.
     * @param alien Alien - Alien a ser adicionado na matriz.
     */
    private void addAlien (int linha, int coluna, Alien alien) {
        this.exercito[linha][coluna] = alien;
    }

    public void setPosicaoExercito (Tuple posicao, double gap) {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                this.exercito[i][j].setPos(new Tuple(
                    posicao.getX() + i * (this.exercito[i][j].getDimensoes().getX() + gap),
                    posicao.getY() + j * (this.exercito[i][j].getDimensoes().getY() + gap)
                ));
            }
        }
    }

    /** Metodo que mobiliza o exercito, adicionando todos os Aliens na matriz.
     */
    public void mobilizarExercito () {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                int tipo;
                if ( j == 0 ) {
                    tipo = 3;
                } else if (j == 1 || j == 2) {
                    tipo = 2;
                } else {
                    tipo = 1;
                }

                this.addAlien(i, j, new Alien(tipo, new Tuple(i, j), this.velocidadeExercito));
            }
        }
    }

    
    /** Muda a velocidade do exercito para o valor passado por parametro.
     * @param v Tuple - Velocidade do exercito.
     */
    private void mudarVelocidadeExercito (Tuple v) {
        if (v.getX() > 0) {
            this.direcao = 1;
        } else if (v.getX() < 0) {
            this.direcao = -1;
        } else {
            this.direcao = 0;
        }

        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                this.exercito[i][j].setVel(v);
            }
        }

        this.velocidadeExercito = v;
        // this.velocidadeSalva = v.getX();
    }

    /** Acelera o exercito na direcao atual.
     */
    public void aumentarDificuldade () {
        this.velocidadeSalva *= 1.2;
    }

    /** Move o Exercito de acordo com sua velocidade atual, detecta com as colisoes com borda e anda uma unidade para baixo caso colida.
     */
    public void moverExercito () {
        Alien alienEsquerda = getUltimoAlienVivoEsquerda();
        Alien alienDireita = getUltimoAlienVivoDireita();

        if (this.direcao == -1 && alienEsquerda.colisaoTela() ||
        this.direcao == 1 && alienDireita.colisaoTela()) {
            this.velocidadeSalva *= -1;
            this.mudarVelocidadeExercito(new Tuple(0, Math.abs(this.velocidadeSalva)));
            if (this._assistForDown == 0) {
                this._assistForDown = alienEsquerda.getPos().getY();
            }
            this.moverExercito();
        }
        
        if (Math.abs((alienEsquerda.getPos().getY() - this._assistForDown) - alienEsquerda.getDimensoes().getY() * 1.5) < 0.5) {
            this.mudarVelocidadeExercito(new Tuple (this.velocidadeSalva, 0));
            this._assistForDown = 0;
        }

        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                this.exercito[i][j].deslocar();
            }
        }
    }

    
    /** Faz com que o Exercito atire, um tiro guiado na posicao do jogador (caso esse esteja alinhado com o Exercito) e outro de uma coluna randomica.
     * @param c Console - Console para desenhar os Tiros.
     */
    public void atirar () {
        if (tiros.size() >= this.numTirosMax) {
            return;
        }

        // tiro randomico
        Random rand = new Random();
        int randLinha = rand.nextInt(this.exercito.length - 1);
        for (int i = this.exercito[0].length - 1; i >= 0; i--) {
            if (this.exercito[randLinha][i].getVivo()) {
                this.tiros.add(this.exercito[randLinha][i].atirar());
                break;
            }
        }

        // checa se a nave esta alinhada com alguma coluna do exercito
        int xNave = (int) this.naveRef.getPos().getX();
        if (this.exercito[0][0].getPos().getX() > xNave ||
            this.exercito[this.exercito.length - 1][0].getPos().getX() < xNave) {
            return;
        }

        // tiro focado na nave
        int xRelative = (int) (xNave - this.exercito[0][0].getPos().getX()) / (int) this.exercito[0][0].getDimensoes().getX();
        for (int j = this.exercito[0].length - 1; j >= 0; j--) {
            if (xRelative >= this.exercito.length || xRelative < 0) {
                break;
            }
            if (this.exercito[xRelative][j].getVivo()) {
                this.tiros.add(this.exercito[xRelative][j].atirar());
                break;
            }
        }
    }

    public void resetarExercito () {
        this.mudarVelocidadeExercito(new Tuple(Math.abs(this.velocidadeSalva), 0));
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                this.exercito[i][j].resetarAlien();
            }
        }
    }

    
    /** Retorna o numero de Aliens vivos no Exercito.
     * @return int
     */
    public int numAliensVivos () {
        int numAliensVivos = 0;
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                if (this.exercito[i][j].getVivo()) {
                    numAliensVivos++;
                }
            }
        }
        return numAliensVivos;
    }

    
    /** Retorna o Alien que colidiu com o Tiro passado por parametro.
     * @param t Tiro - Tiro que colidiu com algum Alien.
     * @return Alien / null - Alien que colidiu com o Tiro.
     */
    public Alien alienColisao ( Tiro t ) {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                if (this.exercito[i][j].getVivo() && this.exercito[i][j].colisaoEntidade(t)) {
                    return this.exercito[i][j];
                }
            }
        }
        return null;
    }

    
    /** Coloca o conteudo do Exercito no Console passado por parametro.
     * @param c Console - Console para desenhar o Exercito.
     */
    public void imprimir ( GraphicsContext gc ) {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                if (this.exercito[i][j].getVivo()) {
                    this.exercito[i][j].desenhar(gc);
                }
            }
        }
    }

    
    /** Coloca o conteudo dos Tiros do Exercito no Console passado por parametro.
     * @param c Console - Console para desenhar os Tiros.
     */
    public void imprimirTiros ( GraphicsContext gc ) {
        for (int i = 0; i < this.tiros.size(); i++) {
            Tiro tiroAtual = this.tiros.get(i);
            if (tiroAtual == null || !tiroAtual.getVisivel() || tiroAtual.colisaoTela()) {
                this.tiros.remove(i);
                i--;
            } else {
                tiroAtual.imprimir(gc);
            }
        }
    }

    /** [CHEAT] Mata todos os Aliens do Exercito.
     */
    public void matarExercito () {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                this.exercito[i][j].setVivo(false);
            }
        }
    }

    
    /** Retorna a altura (y) do Alien vivo mais abaixo na tela.
     * @return int
     */
    public double getAlturaUltimoAlienVivo () {
        for (int i = this.exercito[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < this.exercito.length; j++) {
                if (this.exercito[j][i].getVivo()) {
                    return this.exercito[j][i].getPos().getY();
                }
            }
        }
        return 0;
    }

    public Alien getUltimoAlienVivoEsquerda () {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[0].length; j++) {
                if (this.exercito[i][j].getVivo()) {
                    return this.exercito[i][j];
                }
            }
        }
        return null;
    }

    public Alien getUltimoAlienVivoDireita () {
        for (int i = this.exercito.length - 1; i >= 0; i--) {
            for (int j = 0; j < this.exercito[0].length; j++) {
                if (this.exercito[i][j].getVivo()) {
                    return this.exercito[i][j];
                }
            }
        }
        return null;
    }
}
