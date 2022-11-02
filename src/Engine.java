import java.util.ArrayList;
import java.util.Scanner;

/** Classe para objetos do tipo Engine, que é onde a dinamica de jogo é implementada.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Engine {
    private Nave n;
    private Exercito e;
    private Base[] b;
    private Interface i;

    public Engine(Interface itf, Nave nave, Exercito exercito, Base[] bases) {
        this.n = nave;
        this.e = exercito;
        this.b = bases;
        this.i = itf;
    }

    /** Método que roda o jogo.
     */
    public void rodarJogo () {
        Console console = new Console();
        InputRunnable inputRun = new InputRunnable();
        Thread inputThread = new Thread(inputRun);
        inputThread.start();

        this.parametrosIniciais(console);
        console.clear();

        // imprime a tela inicial e aguarda input do jogador
        console.telaInicial();
        console.print();
        while (true) {
            if (inputRun.getCommand() == 'p') {
                break;
            }
        }

        // imprime as instrucoes e aguarda input do jogador
        console.instrucoes();
        console.print();
        while (true) {
            if (inputRun.getCommand() == 'p') {
                break;
            }
        }


        while (this.n.getVivo() && this.e.numAliensVivos() > 0) {
            this.e.moverExercito();
            this.e.atirar(console);
            this.colisoesTiros(this.todosTiros(), console);

            this.lerTeclado(inputRun.getCommand());
            inputRun.setCommand('0');

            if (this.e.numAliensVivos() == 0) {
                console.clear();
                console.vitoria(this.n);
                console.print();
                break;
            }

            if (!this.n.getVivo() || this.e.getAlturaUltimoAlienVivo() > this.i.getDistanciaLimiteInferior()) {
                console.clear();
                console.derrota(n);
                console.print();
                break;
            }

            this.imprimirTudo(console);
            console.print();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    
    /** Método que imprime na tela o conteúdo de todos os objetos do jogo.
     * @param c Console - Console onde o conteúdo será impresso.
     */
    private void imprimirTudo ( Console c ) {
        // o que está mais para baixo (nas linhas de código) é impresso por cima
        this.n.imprimir(c);
        this.e.imprimir(c);

        this.e.imprimirTiros(c);
        this.n.imprimirTiro(c);

        for (Base base : this.b) {
            base.imprimir(c);
        }
        this.i.imprimir(c);
    }

    
    /** Método que transforma o input do jogador em ações.
     * @param comando char - Comando do jogador.
     */
    private void lerTeclado ( char comando ) {
        Tuple posAtual = this.n.getPos();
        switch (comando) {
            case 'a':
                if (posAtual.getX() > 0) {
                    posAtual.deslocar(-1, 0);
                }
                break;
            case 'd':
                if (posAtual.getX() < this.n.getLarguraTela() - 1) {
                    posAtual.deslocar(1, 0);
                }
                break;
            case 'l':
                this.n.atirar();
                break;

            // cheats
            case 'm':
                this.e.matarExercito();
                break;
            case 'p':
                this.n.addPontos(100);
                break;
            default:
                break;
        }
    }

    
    /** Método que move todos os tiros do jogo na direcao da velocidade.
     * @param tiros ArrayList<Tiro> - Lista de tiros do jogo.
     */
    private void moveTiros (ArrayList<Tiro> tiros) {
        //move todos os tiros
        if (!tiros.isEmpty()) {
            for (int i = 0; i < tiros.size(); i++) {
                Tiro tiroAtual = tiros.get(i);
                if (tiroAtual == null) {
                    continue;
                }
                tiroAtual.deslocar();
            }
        }
    }

    
    /** Método que retorna uma lista com todos os tiros do jogo.
     * @return ArrayList<Tiro> - Lista de tiros do jogo.
     */
    private ArrayList<Tiro> todosTiros () {
        ArrayList<Tiro> todosTiros = new ArrayList<Tiro>();
        todosTiros.addAll(this.e.getTiros());
        todosTiros.add(this.n.getTiro());
        return todosTiros;
    }

    
    /** Método que verifica se algum tiro colidiu com algum objeto do jogo.
     * @param todosTiros ArrayList<Tiro> - Lista de tiros do jogo.
     * @param c Console - Console onde o conteúdo será impresso.
     */
    private void colisoesTiros (ArrayList<Tiro> todosTiros, Console c) {
        moveTiros(todosTiros);
        
        todosTiros.forEach(tiro -> {
            if (tiro == null) {
                return;
            }

            // colisao com cada base
            for (Base base : this.b) {
                if (base.getVivo() && base.colisaoEntidade(tiro)) {
                    base.decVidas(1);
                    base.updateVivo();
                    if (!base.getVivo()) {
                        c.explodir(base);
                    }
                    tiro.setVisivel(false);
                    return;
                }
            }

            // colisao com todos os tiros antes dele, menos ele mesmo
            for (int i = 0; i < todosTiros.indexOf(tiro); i++) {
                Tiro tiroAtual = todosTiros.get(i);
                todosTiros.indexOf(tiro);
                if (tiroAtual == null) {
                    continue;
                }
                if (tiroAtual.colisaoEntidade(tiro)) {
                    c.explodir(tiro);
                    tiro.setVisivel(false);
                    tiroAtual.setVisivel(false);
                    return;
                }
            }

            // colisao com o exercito
            Alien alienColidido = this.e.alienColisao(tiro);
            if (alienColidido != null) {
                c.explodir(alienColidido);
                alienColidido.setVivo(false);
                tiro.setVisivel(false);
                this.n.addPontos(alienColidido.getPontosPorMorte());
                return;
            }

            // colisao com a nave
            if (this.n.colisaoEntidade(tiro)) {
                this.n.decVidas();
                tiro.setVisivel(false);
                return;
            }
        });
    }

    
    /** 
     * @param c
     */
    private void parametrosIniciais (Console c) {
        this.n.setPos(new Tuple(2, 1).relativoAoChao(c));

        int y = c.getDistanciaLimiteInferior();
        this.b[0].setPos(new Tuple(3, y));
        this.b[1].setPos(new Tuple(4, y));
        this.b[2].setPos(new Tuple(5, y));
        this.b[3].setPos(new Tuple(c.getLarguraTela() / 2, y));
        this.b[4].setPos(new Tuple(c.getLarguraTela() / 2 + 1, y));
        this.b[5].setPos(new Tuple(c.getLarguraTela() / 2 + 2, y));
        this.b[6].setPos(new Tuple(c.getLarguraTela() - 3, y));
        this.b[7].setPos(new Tuple(c.getLarguraTela() - 4, y));
        this.b[8].setPos(new Tuple(c.getLarguraTela() - 5, y));
    }
}

/** Classe para pegar os comandos do teclado.
* @author Heitor Tanoue de Mello - 12547260
*/
class InputRunnable implements Runnable {
    public volatile char command = '0';

    /** Método que inicializa o objeto.
     */
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            String line = in.nextLine();
            this.command = line.charAt(0);
        }
        in.close();
    }

    /** Método que retorna o comando atual.
     * @return char - Comando atual.
     */
    public char getCommand() {
        char command = this.command;
        this.resetCommand();
        return command;
    }

    /** Setter para o comando atual.
     * @param command char - Comando atual.
     */
    public void setCommand(char command) {
        this.command = command;
    }

    /** Método que reseta o comando atual.
     */
    public void resetCommand() {
        this.command = '0';
    }
}