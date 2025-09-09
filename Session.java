import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private final List<Game> games = new ArrayList<>();
    private final Map<String, Integer> totalScores = new HashMap<>();

    public void addGame(Game g) { games.add(g); }

    public void addToTotals(Player p1, Player p2) {
        totalScores.put(p1.getName(), totalScores.getOrDefault(p1.getName(), 0) + p1.getScore());
        totalScores.put(p2.getName(), totalScores.getOrDefault(p2.getName(), 0) + p2.getScore());
    }

    public Map<String, Integer> getTotals() { return totalScores; }
    public List<Game> getGames() { return games; }
}
