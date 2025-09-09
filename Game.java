public class Game {
    private final Board board;
    private final Player[] players;
    private int current;
    private GameMode mode;

    public Game(Board board, Player p1, Player p2, GameMode mode) {
        this.board = board;
        this.players = new Player[]{p1, p2};
        this.current = 0;
        this.mode = mode;
    }

    public Player getCurrentPlayer() { return players[current]; }
    public Player getOtherPlayer() { return players[(current + 1) % 2]; }
    public Board getBoard() { return board; }
    public GameMode getMode() { return mode; }
    public void setMode(GameMode mode) { this.mode = mode; }
    public boolean isFinished() { return board.allMatched(); }

    public boolean isValidPick(int r, int c) {
        return board.inBounds(r, c) && !board.get(r, c).isMatched();
    }

    public RevealResult playTurn(int r1, int c1, int r2, int c2) {
        Tile t1 = board.get(r1, c1);
        Tile t2 = board.get(r2, c2);

        boolean match = t1.getSymbol().equals(t2.getSymbol());
        if (match) {
            t1.setMatched(true);
            t2.setMatched(true);
            getCurrentPlayer().addPoint();
        } else {
            current = (current + 1) % 2;
        }
        return new RevealResult(r1, c1, t1.getSymbol(), r2, c2, t2.getSymbol(), match);
    }
}
