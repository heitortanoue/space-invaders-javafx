package InterfaceGrafica;

// popup de pause
import ElementosDoSistema.Tela;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Pause extends Tela{
    private VBox pane;

    public Pause () {
        super();
        this.pane = new VBox();
        this.pane.setSpacing(10);
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setPadding(new Insets(20, 20, 20, 20));
        this.pane.setPrefSize(this.getLarguraTela() /2, this.getAlturaTela() /2);
        this.pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        this.pane.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public VBox getPauseScene (ControladorInterface ci) {
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
        this.pane.setBackground(new Background(myBI));
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
        this.pane.getChildren().addAll(iv_logo, buttonsBox);
        return this.pane;
    }
}
