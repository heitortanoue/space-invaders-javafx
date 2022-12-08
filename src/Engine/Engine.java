package Engine;
import java.util.ArrayList;
import java.util.Scanner;

import ElementosDoSistema.*;
import InterfaceGrafica.ControladorInterface;
import javafx.scene.canvas.GraphicsContext;

/** Classe para objetos do tipo Engine, que é onde a dinamica de jogo é implementada.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Engine {
    private Nave n;
    private Exercito e;
    private Base[] b;

    public Engine(Nave nave, Exercito exercito, Base[] bases) {
        this.n = nave;
        this.e = exercito;
        this.b = bases;
        int larguraTela = n.getLarguraTela();
        int alturaTela = n.getAlturaTela();

        this.e.setPosicaoExercito(new Tuple(0.15 * larguraTela, 0.1 * alturaTela), 10);
        this.b[0].setPos(new Tuple(0.15 * larguraTela, 0.75 * alturaTela));
        this.b[1].setPos(new Tuple(0.45 * larguraTela, 0.75 * alturaTela));
        this.b[2].setPos(new Tuple(0.75 * larguraTela, 0.75 * alturaTela));
        this.n.setPos(new Tuple(0.5 * larguraTela, 0.9 * alturaTela));
    }

    /** Método que roda o jogo.
     */
    public void rodarJogo (ControladorInterface ci) {
        // System.out.println("Jogo iniciado");
        // if (this.n.getVivo() && this.e.numAliensVivos() > 0) {
            if (!this.n.getVivo() || this.e.getAlturaUltimoAlienVivo() >= b[0].getPos().getY() - 40) {
                // System.out.println("GAME OVER");
                ci.mostrarTelaDerrota();
                return;
            }
            if (this.e.numAliensVivos() == 0) {
                System.out.println("VITORIA");
                return;
            }

            this.e.moverExercito();
            this.e.atirar();
            this.colisoesTiros(ci);
            this.n.moverNave();
        // }
    }

    public void resetarJogo () {
        int larguraTela = n.getLarguraTela();
        int alturaTela = n.getAlturaTela();

        this.n.resetarNave();
        this.e.resetarExercito();
        this.b[0].resetarBase();
        this.b[1].resetarBase();
        this.b[2].resetarBase();

        this.e.setPosicaoExercito(new Tuple(0.15 * larguraTela, 0.1 * alturaTela), 10);
        this.b[0].setPos(new Tuple(0.15 * larguraTela, 0.75 * alturaTela));
        this.b[1].setPos(new Tuple(0.45 * larguraTela, 0.75 * alturaTela));
        this.b[2].setPos(new Tuple(0.75 * larguraTela, 0.75 * alturaTela));
        this.n.setPos(new Tuple(0.5 * larguraTela, 0.9 * alturaTela));
    }
    
    /** Método que move todos os tiros do jogo na direcao da velocidade.
     * @param tiros ArrayList<Tiro> - Lista de tiros do jogo.
     */
    private void moveTiros (ArrayList<Tiro> tiros) {
        //move todos os tiros
        if (!tiros.isEmpty()) {
            for (int i = 0; i < tiros.size(); i++) {
                Tiro tiroAtual = tiros.get(i);
                if (tiroAtual == null) {
                    continue;
                }
                tiroAtual.deslocar();
            }
        }
    }

    /** Método que retorna uma lista com todos os tiros do jogo.
     * @return ArrayList<Tiro> - Lista de tiros do jogo.
     */
    private ArrayList<Tiro> todosTiros () {
        ArrayList<Tiro> todosTiros = new ArrayList<Tiro>();
        todosTiros.addAll(this.e.getTiros());
        todosTiros.add(this.n.getTiro());
        return todosTiros;
    }

    private void colisaoComBase (ArrayList<Tiro> tiros, ControladorInterface ci) {
        tiros.forEach(tiro -> {
            if (tiro == null) {
                return;
            }

            // colisao com cada base
            for (Base base : this.b) {
                if (base.getVivo() && base.colisaoEntidade(tiro)) {

                    base.decVidas(1);
                    if (base.getVidas() == 5) {
                        base.setImagem("Imagens/base/danificada.png");
                    }
                    if (base.getVidas() == 0) {
                        ci.explodir(base, () -> {
                            base.updateVivo();
                        });
                    }
                    tiro.setVisivel(false);
                    return;
                }
            }
        });
    }

    private void colisaoComTiros (ArrayList<Tiro> tiros, ControladorInterface ci) {
        tiros.forEach(tiro -> {
            if (tiro == null) {
                return;
            }

            // colisao com todos os tiros antes dele, menos ele mesmo
            for (int i = 0; i < tiros.indexOf(tiro); i++) {
                Tiro tiroAtual = tiros.get(i);
                tiros.indexOf(tiro);
                if (tiroAtual == null) {
                    continue;
                }
                if (tiroAtual.colisaoEntidade(tiro)) {
                    // ci.explodir(tiro);
                    tiro.setVisivel(false);
                    tiroAtual.setVisivel(false);
                    return;
                }
            }
        });
    }

    private void colisaoComAliens (ArrayList<Tiro> tiros, ControladorInterface ci) {
        tiros.forEach(tiro -> {
            if (tiro == null) {
                return;
            }

            // colisao com cada alien
            Alien alienColidido = this.e.alienColisao(tiro);
            if (alienColidido != null) {
                ci.explodir(alienColidido, () -> {
                    alienColidido.setVivo(false);
                });
                tiro.setVisivel(false);
                this.n.addPontos(alienColidido.getPontosPorMorte());
                return;
            }
        });
    }

    private void colisaoComNave (ArrayList<Tiro> tiros, ControladorInterface ci) {
        tiros.forEach(tiro -> {
            if (tiro == null) {
                return;
            }

            // colisao com a nave
            if (this.n.colisaoEntidade(tiro)) {
                this.n.decVidas();
                tiro.setVisivel(false);
                return;
            }
        });
    }
    
    /** Método que verifica se algum tiro colidiu com algum objeto do jogo.
     * @param todosTiros ArrayList<Tiro> - Lista de tiros do jogo.
     * @param c Console - Console onde o conteúdo será impresso.
     */
    private void colisoesTiros (ControladorInterface ci) {
        ArrayList<Tiro> todosTiros = this.todosTiros();
        moveTiros(todosTiros);
        
        ArrayList<Tiro> tirosNave = new ArrayList<Tiro>();
        tirosNave.add(this.n.getTiro());
        ArrayList<Tiro> tirosAliens = this.e.getTiros();

        this.moveTiros(todosTiros);

        this.colisaoComBase(todosTiros, ci);
        this.colisaoComTiros(todosTiros, ci);
        this.colisaoComAliens(tirosNave, ci);
        this.colisaoComNave(tirosAliens, ci);
    }
}