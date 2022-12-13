package ElementosDoSistema.Entidades;

import java.util.Timer;

import ElementosDoSistema.Tuple;

/** Classe que estende Alien, sendo um alien que fica passando, indo e voltando pela tela. Não tem a habilidade de atirar.
* @author Heitor Tanoue de Mello - 12547260
*/
public class AlienEspecial extends Alien {
    private Timer timer;

    /** Construtor da classe AlienEspecial
    */
    public AlienEspecial() {
        super(4, new Tuple(0, 0), new Tuple(-2, 0));
        this.setPos(new Tuple(this.getLarguraTela(), 0.1 * this.getAlturaTela()));
    }

    /** Método que desloca o AlienEspecial, caso ele esteja vivo, e o faz reaparecer caso ele tenha morrido
    */
    public void moverAlienEspecial () {
        // apos morrer, espera 5 segundos para reviver
        if (!this.getVivo() && this.timer == null) {
            this.timer = new Timer();
            this.timer.schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    reviverAlienEspecial();
                }
            }, 10000);
            return;
        }
        // caso passou da borda esquerda, teleporta para a direita
        if (this.getPos().getX() < -this.getDimensoes().getX()) {
            this.setPos(new Tuple(this.getLarguraTela() + this.getDimensoes().getX() + 20, this.getPos().getY()));
        }
        this.deslocar();
    }

    /** Método que revive o AlienEspecial
    */
    public void reviverAlienEspecial () {
        this.resetarAlien();
        this.setPos(new Tuple(this.getLarguraTela(), 0.1 * this.getAlturaTela()));
        this.timer = null;
    }
}
