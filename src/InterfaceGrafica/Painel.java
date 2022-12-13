package InterfaceGrafica;

import Engine.Tela;
import javafx.scene.layout.Pane;

/** Classe abstrata para ser herdada por todas as classes que retornam um Pane para impressao.
 * @author Heitor Tanoue de Mello - 12547260
 */
public abstract class Painel extends Tela implements Scenable {
    private Pane pane;

    /** Construtor da classe Painel
     * @param pane Pane - Pane a ser impresso
     */
    public Painel (Pane pane) {
        super();
        this.pane = pane;
    }

    /** Getter para o atributo pane
     * @return Pane - Valor do atributo pane
     */
    public Pane getPane () {
        return this.pane;
    }

    /** Setter para o atributo pane
     * @param pane Pane - Valor para o atributo pane
     */
    public void setPane (Pane pane) {
        this.pane = pane;
    }
}
