/** Classe para objetos do tipo Nave, que é objeto controlado pelo jogador.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Tiro extends Entidade {
    
    private boolean visivel;

    public Tiro(Tuple posicao, Tuple velocidade) {
        super(posicao, velocidade);
        this.visivel = true;
    }

    
    /** Getter para o atributo visivel.
     * @return boolean
     */
    public boolean getVisivel() {
        return this.visivel;
    }

    
    /** Setter para o atributo visivel.
     * @param visivel boolean - Valor a ser atribuido.
     */
    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    
    /** Retorna se o Tiro está visivel.
     * @return boolean - True se o Tiro estiver visivel, False se não.
     */
    public boolean estaNaTela () {
        return (this == null || !this.getVisivel() || this.colisaoTela());
    }

    
    /** Coloca o conteudo do Tiro no Console.
     * @param c Console - Console onde sera impresso.
     */
    public void imprimir ( Console c ) {
        if (this.getVisivel()) {
            if (this.getVel().getY() < 0) {
                c.setPixel(this.getPos(), '^');
            } else {
                c.setPixel(this.getPos(), 'v');
            }
            // c.setPixel(this.getPos(), '|');
        }
    }
}
