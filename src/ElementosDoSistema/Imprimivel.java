package ElementosDoSistema;

import javafx.scene.canvas.GraphicsContext;

/** Interface para todas as Classes que imprimem seus objetos em um Console.
* @author Heitor Tanoue de Mello - 12547260
*/
public interface Imprimivel {
    /** Metodo que coloca o conteudo no Console para impressao.
     * @param gc GraphicsContext - Contexto grafico para impressao.
     */
    public void imprimir ( GraphicsContext gc );
}
