package ElementosDoSistema;

import java.util.Timer;

public class AlienEspecial extends Alien {
    private Timer timer;

    public AlienEspecial() {
        super(4, new Tuple(0, 0), new Tuple(-2, 0));
        this.setPos(new Tuple(this.getLarguraTela(), 0.1 * this.getAlturaTela()));
    }

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

    public void reviverAlienEspecial () {
        this.resetarAlien();
        this.setPos(new Tuple(this.getLarguraTela(), 0.1 * this.getAlturaTela()));
        this.timer = null;
    }
}
