/** Classe para objetos do tipo Interface, que mostra o conteudo do jogo no Console (pontos, vidas...).
* @author Heitor Tanoue de Mello - 12547260
*/
public class Interface extends Tela implements Imprimivel {
    private Nave naveRef;

    public Interface(Nave nave) {
        super();
        this.naveRef = nave;
    }

    
    /** Metodo que coloca o conteudo a Interface no Console.
     * @param c Console - Console onde sera desenhado.
     */
    public void imprimir(Console c) {
        c.escreverNaTela(new Tuple(0, 0),
            "Pontos: " + this.naveRef.getPontos() + "   Vida: " + this.naveRef.getVidas()
        );
    }
}
