public abstract class Entidade extends Tela implements Imprimivel {
    private Tuple pos;
    private Tuple vel;
    // private int largura;
    // private int altura;
    
    public Entidade(Tuple posicao, Tuple velocidade) {
        super();
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
        // System.out.println("colisaoEntidade " + this.pos.distancia(entidade.getPos()));
        return ( this.pos.equals(entidade.pos) );
    }
 
    public void explodir(Console c) {
        c.setPixel(this.getPos(), '@');
    }

    public boolean colisaoTela() {
        // distancia menor que um ou passou dos limites da tela
        return ( 
            this.pos.getX() <= 0 ||
            this.pos.getX() >= this.getLarguraTela() - 1 ||
            this.pos.getY() <= 0 ||
            this.pos.getY() >= this.getAlturaTela()
            );
    }
}
