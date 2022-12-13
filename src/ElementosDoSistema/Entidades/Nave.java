package ElementosDoSistema.Entidades;

import ElementosDoSistema.Entidade;
import ElementosDoSistema.Tuple;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** Classe para objetos do tipo Nave, que é objeto controlado pelo jogador.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Nave extends Entidade {
    
    private Tiro tiro;
    private int pontos;
    private int vidas;
    private double velocidadePadrao = 6;
    
    /** Construtor da classe Nave.
     */
    public Nave () {
        super(new Tuple(0, 0), new Tuple(0, 0));
        this.resetarNave();
    }

    /** Setter para a velocidade da nave.
     * @param velocidadePadrao double - Valor para a velocidade da nave.
     */
    public void setVelocidadePadrao(double velocidadePadrao) {
        this.velocidadePadrao = velocidadePadrao;   
    }

    /** Getter para a velocidade da nave.
     * @return double - Valor da velocidade da nave.
     */
    public double getVelocidadePadrao() {
        return this.velocidadePadrao;
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
            pos.deslocar(this.getDimensoes().getX()/2 - 6, -this.getDimensoes().getY());
            this.tiro = new Tiro(pos, new Tuple(0, -4));
        }
    }

    /** Metodo que desloca a Nave de acordo com a sua velocidade.
     */
    public void moverNave() {
        if (!this.colisaoTelaComVelocidade()) {
            this.deslocar();
        }
    }

    /** Metodo que reseta a nave, suas vidas, velocidade, pontos e tiros.
     */
    public void resetarNave () {
        this.setVel(new Tuple(0, 0));
        this.setDimensoes(new Image("Imagens/nave/1.png"));
        this.tiro = null;
        this.pontos = 0;
        this.vidas = 3;
    }

    
    /** Metodo que coloca o conteudo da Nave no Console.
     * @param c Console - Console onde sera desenhado.
     */
    public void imprimir ( GraphicsContext gc ) {
        if (this.getVivo()) {
            this.desenhar(gc);
        }
    }

    
    /** Metodo que coloca o conteudo do Tiro da Nave no Console.
     * @param c Console - Console onde sera desenhado.
     */
    public void imprimirTiro ( GraphicsContext gc ) {
        if (this.tiro != null && !this.tiro.estaNaTela()) {
            this.tiro.imprimir(gc);
        } else {
            this.tiro = null;
        }
    }
}
