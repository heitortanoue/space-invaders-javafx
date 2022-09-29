public class Alien extends Entidade {

    private boolean vivo;
    private Tiro tiro;

    public Alien(Tuple posicao, Tuple velocidade) {
        super(posicao, velocidade);
        this.vivo = true;
        this.tiro = null;
    }

    public boolean getVivo() {
        return this.vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public Tiro getTiro() {
        return this.tiro;
    }

    public void setTiro(Tiro tiro) {
        this.tiro = tiro;
    }

    public void atirar() {
        if (this.tiro == null) {
            this.tiro = new Tiro(this.getPos(), new Tuple(0, 1));
        }
    }

    public boolean alinhadoComNave(Nave nave) {
        return ( this.getPos().getX() == nave.getPos().getX() );
    }
}
