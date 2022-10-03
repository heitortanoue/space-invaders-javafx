import java.util.ArrayList;
import java.util.Scanner;

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

    public void rodarJogo () {
        Console console = new Console();
        InputRunnable inputRun = new InputRunnable();
        Thread inputThread = new Thread(inputRun);
        inputThread.start();

        this.parametrosIniciais(console);
        console.clear();

        while (this.n.getVivo() && this.e.numAliensVivos() > 0) {
            this.e.moverExercito();
            this.e.atirar(console);
            this.colisoesTiros(console);

            this.n.lerTeclado(inputRun.getCommand());
            inputRun.setCommand('0');

            this.imprimirTudo(console);
            console.print();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

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

    private void moveTiros (ArrayList<Tiro> tiros) {
        //move todos os tiros
        if (!tiros.isEmpty()) {
            for (int i = 0; i < tiros.size(); i++) {
                Tiro tiroAtual = tiros.get(i);
                if (tiroAtual == null) {
                    continue;
                }
                tiros.get(i).deslocar();
            }
        }
    }

    private void colisoesTiros (Console c) {
        ArrayList<Tiro> todosTiros = this.e.getTiros();
        todosTiros.add(n.getTiro());

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
                        base.explodir(c);
                    }
                    tiro.setVisivel(false);
                    return;
                }
            }

            // colisao com outro tiro, menos ele mesmo
            todosTiros.forEach(tiro2 -> {
                if (tiro2 == null || tiro2 == tiro) {
                    return;
                }
                if (tiro.colisaoEntidade(tiro2)) {
                    tiro.explodir(c);
                    tiro.setVisivel(false);
                    tiro2.setVisivel(false);
                }
            });

            // colisao com o exercito
            Alien alienColidido = this.e.alienColisao(tiro);
            if (alienColidido != null) {
                alienColidido.explodir(c);
                alienColidido.setVivo(false);
                tiro.setVisivel(false);
                this.n.addPontos(10);
                System.out.println("Pontos adicionados");
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

    private void parametrosIniciais (Console c) {
        this.n.setPos(new Tuple(2, 1).relativoAoChao(c));
        this.b[0].setPos(new Tuple(3, 3).relativoAoChao(c));
        this.b[1].setPos(new Tuple(c.getLarguraTela() / 2, 3).relativoAoChao(c));
        this.b[2].setPos(new Tuple(c.getLarguraTela() - 3, 3).relativoAoChao(c));
        // this.n.setVel(new Tuple(0, 0));
    }
}

class InputRunnable implements Runnable {
    public volatile char command = '0';

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            String line = in.nextLine();
            this.command = line.charAt(0);
        }
        in.close();
    }

    public char getCommand() {
        return this.command;
    }

    public void setCommand(char command) {
        this.command = command;
    }
}