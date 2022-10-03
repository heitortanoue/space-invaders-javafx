public class Nave extends Entidade {
    
    private Tiro tiro;
    private int pontos;
    private int vidas;
    
    public Nave () {
        super(new Tuple(0, 0), new Tuple(0, 0));
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
            Tuple pos = this.getPos().clone();
            pos.deslocar(0, -1);
            this.tiro = new Tiro(pos, new Tuple(0, -1));
        }
    }

    public void imprimir ( Console c ) {
        if (this.getVivo()) {
            c.setPixel(this.getPos(), 'N');
        }
    }

    public void imprimirTiro ( Console c ) {
        if (this.tiro != null && !this.tiro.estaNaTela()) {
            this.tiro.imprimir(c);
            // System.out.println("[Tiro] P:" + this.tiro.getPos().toString() + " V:" + this.tiro.getVel().toString());
        } else {
            this.tiro = null;
        }
    }

    public void lerTeclado ( char comando) {
        Tuple posAtual = this.getPos();
        switch (comando) {
            case 'a':
                posAtual.deslocar(-1, 0);
                break;
            case 'd':
                posAtual.deslocar(1, 0);
                break;
            case 'l':
                this.atirar();
                break;
            default:
                break;
        }
    }
}
