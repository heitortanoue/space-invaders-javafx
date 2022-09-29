public class Exercito {
    public Alien[][] exercito;
    private Nave naveRef;
    private int numTiros;
    private Tuple velocidadeExercito;
    private int direcao;

    public Exercito (Nave naveRef) {
        this.naveRef = naveRef;
        this.exercito = new Alien[11][5];
        this.numTiros = 0;
        this.direcao = 1;
        this.velocidadeExercito = new Tuple(1, 0);
        this.mobilizarExercito();
    }

    private void addAlien (int linha, int coluna, Alien alien) {
        this.exercito[linha][coluna] = alien;
    }

    public int getNumTiros () {
        return this.numTiros;
    }

    public int addNumTiros () {
        return this.numTiros++;
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
        mudarVelocidadeExercito(this.velocidadeExercito.deslocar(1, 0));
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

        System.out.println("Mover exercito " + this.exercito[0][0].getPos().toString() + " " + this.exercito[10][0].getPos().toString());
    }

    public void atirar () {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                if (this.exercito[i][j].alinhadoComNave(naveRef)) {
                    this.exercito[i][j].atirar();
                    System.out.println("O ALIEN" + i + " " + j + " ATIROU " + this.exercito[i][j].getPos().toString());
                    this.addNumTiros();
                }
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

    public void imprimir ( Console c ) {
        for (int i = 0; i < this.exercito.length; i++) {
            for (int j = 0; j < this.exercito[i].length; j++) {
                if (this.exercito[i][j].getVivo()) {
                    c.setPixel(this.exercito[i][j].getPos(), 'A');
                }
            }
        }
    }
}
