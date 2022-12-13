package ElementosDoSistema.Entidades;

import ElementosDoSistema.Entidade;
import ElementosDoSistema.Tuple;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** Classe para objetos do tipo Nave, que é objeto controlado pelo jogador.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Tiro extends Entidade {
    
    private boolean visivel;

    /** Construtor da classe Tiro.
     * @param posicao Tuple - Posição do Tiro.
     * @param velocidade Tuple - Velocidade do Tiro.
     */
    public Tiro(Tuple posicao, Tuple velocidade) {
        super(posicao, velocidade);
        this.visivel = true;
        this.setDimensoes(new Image("Imagens/outros/tiroNave.png"));
    }

    
    /** Getter para o atributo visivel.
     * @return boolean
     */
    public boolean getVisivel() {
        return this.visivel;
    }

    
    /** Setter para o atributo visivel.
     * @param visivel boolean - Valor a ser atribuido.
     */
    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    
    /** Retorna se o Tiro está visivel.
     * @return boolean - True se o Tiro estiver visivel, False se não.
     */
    public boolean estaNaTela () {
        return (this == null || !this.getVisivel() || this.colisaoTela());
    }

    
    /** Coloca o conteudo do Tiro no Console.
     * @param c Console - Console onde sera impresso.
     */
    public void imprimir ( GraphicsContext gc ) {
        if (this.getVisivel()) {
            if (this.getVel().getY() < 0) {
                this.setDimensoes(new Image("Imagens/outros/tiroNave.png"));
            } else {
                this.setDimensoes(new Image("Imagens/outros/tiroAlien.png"));
            }
            this.desenhar(gc);
        }
    }
}
