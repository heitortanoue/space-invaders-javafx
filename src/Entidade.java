public class Entidade {

    private int larguraTela = 20;
    private int alturaTela = 20;
    private Tuple pos;
    private Tuple vel;
    
    public Entidade(Tuple posicao, Tuple velocidade) {
        this.pos = posicao;
        this.vel = velocidade;
    }

    public Tuple getPos () {
        return this.pos;
    }

    public void setPos (Tuple pos) {
        this.pos = pos;
    }

    public Tuple getVel () {
        return this.vel;
    }

    public void setVel (Tuple vel) {
        this.vel = vel;
    }

    public void deslocar() {
        this.pos.deslocar(this.vel.getX(), this.vel.getY());
    }

    public void deslocar(Tuple deslocamento) {
        this.pos.deslocar(deslocamento.getX(), deslocamento.getY());
    }

    public boolean colisaoEntidade(Entidade entidade) {
        return ( this.pos.distancia(e.getPos()) < 1 );
    }

    public void printConsole( char rep, Console c ) {
        c.setPixel(this.pos, rep);
    }

    public boolean colisaoTela() {
        // distancia menor que um ou passou dos limites da tela
        return ( 
            this.pos.getX() < 0 ||
            this.pos.getX() > this.larguraTela ||
            this.pos.getY() < 0 ||
            this.pos.getY() > this.alturaTela
            );
    }
}
