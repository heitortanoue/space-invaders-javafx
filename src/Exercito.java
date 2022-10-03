import java.util.ArrayList;
import java.util.Random;

public class Exercito {
    public Alien[][] exercito;
    private Nave naveRef;

    private ArrayList<Tiro> tiros = new ArrayList<Tiro>();

    private Tuple velocidadeExercito;
    private int direcao;

    public Exercito (Nave naveRef) {
        this.naveRef = naveRef;
        this.exercito = new Alien[11][5];
        // this.numTiros = 0;
        this.direcao = 1;
        this.velocidadeExercito = new Tuple(1, 0);
        this.mobilizarExercito();
    }

    public ArrayList<Tiro> getTiros() {
        return this.tiros;
    }

    private void addAlien (int linha, int coluna, Alien alien) {
        this.exercito[linha][coluna] = alien;
    }

    public void mobilizarExercito () {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                this.addAlien(i, j, new Alien(new Tuple(i, j), this.velocidadeExercito));
            }
        }
    }

    private void mudarVelocidadeExercito (Tuple v) {
        if (v.getX() > 0) {
            this.direcao = 1;
        } else if (v.getX() < 0) {
            this.direcao = -1;
        } else {
            this.direcao = 0;
        }

        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                this.exercito[i][j].setVel(v);
            }
        }

        this.velocidadeExercito = v;
    }

    public void acelerarExercito () {
        Tuple novaVelocidade = this.velocidadeExercito;
        novaVelocidade.deslocar(1, 0);
        mudarVelocidadeExercito(novaVelocidade);
    }

    public void moverExercito () {
        if (this.direcao == -1 && this.exercito[0][0].colisaoTela() ||
        this.direcao == 1 && this.exercito[this.exercito.length - 1][0].colisaoTela()) {
            Tuple velAtual = this.velocidadeExercito;
            velAtual.setX(velAtual.getX() * -1);
            this.mudarVelocidadeExercito(new Tuple(0, 1));
            this.moverExercito();
            this.mudarVelocidadeExercito(velAtual);
        }

        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                this.exercito[i][j].deslocar();
            }
        }

        // System.out.println("Mover exercito " + this.exercito[0][0].getPos().toString() + " " + this.exercito[10][0].getPos().toString());
    }

    public void atirar ( Console c ) {
        if (tiros.size() >= 2) {
            return;
        }

        // tiro randomico
        Random rand = new Random();
        int randLinha = rand.nextInt(this.exercito.length - 1);
        for (int i = this.exercito[0].length - 1; i >= 0; i--) {
            this.tiros.add(this.exercito[randLinha][i].atirar());
            break;
        }

        // checa se a nave esta alinhada com alguma coluna do exercito
        int xNave = this.naveRef.getPos().getX();
        if (this.exercito[0][0].getPos().getX() > xNave ||
            this.exercito[this.exercito.length - 1][0].getPos().getX() < xNave) {
            return;
        }

        // tiro focado na nave
        int xRelative = xNave - this.exercito[0][0].getPos().getX();
        for (int j = this.exercito[0].length - 1; j >= 0; j--) {
            if (this.exercito[xRelative][j].getVivo()) {
                this.tiros.add(this.exercito[xRelative][j].atirar());
                // System.out.println("tiros[0] = " + this.tiros.get(0).getPos().toString());
                break;
            }
        }
    }

    public int numAliensVivos () {
        int numAliensVivos = 0;
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                if (this.exercito[i][j].getVivo()) {
                    numAliensVivos++;
                }
            }
        }
        return numAliensVivos;
    }

    public Alien alienColisao ( Tiro t ) {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                if (this.exercito[i][j].colisaoEntidade(t)) {
                    return this.exercito[i][j];
                }
            }
        }
        return null;
    }

    public void imprimir ( Console c ) {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                if (this.exercito[i][j].getVivo()) {
                    this.exercito[i][j].imprimir(c);
                }
            }
        }
    }

    public void imprimirTiros ( Console c ) {
        for (int i = 0; i < this.tiros.size(); i++) {
            Tiro tiroAtual = this.tiros.get(i);
            if (tiroAtual == null || !tiroAtual.getVisivel() || tiroAtual.colisaoTela()) {
                this.tiros.remove(i);
                i--;
            } else {
                tiroAtual.imprimir(c);
            }
        }
    }
}
