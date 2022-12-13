package Engine;
import java.util.ArrayList;

import ElementosDoSistema.*;
import ElementosDoSistema.Entidades.Alien;
import ElementosDoSistema.Entidades.AlienEspecial;
import ElementosDoSistema.Entidades.Base;
import ElementosDoSistema.Entidades.Exercito;
import ElementosDoSistema.Entidades.Nave;
import ElementosDoSistema.Entidades.Tiro;
import InterfaceGrafica.ControladorInterface;
import InterfaceGrafica.Cenas.Derrota;
import InterfaceGrafica.Cenas.Vitoria;

/** Classe para objetos do tipo Engine, que é onde a dinamica de jogo é implementada.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Engine {
    private Nave n;
    private Exercito e;
    private Base[] b;
    private AlienEspecial a_esp;

    private int dificuldade = 1;

    /** Construtor da classe Engine.
     * @param nave Nave - Nave do jogo.
     * @param exercito Exercito - Exercito de aliens do jogo.
     * @param bases Base[] - Bases do jogo.
     * @param alien_esp AlienEspecial - Alien especial do jogo.
     */
    public Engine(Nave nave, Exercito exercito, Base[] bases, AlienEspecial alien_esp) {
        this.n = nave;
        this.e = exercito;
        this.b = bases;
        this.a_esp = alien_esp;

        this.resetarJogo();
    }

    /** Método que roda o jogo.
     */
    public void rodarJogo (ControladorInterface ci) {
            if (!this.n.getVivo() || this.e.getAlturaUltimoAlienVivo() >= b[0].getPos().getY() - 40) {
                ci.mostrarPainel(new Derrota());
                return; 
            }
            if (this.e.numAliensVivos() == 0) {
                ci.mostrarPainel(new Vitoria());
                return;
            }

            this.e.moverExercito();
            this.e.atirar();
            this.colisoesTiros(ci);
            this.n.moverNave();
            this.a_esp.moverAlienEspecial();

            this.aumentarDificuldade();
    }

    /** Método que reseta todas as entidades do jogo.
     */
    public void resetarJogo () {
        int larguraTela = n.getLarguraTela();
        int alturaTela = n.getAlturaTela();

        this.n.resetarNave();
        this.e.resetarExercito();
        this.b[0].resetarBase();
        this.b[1].resetarBase();
        this.b[2].resetarBase();
        this.a_esp.resetarAlien();

        this.e.setPosicaoExercito(new Tuple(0.15 * larguraTela, 0.15 * alturaTela), 10);
        this.b[0].setPos(new Tuple(0.15 * larguraTela, 0.75 * alturaTela));
        this.b[1].setPos(new Tuple(0.45 * larguraTela, 0.75 * alturaTela));
        this.b[2].setPos(new Tuple(0.75 * larguraTela, 0.75 * alturaTela));
        this.n.setPos(new Tuple(0.5 * larguraTela, 0.9 * alturaTela));
    }
   
    /** Método que aumenta a dificuldade do jogo com base na quantidade de aliens vivos
     */
    public void aumentarDificuldade () {
        int dificuldadeMaxima = 3;
        if (this.dificuldade == dificuldadeMaxima) {
            return;
        }

        double deltaDif = 100 / dificuldadeMaxima;
        int numAliensVivos = this.e.numAliensVivos();
        int numAliensTotal = this.e.getTamanhoExercito();
        double porcentagemAliensVivos = (double) numAliensVivos / numAliensTotal * 100;
        double minDif = (double) 1 / numAliensTotal * 100;

        if (Math.abs(porcentagemAliensVivos - (deltaDif * (dificuldadeMaxima - this.dificuldade))) <= minDif) {
            this.e.aumentarDificuldade();
            this.dificuldade++;
            return;
        }
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

    /** Método que verifica as colisoes entre os tiros passados e as bases do jogo.
     * @param tiros ArrayList<Tiro> - Lista de tiros.
     * @param ci ControladorInterface - Controlador da interface grafica.
     */
    private void colisaoComBase (ArrayList<Tiro> tiros, ControladorInterface ci) {
        tiros.forEach(tiro -> {
            if (tiro == null) {
                return;
            }

            // colisao com cada base
            for (Base base : this.b) {
                if (base.getVivo() && base.colisaoEntidade(tiro)) {

                    base.decVidas(1);
                    if (base.getVidas() == base.vidasMax/2) {
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

    /** Método que verifica as colisoes entre os tiros passados e eles mesmos.
     * @param tiros ArrayList<Tiro> - Lista de tiros.
     * @param ci ControladorInterface - Controlador da interface grafica.
     */
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

    /** Método que verifica as colisoes entre os tiros passados e os aliens do jogo.
     * @param tiros ArrayList<Tiro> - Lista de tiros.
     * @param ci ControladorInterface - Controlador da interface grafica.
     */
    private void colisaoComAliens (ArrayList<Tiro> tiros, ControladorInterface ci) {
        tiros.forEach(tiro -> {
            if (tiro == null) {
                return;
            }

            // colisao com alien especial
            if (this.a_esp.getVivo() && this.a_esp.colisaoEntidade(tiro)) {
                ci.explodir(this.a_esp, () -> {
                    this.a_esp.setVivo(false);
                });
                tiro.setVisivel(false);
                this.n.addPontos(this.a_esp.getPontosPorMorte());
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

    /** Método que verifica as colisoes entre os tiros passados e a nave do jogo.
     * @param tiros ArrayList<Tiro> - Lista de tiros.
     * @param ci ControladorInterface - Controlador da interface grafica.
     */
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

    /** Retorna a quantidade de pontos da nave.
     * @return int - Quantidade de pontos da nave.
     */
    public int getPontos () {
        return this.n.getPontos();
    }
}