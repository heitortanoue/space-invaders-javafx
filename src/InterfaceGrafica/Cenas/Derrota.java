package InterfaceGrafica.Cenas;

import InterfaceGrafica.*;
import InterfaceGrafica.Componentes.BotaoEstilizado;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/** Classe para objetos do tipo Derrota, que é uma cena que aparece quando o jogador perde o jogo.
 * @author Heitor Tanoue de Mello - 12547260
 */
public class Derrota extends Painel {
    /** Construtor da classe Derrota
     */
    public Derrota () {
        super(new BorderPane());
    }

    /** Metodo que cria o painel da cena Derrota.
     * @param ci ControladorInterface - Controlador da interface.
     * @return BorderPane - Painel da cena Derrota.
     */
    public BorderPane createPane (ControladorInterface ci) {
        BorderPane pane = (BorderPane) this.getPane();

        VBox buttonsBox = new VBox();
        BotaoEstilizado btn_voltar = new BotaoEstilizado("Voltar ao menu");
        BotaoEstilizado btn_sair = new BotaoEstilizado("Sair");
        Text texto = new Text("GAME OVER!");
        Font novaFonte = Font.loadFont(Derrota.class.getResource("/Outros/fonte.ttf").toExternalForm(), 64);
        texto.setFont(novaFonte);
        texto.setFill(Color.WHITE);
        texto.setStroke(Color.BLACK);
        texto.setStrokeWidth(2);

        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(0, 0, 100, 0));

        btn_voltar.setOnAction(e -> {
            ci.setIniciado(false);
            ci.setTelaAtual(null);
            ci.getEngine().resetarJogo();
        });
        btn_sair.setOnAction(e -> {
            // fecha o jogo
            System.exit(0);
        });
    
        buttonsBox.getChildren().addAll(texto, btn_voltar, btn_sair);
        pane.setCenter(buttonsBox);

        this.setPane(pane);
        return pane;
    }
}
