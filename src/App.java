public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Começou o jogo");

        Nave nave = new Nave();
        Exercito exercito = new Exercito(nave);
        Interface intfc = new Interface(nave);
        Base[] bases = new Base[3];
        bases[0] = new Base();
        bases[1] = new Base();
        bases[2] = new Base();

        Engine engine = new Engine(intfc, nave, exercito, bases);

        engine.rodarJogo();

    }
}
