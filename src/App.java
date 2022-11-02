/** Classe principal.
* @author Heitor Tanoue de Mello - 12547260
*/
public class App {
    
    /** 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Nave nave = new Nave();
        Exercito exercito = new Exercito(nave);
        Interface intfc = new Interface(nave);
        Base[] bases = new Base[9];
        bases[0] = new Base();
        bases[1] = new Base();
        bases[2] = new Base();
        bases[3] = new Base();
        bases[4] = new Base();
        bases[5] = new Base();
        bases[6] = new Base();
        bases[7] = new Base();
        bases[8] = new Base();

        Engine engine = new Engine(intfc, nave, exercito, bases);

        engine.rodarJogo();

    }
}
