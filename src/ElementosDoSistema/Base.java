package ElementosDoSistema;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** Classe para objetos do tipo Base, que são 'escudos' para o jogador.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Base extends Entidade {
    public int vidasMax = 20;
    private int vidas;
    private boolean vivo;

    public Base() {
        super(new Tuple(0, 0), new Tuple(0, 0));
        this.resetarBase();
    }

    /** Getter para o atributo vidas
     * @return int - Valor do atributo vidas
     */
    public int getVidas() {
        return this.vidas;
    }

    
    /** Setter para o atributo vidas
     * @param vidas int - Valor para o atributo vidas
     */
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    
    /** Metodo que decrementa as vidas da Base
     * @param dec int - Valor a ser decrementado das vidas
     */
    public void decVidas( int dec ) {
        this.vidas -= dec;
    }

    
    /** Getter para o atributo vivo
     * @return boolean - Valor do atributo vivo
    */
    public boolean getVivo() {
        return this.vidas > 0;
    }

    /** Metodo atualiza o atributo vivo da Base
     */
    public void updateVivo() {
        if (this.vidas <= 0) {
            this.vivo = false;
        }
    }

    public void resetarBase () {
        this.vidas = this.vidasMax;
        this.vivo = true;
        this.setDimensoes(new Image("Imagens/base/normal.png"));
    }

    
    /** Metodo que imprime a Base na tela
     * @param c Console - Console a ser desenhado
     */
    public void imprimir ( GraphicsContext gc ) {
        if (this.vivo) {
            this.desenhar(gc);
        }
    }
}
