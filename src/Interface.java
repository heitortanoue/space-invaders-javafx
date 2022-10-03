public class Interface extends Tela implements Imprimivel {
    private Nave naveRef;

    public Interface(Nave nave) {
        super();
        this.naveRef = nave;
    }

    public void imprimir(Console c) {
        c.escreverNaTela(new Tuple(0, 0),
            "Pontos: " + this.naveRef.getPontos() + "   Vida: " + this.naveRef.getVidas()
        );
    }
}
