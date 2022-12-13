package InterfaceGrafica;

import javafx.scene.layout.Pane;

/** Interface para todas as Classes retornam um Pane para impressao.
 * @author Heitor Tanoue de Mello - 12547260
 */
public interface Scenable {
    /** Metodo que retorna um Pane para impressao.
     * @param ci ControladorInterface - Controlador da Interface.
     * @return Pane - Pane para impressao.
     */
    public Pane createPane(ControladorInterface ci);
}
