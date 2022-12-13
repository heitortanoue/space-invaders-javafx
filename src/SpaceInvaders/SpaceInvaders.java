package SpaceInvaders;

import ElementosDoSistema.*;
import ElementosDoSistema.Entidades.AlienEspecial;
import ElementosDoSistema.Entidades.Base;
import ElementosDoSistema.Entidades.Exercito;
import ElementosDoSistema.Entidades.Nave;
import InterfaceGrafica.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.animation.AnimationTimer;

/** Classe principal.
* @author Heitor Tanoue de Mello - 12547260
*/
public class SpaceInvaders extends Application {

    @Override
    public void start (Stage stage) {
        Nave nave = new Nave();
        Exercito exercito = new Exercito(nave);
        Base[] bases = new Base[3];
        for (int i = 0; i < 3; i++) {
            bases[i] = new Base();
        }
        AlienEspecial alien_esp = new AlienEspecial();

        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(nave.getLarguraTela(), nave.getAlturaTela());
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        ControladorInterface engineInterface = new ControladorInterface(scene, stage, root, gc, nave, exercito, bases, alien_esp);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                engineInterface.rodarLoop();
            }
        };
        timer.start();

        stage.setScene(scene);
        stage.setTitle("Space Invaders by Heitor Tanoue");
        stage.setResizable(false);
        stage.getIcons().add(new Image("Imagens/outros/icon.png"));
        stage.show();
    }
    
    /** 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
