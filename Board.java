import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
    private final int rows;
    private final int cols;
    private final Tile[][] grid;
    private final Random rng = new Random();

    private static final String[] EMOJIS = new String[] {
        "🍎","🍌","🍒","🍇","🍉","🍓","🥝","🍍","🥥","🥑",
        "🍑","🥕","🌽","🍆","🍋","🍊","🍐","🫐","🥔","🥦",
        "🐶","🐱","🐭","🐹","🐰","🦊","🐻","🐼","🐨","🐯",
        "⚽","🏀","🏈","⚾","🎾","🏐","🥎","🏉","🎱","🏓",
        "⭐","🌙","☀️","⚡","🔥","❄️","🌈","💧","🌟","🪐"
    };

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Tile[rows][cols];
        fillAndShuffle();
    }

    private void fillAndShuffle() {
        int total = rows * cols;
        int pairs = total / 2;

        List<String> symbols = new ArrayList<>();
        for (int i = 0; i < pairs; i++) {
            String base = (i < EMOJIS.length) ? EMOJIS[i] : "S" + (i + 1);
            symbols.add(base);
            symbols.add(base);
        }

        Collections.shuffle(symbols, rng);
        int k = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Tile(symbols.get(k));
                k++;
            }
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public Tile get(int r, int c) { return grid[r][c]; }

    public boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public boolean allMatched() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c].isMatched()) return false;
            }
        }
        return true;
    }
}
