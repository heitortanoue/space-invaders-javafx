package InterfaceGrafica.Cenas;

import InterfaceGrafica.ControladorInterface;
import InterfaceGrafica.Painel;
import InterfaceGrafica.Componentes.BotaoEstilizado;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

/** Classe para objetos do tipo Pause, que é uma cena que aparece quando o jogador pausa o jogo.
 * @author Heitor Tanoue de Mello - 12547260
 */
public class Pause extends Painel{
    /** Construtor da classe Pause.
     */
    public Pause () {
        super(new BorderPane());
    }

    /** Metodo que cria o painel da cena Pause.
     * @param ci ControladorInterface - Controlador da interface.
     * @return BorderPane - Painel da cena Pause.
     */
    public BorderPane createPane (ControladorInterface ci) {
        BorderPane pane = (BorderPane) this.getPane();
        VBox newVbox = new VBox();

        newVbox.setSpacing(10);
        newVbox.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setPrefSize(this.getLarguraTela() /2, this.getAlturaTela() /2);
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        pane.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        VBox buttonsBox = new VBox();
        BotaoEstilizado btn_continuar = new BotaoEstilizado("Continuar");
        BotaoEstilizado btn_sair = new BotaoEstilizado("Sair");
        Image logo = new Image("Imagens/outros/logo.png");
        ImageView iv_logo = new ImageView(logo);

        iv_logo.setPreserveRatio(true);
        iv_logo.setFitWidth(600);
        iv_logo.setFitHeight(600);
        BackgroundImage myBI= new BackgroundImage(new Image("Imagens/outros/menu_background.jpg", this.getLarguraTela(), this.getAlturaTela(), false, true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        pane.setBackground(new Background(myBI));
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(0, 0, 100, 0));

        btn_continuar.setOnAction(e -> {
            ci.pausar();
        });

        btn_sair.setOnAction(e -> {
            ci.setIniciado(false);
            ci.setTelaAtual(null);
            ci.getEngine().resetarJogo();
        });
    
        buttonsBox.getChildren().addAll(btn_continuar, btn_sair);
        newVbox.getChildren().addAll(iv_logo, buttonsBox);
        pane.setCenter(newVbox);

        this.setPane(pane);
        return pane;
    }
}
