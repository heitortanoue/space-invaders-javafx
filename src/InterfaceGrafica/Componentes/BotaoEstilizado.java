package InterfaceGrafica.Componentes;

import javafx.scene.control.Button;

/** Classe para objetos do tipo BotaoEstilizado, que é um botão estilizado para o jogo.
 * @author Heitor Tanoue de Mello - 12547260
 */
public class BotaoEstilizado extends Button {
    /** Construtor da classe BotaoEstilizado
     * @param texto String - Texto do botão
     */
    public BotaoEstilizado (String texto) {
        super();

        String style = "-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 10; -fx-background-radius: 10;";
        String style_hover = "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 10; -fx-background-radius: 10;";

        this.setStyle(style);
        // cursor vira maozinha quando passa por cima
        this.setOnMouseEntered(e -> {
            this.setStyle(style_hover + "-fx-cursor: hand;");
            this.setOnMouseExited(e2 -> {
                this.setStyle(style);
            });
        });

        this.setText(texto);
    }
}
