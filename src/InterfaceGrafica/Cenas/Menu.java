package InterfaceGrafica.Cenas;

import InterfaceGrafica.*;
import InterfaceGrafica.Componentes.BotaoEstilizado;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

/** Classe para objetos do tipo Menu, que é uma cena que aparece quando o jogador abre o jogo.
 * @author Heitor Tanoue de Mello - 12547260
 */
public class Menu extends Painel{
    /** Construtor da classe Menu
     */
    public Menu () {
        super(new BorderPane());
    }

    /** Metodo que cria o painel da cena Menu.
     * @param ci ControladorInterface - Controlador da interface.
     * @return BorderPane - Painel da cena Menu.
     */
    public BorderPane createPane (ControladorInterface ci) {
        BorderPane pane = (BorderPane) this.getPane();

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
        pane.setBackground(new Background(myBI));
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(0, 0, 100, 0));

        btn_comecar.setOnAction(e -> {
            ci.setIniciado(true);
            ci.pausar(false);
            ci.getEngine().resetarJogo();
            ci.setTelaAtual(null);
        });
        btn_instrucoes.setOnAction(e -> {
            // vai para a tela de instruções
            Instrucoes instrucoes = new Instrucoes();
            ci.mostrarPainel(instrucoes);
        });
    
        buttonsBox.getChildren().addAll(iv_logo, btn_comecar, btn_instrucoes);
        pane.setCenter(buttonsBox);

        this.setPane(pane);
        return pane;
    }
}
