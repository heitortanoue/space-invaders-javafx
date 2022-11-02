/** Classe para objetos do tipo Alien, que são os inimigos do jogo e tem a habilidade de atirar e de se mover.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Alien extends Entidade {
    private boolean vivo;
    private Tiro tiro;

    private int tipo;

    public Alien(int tipo, Tuple posicao, Tuple velocidade) {
        super(posicao, velocidade);
        this.vivo = true;
        this.tiro = null;
        this.tipo = tipo;
    }
    
    /** Getter para o atributo vivo
     * @return boolean - Valor do atributo vivo
     */
    public boolean getVivo() {
        return this.vivo;
    }

    /** Retorna o tanto de pontos que o jogador ganha por matar um Alien
     * @return int - Pontos que o jogador ganha por matar esse tipo de Alien
     */
    public int getPontosPorMorte() {
        switch (this.tipo) {
            case 1:
                return 10;
            case 2:
                return 20;
            case 3:
                return 30;
            case 4:
                return 100;
            default:
                return 0;
        }
    }

    
    /** Setter para o atributo vivo
     * @param vivo boolean - Valor para o atributo vivo
     */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    
    /** Getter para o atributo tiro
     * @return Tiro - Tiro atribuido ao Alien
     */
    public Tiro getTiro() {
        return this.tiro;
    }

    
    /** Setter para o atributo tiro
     * @param tiro Tiro - Tiro a ser atribuido ao Alien
     */
    public void setTiro(Tiro tiro) {
        this.tiro = tiro;
    }

    
    /** Metodo que faz o alien atirar, gerando um Tiro que se desloca para baixo
     * @return Tiro - Tiro gerado
     */
    public Tiro atirar() {
        Tuple tiroPos = this.getPos().clone();
        tiroPos.deslocar(0, 1);
        return new Tiro(tiroPos, new Tuple(0, 0.5));
    }

    
    /** Metodo que imprime o Alien na tela
     * @param c Console - Console a ser desenhado
     */
    public void imprimir ( Console c ) {
        char caractere;
        switch (this.tipo) {
            case 1:
                caractere = 'A';
                break;
            case 2:
                caractere = 'B';
                break;
            case 3:
                caractere = 'C';
                break;
            case 4:
                caractere = 'E';
                break;
            default:
                caractere = 'A';
                break;
        }

        if (this.getVivo()) {
            c.setPixel(this.getPos(), caractere);
        }
    }
}
