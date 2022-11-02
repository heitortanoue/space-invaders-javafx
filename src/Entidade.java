/** Classe abstrata para todos os objetos do jogo.
* @author Heitor Tanoue de Mello - 12547260
*/
public abstract class Entidade extends Tela implements Imprimivel {
    private Tuple pos;
    private Tuple vel;
    private Tuple virtualPos;
    
    public Entidade(Tuple posicao, Tuple velocidade) {
        super();
        this.virtualPos = posicao;
        posicao.intify();
        this.pos = posicao;
        this.vel = velocidade;
    }

    
    /** Getter para a posicao.
     * @return Tuple
     */
    public Tuple getPos () {
        return this.pos;
    }

    
    /** Setter para a posicao.
     * @param pos Tuple - Posicao a ser atribuida.
     */
    public void setPos (Tuple pos) {
        this.virtualPos = pos;
        this.pos = pos;
    }

    
    /** Getter para a velocidade.
     * @return Tuple
     */
    public Tuple getVel () {
        return this.vel;
    }

    
    /** Setter para a velocidade.
     * @param vel Tuple - Velocidade a ser atribuida.
     */
    public void setVel (Tuple vel) {
        this.vel = vel;
    }

    /** Metodo que atualiza a posicao da Entidade de acordo com sua velocidade atual.
     */
    public void deslocar() {
        this.virtualPos.deslocar(this.vel.getX(), this.vel.getY());
        this.setPos(this.virtualPos.intify());
    }

    
    /** Metodo que atualiza a posicao da Entidade de acordo com o valor passado como parametro.
     * @param deslocamento Tuple - Deslocamento a ser aplicado.
     */
    public void deslocar(Tuple deslocamento) {
        this.virtualPos.deslocar(deslocamento.getX(), deslocamento.getY());
        this.setPos(this.virtualPos.intify());

    }

    
    /** Metodo que checa se duas entidades colidiram.
     * @param entidade Entidade - Entidade a ser comparada.
     * @return boolean - Retorna true se a Entidade passada como parametro estiver na mesma posicao que a Entidade atual.
     */
    public boolean colisaoEntidade(Entidade entidade) {
        if (this.pos.equals(entidade.pos)) {
            return true;
        }
        if (Math.abs(this.getPos().getX() - entidade.getPos().getX()) <= 1 && Math.abs(this.getPos().getY() - entidade.getPos().getY()) <= 1) {      
            // Estão na mesma coluna Y
            if (this.getPos().getY() == entidade.getPos().getY()) {
                // Sentido contrário do movimento;
                return (this.getVel().getX() * entidade.getVel().getX() < 0);
            }
            // Estão na mesma linha X
            if (this.getPos().getX() == entidade.getPos().getX()) {
                // Sentido contrário do movimento;
                return (this.getVel().getY() * entidade.getVel().getY() < 0);
            }
        }
        return false;
    }
    
    /** Metodo que checa se a Entidade atual colidiu com uma das bordas da tela.
     * @return boolean
     */
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
