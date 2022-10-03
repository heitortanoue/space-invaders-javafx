public class Base extends Entidade {
    private int vidas;
    private boolean vivo;

    public Base() {
        super(new Tuple(0, 0), new Tuple(0, 0));
        this.vidas = 3;
        this.vivo = true;
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

    public boolean getVivo() {
        return this.vidas > 0;
    }

    public void updateVivo() {
        if (this.vidas <= 0) {
            this.vivo = false;
        }
    }

    public void imprimir ( Console c ) {
        if (this.vivo) {
            c.setPixel(this.getPos(), 'B');
        }
    }
}
