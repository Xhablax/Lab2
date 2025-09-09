public class Player {
    private final String name; // identifica al jugador
    private int score;         // pares encontrados

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public void addPoint() { this.score++; }
}
