public class Tile {
    private final String symbol; // (símbolo único del par)
    private boolean matched;     // (true si ya fue emparejada)

    public Tile(String symbol) {
        this.symbol = symbol;
        this.matched = false;
    }

    public String getSymbol() { return symbol; }             // retorna símbolo
    public boolean isMatched() { return matched; }           // indica si ya está emparejada
    public void setMatched(boolean matched) { this.matched = matched; } // marca como emparejada
}
