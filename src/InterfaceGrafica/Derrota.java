package InterfaceGrafica;

import ElementosDoSistema.Tela;
import InterfaceGrafica.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Derrota extends Tela {
    private BorderPane pane;

    public Derrota () {
        super();
        this.pane = new BorderPane();
    }

    public BorderPane getDerrotaScene (ControladorInterface ci) {
        VBox buttonsBox = new VBox();
        BotaoEstilizado btn_voltar = new BotaoEstilizado("Voltar ao menu");
        BotaoEstilizado btn_sair = new BotaoEstilizado("Sair");
        Text texto = new Text("GAME OVER!");
        Font novaFonte = Font.loadFont(Derrota.class.getResource("/Outros/fonte.ttf").toExternalForm(), 64);
        texto.setFont(novaFonte);
        texto.setFill(Color.WHITE);
        texto.setStroke(Color.BLACK);
        texto.setStrokeWidth(2);

        BackgroundImage myBI= new BackgroundImage(new Image("Imagens/outros/menu_background.jpg", this.getLarguraTela(), this.getAlturaTela(), false, true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        this.pane.setBackground(new Background(myBI));
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
        this.pane.setCenter(buttonsBox);
        return this.pane;
    }
}
