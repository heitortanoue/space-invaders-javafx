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

    public Tiro atirar() {
        Tuple tiroPos = this.getPos().clone();
        tiroPos.deslocar(0, 1);
        return new Tiro(tiroPos, new Tuple(0, 1));
    }

    public void imprimir ( Console c ) {
        if (this.getVivo()) {
            c.setPixel(this.getPos(), 'A');
        }
    }
}
