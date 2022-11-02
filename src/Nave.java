/** Classe para objetos do tipo Nave, que é objeto controlado pelo jogador.
* @author Heitor Tanoue de Mello - 12547260
*/
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

    
    /** Getter para o atributo tiro.
     * @return Tiro
     */
    public Tiro getTiro() {
        return this.tiro;
    }

    
    /** Getter para o atributo vidas.
     * @return int
     */
    public int getVidas() {
        return this.vidas;
    }

    
    /** Getter para o atributo pontos.
     * @return int
     */
    public int getPontos () {
        return this.pontos;
    }

    
    /** Incrementa os pontos da Nave.
     * @param pontos int - Valor a ser incrementado.
     */
    public void addPontos (int pontos) {
        this.pontos += pontos;
    }

    
    /** Retorna se a Nave está viva.
     * @return boolean - True se a Nave estiver viva, False se não.
    */
    public boolean getVivo() {
        return this.vidas > 0;
    }

    /** Decrementa uma vida da Nave.
     */
    public void decVidas () {
        this.vidas--;
    }

    /** Caso a Nave não possua nenhum Tiro, cria um novo Tiro que se move para cima.
     */
    public void atirar(){
        if (this.tiro == null) {
            Tuple pos = this.getPos().clone();
            pos.deslocar(0, -1);
            this.tiro = new Tiro(pos, new Tuple(0, -1));
        }
    }

    
    /** Metodo que coloca o conteudo da Nave no Console.
     * @param c Console - Console onde sera desenhado.
     */
    public void imprimir ( Console c ) {
        if (this.getVivo()) {
            c.setPixel(this.getPos(), 'N');
        }
    }

    
    /** Metodo que coloca o conteudo do Tiro da Nave no Console.
     * @param c Console - Console onde sera desenhado.
     */
    public void imprimirTiro ( Console c ) {
        if (this.tiro != null && !this.tiro.estaNaTela()) {
            this.tiro.imprimir(c);
            // System.out.println("[Tiro] P:" + this.tiro.getPos().toString() + " V:" + this.tiro.getVel().toString());
        } else {
            this.tiro = null;
        }
    }
}
