package ElementosDoSistema;
/** Classe para objetos do tipo Tuple, que sao auxiliares para a representacao de coordenadas e vetores.
* @author Heitor Tanoue de Mello - 12547260
*/
public class Tuple {
    private double x;
    private double y;
    
    /** Construtor da classe Tuple.
     * @param x double - Valor para o atributo x.
     * @param y double - Valor para o atributo y.
     */
    public Tuple(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    
    /** Getter para o atributo x.
     * @return double
     */
    public double getX() {
        return x;
    }
    
    
    /** Getter para o atributo y.
     * @return double
     */
    public double getY() {
        return y;
    }
    
    
    /** Setter para o atributo x.
     * @param x double - Valor a ser atribuido.
     */
    public void setX(double x) {
        this.x = x;
    }
    
    
    /** Setter para o atributo y.
     * @param y double - Valor a ser atribuido.
     */
    public void setY(double y) {
        this.y = y;
    }
    
    
    /** Setter para o atributo x e y.
     * @param x double - Valor a ser atribuido.
     * @param y double - Valor a ser atribuido.
     */
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    
    /** Desloca o Tuple em x e y.
     * @param dx double - Valor a ser somado a x.
     * @param dy double - Valor a ser somado a y.
     */
    public void deslocar(double dx, double dy) {
        x += dx;
        y += dy;
    }

    
    /** Transforma o Tuple em uma String.
     * @return String - String com os valores de x e y.
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    
    /** Retorna um novo Tuple com os mesmos valores de x e y.
     * @return Tuple - Novo Tuple com os mesmos valores de x e y.
     */
    public Tuple clone() {
        return new Tuple(this.getX(), this.getY());
    }

    
    /** Retorna se o Tuple é igual a outro.
     * @param t Tuple - Tuple a ser comparado.
     * @return boolean - True se forem iguais, False se não.
     */
    public boolean equals ( Tuple t ) {
        return (this.getX() == t.getX() && this.getY() == t.getY());
    }

    
    /** Arredonda os valores de x e y para inteiros.
     * @return Tuple - Novo Tuple com os valores de x e y arredondados.
     */
    public Tuple intify() {
        return new Tuple(Math.ceil(this.x), Math.ceil(this.y));
    }
}
