package InterfaceGrafica;

import ElementosDoSistema.Tela;
import InterfaceGrafica.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

public class Menu extends Tela{
    private BorderPane pane;

    public Menu () {
        super();
        this.pane = new BorderPane();
    }

    public BorderPane getMenuScene (ControladorInterface ci) {
        VBox buttonsBox = new VBox();
        BotaoEstilizado btn_comecar = new BotaoEstilizado("Começar jogo");
        BotaoEstilizado btn_instrucoes = new BotaoEstilizado("Instruções");
        Image logo = new Image("Imagens/outros/logo.png");
        ImageView iv_logo = new ImageView(logo);

        iv_logo.setPreserveRatio(true);
        iv_logo.setFitWidth(600);
        iv_logo.setFitHeight(600);
        BackgroundImage myBI= new BackgroundImage(new Image("Imagens/outros/menu_background.jpg", this.getLarguraTela(), this.getAlturaTela(), false, true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        this.pane.setBackground(new Background(myBI));
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(0, 0, 100, 0));

        btn_comecar.setOnAction(e -> {
            ci.setIniciado(true);
            ci.pausar(false);
            ci.getEngine().resetarJogo();
            ci.setTelaAtual(null);
        });
    
        buttonsBox.getChildren().addAll(iv_logo, btn_comecar, btn_instrucoes);
        this.pane.setCenter(buttonsBox);
        return this.pane;
    }
}
