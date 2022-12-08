package InterfaceGrafica;

import ElementosDoSistema.Console;
import ElementosDoSistema.Imprimivel;
import ElementosDoSistema.Nave;
import ElementosDoSistema.Tela;
import ElementosDoSistema.Tuple;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
    public void imprimir(GraphicsContext gc) {
        // desenhar coracoes de acordo com as vidas
        Image coracao = new Image("Imagens/outros/heart.png");
        for (int i = 0; i < this.naveRef.getVidas(); i++) {
            gc.drawImage(coracao, 96 + i * 28, 44);
        }

        gc.fillText("<PONTOS>  " + this.naveRef.getPontos(), 16, 36);
        gc.fillText("<VIDAS>  ", 16, 66);
    }
}
