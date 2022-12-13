package InterfaceGrafica;


import ElementosDoSistema.*;
import ElementosDoSistema.Entidades.AlienEspecial;
import ElementosDoSistema.Entidades.Base;
import ElementosDoSistema.Entidades.Exercito;
import ElementosDoSistema.Entidades.Nave;
import Engine.*;
import InterfaceGrafica.Cenas.Interface;
import InterfaceGrafica.Cenas.Menu;
import InterfaceGrafica.Cenas.Pause;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

/** Classe para objetos do tipo ControladorInterface, que é responsável por controlar a interface do jogo.
 * @author Heitor Tanoue de Mello - 12547260
 */
public class ControladorInterface extends Tela {
    private Image background;
    private Engine engine;

    private Pause pausado = null;
    private boolean iniciado = false;

    private Nave nave;
    private Exercito exercito;
    private Base[] bases;
    private Interface intf;
    private AlienEspecial a_esp;

    private GraphicsContext gc;
    private StackPane stkPane;
    private Scene scene;
    private Stage stage;
    private Tela telaAtual;

    /** Construtor da classe ControladorInterface
     * @param scene Scene - Cena da interface
     * @param stage Stage - Stage da interface
     * @param stkPane StackPane - StackPane da interface
     * @param gc GraphicsContext - GraphicsContext da interface
     * @param n Nave - Nave do jogo
     * @param e Exercito - Exercito do jogo
     * @param b Base[] - Bases do jogo
     * @param a_esp AlienEspecial - Alien especial do jogo
     */
    public ControladorInterface(Scene scene, Stage stage, StackPane stkPane, GraphicsContext gc, Nave n, Exercito e, Base[] b, AlienEspecial a_esp) {
        super();
        // this.background = new Image("Imagens/outros/fundo.png");
        // background do tamanho da tela
        this.background = new Image("Imagens/outros/fundo.png", n.getLarguraTela(), n.getAlturaTela(), false, false);
        this.engine = new Engine(n, e, b, a_esp);

        this.nave = n;
        this.exercito = e;
        this.bases = b;
        this.gc = gc;
        this.a_esp = a_esp;
        this.intf = new Interface(n);
        this.scene = scene;
        this.stage = stage;
        this.stkPane = stkPane;

        // Adicionar nova fonte e colocar cor certa
        Font novaFonte = Font.loadFont(ControladorInterface.class.getResource("/Outros/fonte.ttf").toExternalForm(), 16);
        gc.setFont( novaFonte );
        gc.setFill( Color.WHITE );
        this.comandosTeclado(n);
    }

    /** Lê os comandos do teclado
     * @param n Nave - Nave do jogo
     */
    private void comandosTeclado (Nave n) {
        ControladorInterface essaInterface = this;
        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        n.setVel(new Tuple(-n.getVelocidadePadrao(), 0));
                        break;
                    case RIGHT:
                        n.setVel(new Tuple(n.getVelocidadePadrao(), 0));
                        break;
                    case SPACE:
                        n.atirar();
                        break;
                    case P:
                        essaInterface.pausar();
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

    /** Loop principal do jogo
     */
    public void rodarLoop() {
        // nao comecou e botao nao pressionado
        if (!this.iniciado && this.telaAtual == null) {
            mostrarPainel(new Menu());
            return;
        }
        // botao pressionado, trocar cena
        if (this.telaAtual == null) this.stage.setScene(this.scene);
        if (!this.iniciado) return;

        // pausado
        if (this.pausado != null) {
            return;
        }

        this.gc.clearRect(0, 0, this.getLarguraTela(), this.getAlturaTela());
        this.gc.drawImage(this.background, 0, 0);

        this.engine.rodarJogo(this);

        this.imprimirTudo();
    }

    /** Imprime todas as entidades na tela
     */
    private void imprimirTudo () {
        this.nave.imprimir(this.gc);
        this.exercito.imprimir(this.gc);
        this.a_esp.imprimir(this.gc);

        this.exercito.imprimirTiros(this.gc);
        this.nave.imprimirTiro(this.gc);

        for (Base base : this.bases) {
            base.imprimir(this.gc);
        }

        this.intf.imprimir(gc);
    }

    /** Setter do atributo iniciado
     * @param iniciado boolean - Novo valor do atributo iniciado
     */
    public void setIniciado (boolean iniciado) {
        this.iniciado = iniciado;
    }
    /** Setter do atributo telaAtual
     * @param t Tela - Nova tela atual
     */
    public void setTelaAtual (Tela t) {
        this.telaAtual = t;
    }
    /** Setter para a cena 
     * @param s Scene - Nova cena atual
     */
    public void setScene (Scene s) {
        this.stage.setScene(s);
    }

    /** Pausa ou despausa o jogo
     */
    public void pausar () {
        if (this.pausado instanceof Pause) {
            this.stkPane.getChildren().remove(this.pausado.getPane());
            this.pausado = null;
            return;
        }

        this.pausado = new Pause();
        this.stkPane.getChildren().add(this.pausado.createPane(this));
    }
    /** Pausa ou despausa o jogo
     * @param p boolean - Se deve pausar ou despausar
     */
    public void pausar (boolean p) {
        // pausar
        if (p) {
            if (this.pausado instanceof Pause) {
                return;
            }
    
            this.pausado = new Pause();
            this.stkPane.getChildren().add(this.pausado.createPane(this));
        } 
        // despausar
        else {
            if (this.pausado instanceof Pause) {
                this.stkPane.getChildren().remove(this.pausado.getPane());
                this.pausado = null;
                return;
            }
        }
    }

    /** Explode uma entidade
     * @param ent Entidade - Entidade que será explodida!
     * @param callback Runnable - Callback a ser executado após a explosão
     */
    public void explodir (Entidade ent, Runnable callback) {
        // se classe é base
        if (ent instanceof Base) {
            ent.setImagem("Imagens/base/explosao.png");
        } else {
            ent.setImagem("Imagens/outros/explosao.png");
        }
        delay(500, callback);
    }

    /** Mostra um painel na tela
     * @param panel Painel - Painel a ser mostrado
     */
    public void mostrarPainel (Painel panel) {
        if (this.telaAtual != null && this.telaAtual.getClass() == panel.getClass()) return;
        this.telaAtual = panel;
        Scene scene = new Scene(panel.createPane(this), this.getLarguraTela(), this.getAlturaTela());
        this.stage.setScene(scene);
    }

    /** Getter do atributo engine
     * @return Engine - Engine do jogo
     */
    public Engine getEngine() {
        return this.engine;
    }

    /** Metodo que da um delay na execucao do programa
     * @param millis long - Tempo de delay em milisegundos
     * @param continuation Runnable - Callback a ser executado após o delay
     */
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
