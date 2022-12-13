package InterfaceGrafica.Cenas;

import java.util.ArrayList;

import InterfaceGrafica.*;
import InterfaceGrafica.Componentes.BotaoEstilizado;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/** Classe para objetos do tipo Instrucoes, que é uma cena que aparece quando o jogador clica no botao de instrucoes.
 * @author Heitor Tanoue de Mello - 12547260
 */
public class Instrucoes extends Painel {
    /** Construtor da classe Instrucoes.
     */
    public Instrucoes () {
        super(new BorderPane());
    }

    /** Metodo que cria o painel da cena Instrucoes.
     * @param ci ControladorInterface - Controlador da interface.
     * @return BorderPane - Painel da cena Instrucoes.
     */
    public BorderPane createPane (ControladorInterface ci) {
        BorderPane pane = (BorderPane) this.getPane();
        VBox centerBox = new VBox();
        centerBox.setSpacing(20);

        Font fontTitle = Font.loadFont(Instrucoes.class.getResource("/Outros/fonte.ttf").toExternalForm(), 36);
        Text title = new Text("Instrucoes");
        title.setFont(fontTitle);
        title.setFill(Color.WHITE);

        HBox buttonsBox = new HBox();
        BotaoEstilizado btn_voltar = new BotaoEstilizado("Voltar ao menu");
        BotaoEstilizado btn_jogar = new BotaoEstilizado("Jogar");

        VBox textosBox = new VBox();
        textosBox.setSpacing(5);
        Font novaFonte = Font.loadFont(Instrucoes.class.getResource("/Outros/fonte.ttf").toExternalForm(), 18);
        ArrayList<Text> textos = new ArrayList<Text>();
        textos.add(new Text("O objetivo do jogo e destruir todos os inimigos"));
        textos.add(new Text("que aparecem na tela. Para isso, voce deve"));
        textos.add(new Text("utilizar as setas do teclado para se movimentar"));
        textos.add(new Text("e a barra de espaco para atirar."));
        for (Text texto : textos) {
            texto.setFont(novaFonte);
            texto.setFill(Color.WHITE);
            // texto.setStroke(Color.BLACK);
            // texto.setStrokeWidth(2);
            textosBox.getChildren().add(texto);
        }

        HBox aliensBox = new HBox();
        aliensBox.setSpacing(30);
        aliensBox.setAlignment(Pos.CENTER);

        String alienPaths[] = {"alien1/1.png", "alien2/1.png", "alien3/1.png", "especial/1.png"};
        String alienPoints[] = {"10 pontos", "20 pontos", "30 pontos", "?? pontos"};
        String alienPath = "Imagens/aliens/";
        int quantidadeAliens = alienPaths.length;

        Font novaFonteMenor = Font.loadFont(Instrucoes.class.getResource("/Outros/fonte.ttf").toExternalForm(), 14);
        for (int i = 0; i < quantidadeAliens; i++) {
            VBox alienBox = new VBox();
            alienBox.setSpacing(5);
            alienBox.setAlignment(Pos.CENTER);

            Image alienImage = new Image(alienPath + alienPaths[i]);
            ImageView alienImageView = new ImageView(alienImage);
            alienImageView.setPreserveRatio(true);
            alienImageView.setFitWidth(60);

            Text alienPointsText = new Text(alienPoints[i]);
            alienPointsText.setFont(novaFonteMenor);
            alienPointsText.setFill(Color.WHITE);
            alienBox.getChildren().addAll(alienImageView, alienPointsText);
            aliensBox.getChildren().add(alienBox);
        }

        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(10, 0, 0, 0));

        btn_jogar.setOnAction(e -> {
            ci.setIniciado(true);
            ci.pausar(false);
            ci.getEngine().resetarJogo();
            ci.setTelaAtual(null);
        });
        btn_voltar.setOnAction(e -> {
            // volta para o menu
            ci.setTelaAtual(null);
        });
    
        buttonsBox.getChildren().addAll(btn_voltar, btn_jogar);

        centerBox.getChildren().addAll(title, textosBox, aliensBox, buttonsBox);
        centerBox.setFillWidth(false);
        centerBox.setAlignment(Pos.CENTER);

        pane.setCenter(centerBox);

        this.setPane(pane);
        return pane;
    }
}

