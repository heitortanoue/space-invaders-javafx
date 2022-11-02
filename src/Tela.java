/** Classe abstrada Tela, que da a proporcao da tela de jogo, distancias e outras definicoes.
* @author Heitor Tanoue de Mello - 12547260
*/
public abstract class Tela {
    // colocar -2 em cada para impressao da borda
    private int larguraTela = 40;
    private int alturaTela = 25;

    // distancia da parte de baixo da tela até as bases
    private int distanciaLimiteInferior = 5;

    /** Getter para o atributo larguraTela.
     * @return int
     */
    public int getLarguraTela () {
        return this.larguraTela;
    }

    
    /** Getter para o atributo alturaTela.
     * @return int
     */
    public int getAlturaTela () {
        return this.alturaTela;
    }

    
    /** Retorna o y (altura) das bases.
     * @return int
     */
    public int getDistanciaLimiteInferior () {
        return (this.alturaTela - this.distanciaLimiteInferior);
    }
}
