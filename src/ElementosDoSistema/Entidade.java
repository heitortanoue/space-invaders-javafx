package ElementosDoSistema;
/** Classe abstrata para todos os objetos do jogo.
* @author Heitor Tanoue de Mello - 12547260
*/

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import Engine.Tela;
import javafx.geometry.*;

public abstract class Entidade extends Tela implements Imprimivel {
    private Image imagem;
    private double largura;
    private double altura;
    // private boolean visivel;

    private Tuple pos;
    private Tuple vel;
    
    /** Construtor da classe Entidade.
     * @param posicao Tuple - Posicao da Entidade.
     * @param velocidade Tuple - Velocidade da Entidade.
     */
    public Entidade(Tuple posicao, Tuple velocidade) {
        super();
        this.pos = posicao;
        this.vel = velocidade;
    }

    
    /** Getter para a posicao.
     * @return Tuple
     */
    public Tuple getPos () {
        return this.pos;
    }

    
    /** Setter para a posicao.
     * @param pos Tuple - Posicao a ser atribuida.
     */
    public void setPos (Tuple pos) {
        this.pos = pos;
    }

    
    /** Getter para a velocidade.
     * @return Tuple
     */
    public Tuple getVel () {
        return this.vel;
    }

    public void setDimensoes (Tuple dimensoes) {
        this.largura = dimensoes.getX();
        this.altura = dimensoes.getY();
    }
    public void setDimensoes (Image imagem) {
        this.imagem = imagem;
        this.largura = imagem.getWidth();
        this.altura = imagem.getHeight();
    }

    public Tuple getDimensoes () {
        return new Tuple(this.largura, this.altura);
    }
    
    /** Setter para a velocidade.
     * @param vel Tuple - Velocidade a ser atribuida.
     */
    public void setVel (Tuple vel) {
        this.vel = vel;
    }

    /** Metodo que atualiza a posicao da Entidade de acordo com sua velocidade atual.
     */
    public void deslocar() {
        this.pos.deslocar(this.vel.getX(), this.vel.getY());
    }

    
    /** Metodo que atualiza a posicao da Entidade de acordo com o valor passado como parametro.
     * @param deslocamento Tuple - Deslocamento a ser aplicado.
     */
    public void deslocar(Tuple deslocamento) {
        this.pos.deslocar(deslocamento.getX(), deslocamento.getY());
    }


    public void setImagem (String caminho) {
        this.imagem = new Image(caminho);
        this.largura = this.imagem.getWidth();
        this.altura = this.imagem.getHeight();
    }

    public Rectangle2D getFronteira () {
        return new Rectangle2D(this.pos.getX(), this.pos.getY(), this.largura, this.altura);
    }

    public void desenhar (GraphicsContext gc) {
        if (this.imagem == null) {
            System.out.println("Imagem nao definida");
            return;
        }
        gc.drawImage(this.imagem, this.pos.getX(), this.pos.getY());
    }

    
    
    /** Metodo que checa se duas entidades colidiram.
     * @param entidade Entidade - Entidade a ser comparada.
     * @return boolean - Retorna true se a Entidade passada como parametro estiver na mesma posicao que a Entidade atual.
     */
    public boolean colisaoEntidade(Entidade entidade) {
        return this.getFronteira().intersects(entidade.getFronteira());
    }
    
    /** Metodo que checa se a Entidade atual colidiu com uma das bordas da tela.
     * @return boolean
     */
    public boolean colisaoTela() {
        // distancia menor que um ou passou dos limites da tela
        return ( 
            this.pos.getX() <= 0 ||
            this.pos.getX() + this.largura >= this.getLarguraTela() ||
            this.pos.getY() <= 0 ||
            this.pos.getY() + this.altura >= this.getAlturaTela()
            );
    }
    public boolean colisaoTelaComVelocidade() {
        // distancia menor que um ou passou dos limites da tela
        return ( 
            this.pos.getX() + this.vel.getX() <= 0 ||
            this.pos.getX() + this.vel.getX() + this.largura >= this.getLarguraTela() ||
            this.pos.getY() + this.vel.getY() <= 0 ||
            this.pos.getY() + this.vel.getY() + this.altura >= this.getAlturaTela()
            );
    }
}
