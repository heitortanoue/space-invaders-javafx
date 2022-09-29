public class Nave extends Entidade {
    
    private Tiro tiro;
    private int pontos;
    private int vidas;
    
    public Nave (Tuple posicao, Tuple velocidade) {
        super(posicao, velocidade);
        this.tiro = null;
        this.pontos = 0;
        this.vidas = 3;
    }

    public Tiro getTiro() {
        return this.tiro;
    }

    public int getVidas() {
        return this.vidas;
    }

    public int getPontos () {
        return this.pontos;
    }

    public void addPontos (int pontos) {
        this.pontos += pontos;
    }

    public boolean getVivo() {
        return this.vidas > 0;
    }

    public void decVidas () {
        this.vidas--;
    }

    public void atirar(){
        if (this.tiro == null) {
            this.tiro = new Tiro(this.getPos(), new Tuple(0, 1));
        }
    }
}
