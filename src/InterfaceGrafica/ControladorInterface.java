package InterfaceGrafica;

import javax.swing.text.html.HTMLDocument.RunElement;

import ElementosDoSistema.*;
import Engine.*;
import InterfaceGrafica.*;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class ControladorInterface extends Tela {
    private Image background;
    private Engine engine;

    private boolean pausado = false;
    private boolean iniciado = false;

    private Nave nave;
    private Exercito exercito;
    private Base[] bases;
    private Interface intf;

    private GraphicsContext gc;
    private Scene scene;
    private Stage stage;
    // private Menu menu;
    private Tela telaAtual;

    public ControladorInterface(Scene scene, Stage stage, GraphicsContext gc, Nave n, Exercito e, Base[] b) {
        super();
        this.background = new Image("Imagens/outros/fundo.png");
        this.engine = new Engine(n, e, b);

        this.nave = n;
        this.exercito = e;
        this.bases = b;
        this.gc = gc;
        this.intf = new Interface(n);
        this.scene = scene;
        this.stage = stage;

        // Adicionar nova fonte e colocar cor certa
        Font novaFonte = Font.loadFont(ControladorInterface.class.getResource("/Outros/fonte.ttf").toExternalForm(), 16);
        gc.setFont( novaFonte );
        gc.setFill( Color.WHITE );
        this.comandosTeclado(n);
    }

    private void comandosTeclado (Nave n) {
        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // System.out.println("[Distancias] ESQUERDA: " + n.getPos().getX() + " | DIREITA: " + (n.getLarguraTela() - n.getPos().getX() - n.getDimensoes().getX()));
                switch (event.getCode()) {
                    case LEFT:
                        // double distanciaEsq = n.getPos().getX();
                        // if (distanciaEsq <= 0) {
                        //     //n.setPos(new Tuple(0, n.getPos().getY()));
                        //     n.setVel(new Tuple(0, 0));
                        //     break;
                        // }
                        n.setVel(new Tuple(-n.getVelocidadePadrao(), 0));
                        break;
                    case RIGHT:
                        // double distanciaDir = n.getLarguraTela() - (n.getPos().getX() + n.getDimensoes().getX());
                        // if (distanciaDir <= 0) {
                        //     //n.setPos(new Tuple(n.getLarguraTela(), n.getPos().getY()));
                        //     n.setVel(new Tuple(0, 0));
                        //     break;
                        // }
                        n.setVel(new Tuple(n.getVelocidadePadrao(), 0));
                        break;
                    case SPACE:
                        n.atirar();
                        break;
                    case P:
                        pausado = !pausado;
                        break;
                    case ENTER:
                        iniciado = true;
                        break;
                    case ESCAPE:
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        n.setVel(new Tuple(0, 0));
                        break;
                    case RIGHT:
                        n.setVel(new Tuple(0, 0));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void rodarLoop() {
        // nao comecou e botao nao pressionado
        if (!this.iniciado && this.telaAtual == null) {
            Menu newMenu = new Menu();
            this.telaAtual = newMenu;
            Scene scene = new Scene(newMenu.getMenuScene(this), this.getLarguraTela(), this.getAlturaTela());
            stage.setScene(scene);
            return;
        }
        // botao pressionado, trocar cena
        if (this.telaAtual == null) this.stage.setScene(this.scene);
        if (!this.iniciado) return;

        this.gc.clearRect(0, 0, this.getLarguraTela(), this.getAlturaTela());
        this.gc.drawImage(this.background, 0, 0);

        this.engine.rodarJogo(this);

        this.imprimirTudo();
    }

    private void imprimirTudo () {
        this.nave.imprimir(this.gc);
        this.exercito.imprimir(this.gc);

        this.exercito.imprimirTiros(this.gc);
        this.nave.imprimirTiro(this.gc);

        for (Base base : this.bases) {
            base.imprimir(this.gc);
        }

        this.intf.imprimir(gc);
    }

    public void setIniciado (boolean iniciado) {
        this.iniciado = iniciado;
    }
    public void setTelaAtual (Menu menu) {
        this.telaAtual = menu;
    }

    public void pausar () {
        this.pausado = !this.pausado;
    }

    public void explodir (Entidade ent, Runnable callback) {
        // se classe é base
        if (ent instanceof Base) {
            ent.setImagem("Imagens/base/explosao.png");
        } else {
            ent.setImagem("Imagens/outros/explosao.png");
        }
        delay(500, callback);
    }

    public void mostrarTelaDerrota () {
        if (this.telaAtual instanceof Derrota) return;
        Derrota newDerrota = new Derrota();
        this.telaAtual = newDerrota;
        Scene scene = new Scene(newDerrota.getDerrotaScene(this), this.getLarguraTela(), this.getAlturaTela());
        this.stage.setScene(scene);
    }

    public Engine getEngine() {
        return this.engine;
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
      }
}
