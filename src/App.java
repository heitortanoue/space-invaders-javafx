public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Começou o jogo!");

        Nave nave = new Nave(new Tuple(0, 0), new Tuple(0, 0));
        Exercito exercito = new Exercito(nave);
        Base[] bases = new Base[3];
        bases[0] = new Base(new Tuple(5, 0));
        bases[1] = new Base(new Tuple(15, 0));
        bases[2] = new Base(new Tuple(30, 0));

        Engine engine = new Engine(nave, exercito, bases);

        engine.rodarJogo();

        System.out.println("Fim do jogo!");
    }
}
