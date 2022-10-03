public class Tiro extends Entidade {
    
    private boolean visivel;

    public Tiro(Tuple posicao, Tuple velocidade) {
        super(posicao, velocidade);
        this.visivel = true;
    }

    public boolean getVisivel() {
        return this.visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public boolean estaNaTela () {
        return (this == null || !this.getVisivel() || this.colisaoTela());
    }

    public void imprimir ( Console c ) {
        if (this.getVisivel()) {
            c.setPixel(this.getPos(), '|');
        }
    }
}
