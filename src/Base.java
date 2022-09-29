public class Base extends Entidade {
    private int vidas;

    public Base(Tuple posicao) {
        super(posicao, new Tuple(0, 0));
        this.vidas = 50;
    }

    public int getVidas() {
        return this.vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void decVidas( int dec ) {
        this.vidas -= dec;
    }
}
