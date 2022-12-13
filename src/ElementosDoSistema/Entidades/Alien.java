package ElementosDoSistema.Entidades;

import ElementosDoSistema.Entidade;
import ElementosDoSistema.Tuple;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** Classe para objetos do tipo Alien, que são os inimigos do jogo e tem a habilidade de atirar e de se mover.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Alien extends Entidade {
    private boolean vivo;
    private Tiro tiro;

    private int tipo;

    /** Construtor da classe Alien
     * @param tipo int - Tipo de Alien
     * @param posicao Tuple - Posicao do Alien
     * @param velocidade Tuple - Velocidade do Alien
     */
    public Alien(int tipo, Tuple posicao, Tuple velocidade) {
        super(posicao, velocidade);
        this.tipo = tipo;
        this.resetarAlien();
    }
    
    /** Getter para o atributo vivo
     * @return boolean - Valor do atributo vivo
     */
    public boolean getVivo() {
        return this.vivo;
    }

    /** Retorna o tanto de pontos que o jogador ganha por matar um Alien
     * @return int - Pontos que o jogador ganha por matar esse tipo de Alien
     */
    public int getPontosPorMorte() {
        switch (this.tipo) {
            case 1:
                return 10;
            case 2:
                return 20;
            case 3:
                return 30;
            case 4:
                return 100;
            default:
                return 0;
        }
    }

    
    /** Setter para o atributo vivo
     * @param vivo boolean - Valor para o atributo vivo
     */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    
    /** Getter para o atributo tiro
     * @return Tiro - Tiro atribuido ao Alien
     */
    public Tiro getTiro() {
        return this.tiro;
    }

    
    /** Setter para o atributo tiro
     * @param tiro Tiro - Tiro a ser atribuido ao Alien
     */
    public void setTiro(Tiro tiro) {
        this.tiro = tiro;
    }

    /** Metodo que reseta o Alien para o estado inicial, com vida e sem tiro
     */
    public void resetarAlien () {
        this.vivo = true;
        this.tiro = null;
        switch(tipo) {
            case 1:
                this.setDimensoes(new Image("Imagens/aliens/alien1/1.png"));
                break;
            case 2:
                this.setDimensoes(new Image("Imagens/aliens/alien2/1.png"));
                break;
            case 3:
                this.setDimensoes(new Image("Imagens/aliens/alien3/1.png"));
                break;
            case 4:
                this.setDimensoes(new Image("Imagens/aliens/especial/1.png"));
                break;
        }
    }

    
    /** Metodo que faz o alien atirar, gerando um Tiro que se desloca para baixo
     * @return Tiro - Tiro gerado
     */
    public Tiro atirar() {
        Tuple tiroPos = this.getPos().clone();
        tiroPos.deslocar(0, this.getDimensoes().getY());
        return new Tiro(tiroPos, new Tuple(0, 2));
    }

    
    /** Metodo que imprime o Alien na tela, caso ele esteja vivo
     * @param gc GraphicsContext - Contexto grafico do canvas
     */
    public void imprimir(GraphicsContext gc) {
        if (this.getVivo()) {
            this.desenhar(gc);
        }
    }
}
