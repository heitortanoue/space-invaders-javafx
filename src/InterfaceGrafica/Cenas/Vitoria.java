package InterfaceGrafica.Cenas;

import InterfaceGrafica.*;
import InterfaceGrafica.Componentes.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

/** Classe para objetos do tipo Vitoria, que é uma cena que aparece quando o jogador vence o jogo.
 * @author Heitor Tanoue de Mello - 12547260
 */
public class Vitoria extends Painel {
    /** Construtor da classe Vitoria.
     */
    public Vitoria() {
        super(new BorderPane());
    }

    /** Metodo que cria o painel da cena Vitoria.
     * @param ci ControladorInterface - Controlador da interface.
     * @return BorderPane - Painel da cena Vitoria.
     */
    public BorderPane createPane(ControladorInterface ci) {
        BorderPane pane = (BorderPane) this.getPane();
        BackgroundImage myBI= new BackgroundImage(new Image("Imagens/outros/menu_background.jpg", this.getLarguraTela(), this.getAlturaTela(), false, true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        pane.setBackground(new Background(myBI));

        BotaoEstilizado btn_voltar = new BotaoEstilizado("Voltar ao menu");
        btn_voltar.setOnAction(e -> {
            // volta para o menu
            ci.setIniciado(false);
            ci.setTelaAtual(null);
        });

        VBox root = new VBox();
        root.setSpacing(30);
        root.setAlignment(Pos.CENTER);

        Text title = new Text("Voce venceu!");
        Font novaFonteGrande = Font.loadFont(Vitoria.class.getResource("/Outros/fonte.ttf").toExternalForm(), 48);
        title.setFont(novaFonteGrande);
        title.setFill(Color.WHITE);

        Image crown = new Image("Imagens/outros/crown.png");
        ImageView crownView = new ImageView(crown);
        crownView.setPreserveRatio(true);
        crownView.setFitWidth(200);

        Text pontos = new Text("Pontos: " + ci.getEngine().getPontos());
        Font novaFontePequena = Font.loadFont(Vitoria.class.getResource("/Outros/fonte.ttf").toExternalForm(), 28);
        pontos.setFont(novaFontePequena);
        pontos.setFill(Color.WHITE);

        VBox principal = new VBox();
        principal.setSpacing(10);
        principal.setAlignment(Pos.CENTER);

        principal.getChildren().addAll(crownView, title, pontos);
        root.getChildren().addAll(principal, btn_voltar);
        pane.setCenter(root);

        this.setPane(pane);
        return pane;
    }
}
