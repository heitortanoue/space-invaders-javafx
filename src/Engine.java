public class Engine {
    private Nave n;
    private Exercito e;
    private Base[] b;

    public Engine(Nave nave, Exercito exercito, Base[] bases) {
        this.n = nave;
        this.e = exercito;
        this.b = bases;
    }

    public void rodarJogo () {
        Console console = new Console(20, 20);
        console.clear();

        while (this.n.getVivo() && this.e.numAliensVivos() > 0) {
            this.e.moverExercito();
            this.e.atirar();
            this.e.imprimir(console);
            console.print();
            try {
                Thread.sleep(300);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
        }
    }
}