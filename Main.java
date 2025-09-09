import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Nombre del Jugador 1: ");
        String n1 = in.nextLine().trim();
        System.out.print("Nombre del Jugador 2: ");
        String n2 = in.nextLine().trim();
        Player p1 = new Player(n1.isEmpty() ? "Jugador 1" : n1);
        Player p2 = new Player(n2.isEmpty() ? "Jugador 2" : n2);

        Session session = new Session();

        boolean seguir = true;
        while (seguir) {
            int rows = leerEnteroEnRango(in, "Filas (1..20): ", 1, 20);
            int cols = leerEnteroEnRango(in, "Columnas (1..20): ", 1, 20);
            int total = rows * cols;

            while (total % 2 != 0) {
                System.out.println("El número de casillas debe ser PAR.");
                rows = leerEnteroEnRango(in, "Filas (1..20): ", 1, 20);
                cols = leerEnteroEnRango(in, "Columnas (1..20): ", 1, 20);
                total = rows * cols;
            }

            System.out.println("Modo de juego: 1) Normal  2) Difícil");
            int modoSel = leerEnteroEnRango(in, "Selecciona (1-2): ", 1, 2);
            GameMode mode = (modoSel == 1) ? GameMode.NORMAL : GameMode.DIFICIL;

            Board board = new Board(rows, cols);
            p1 = new Player(p1.getName());
            p2 = new Player(p2.getName());
            Game game = new Game(board, p1, p2, mode);

            while (!game.isFinished()) {
                Player actual = game.getCurrentPlayer();
                System.out.println("\nTurno de: " + actual.getName());
                imprimirTableroVisible(board, null, null, false);

                int r1 = leerEnteroEnRango(in, "Fila 1: ", 0, rows - 1);
                int c1 = leerEnteroEnRango(in, "Col 1: ", 0, cols - 1);
                while (!game.isValidPick(r1, c1)) {
                    System.out.println("Casilla inválida.");
                    r1 = leerEnteroEnRango(in, "Fila 1: ", 0, rows - 1);
                    c1 = leerEnteroEnRango(in, "Col 1: ", 0, cols - 1);
                }

                int r2 = leerEnteroEnRango(in, "Fila 2: ", 0, rows - 1);
                int c2 = leerEnteroEnRango(in, "Col 2: ", 0, cols - 1);
                while ((r1 == r2 && c1 == c2) || !game.isValidPick(r2, c2)) {
                    System.out.println("Casilla inválida o repetida.");
                    r2 = leerEnteroEnRango(in, "Fila 2: ", 0, rows - 1);
                    c2 = leerEnteroEnRango(in, "Col 2: ", 0, cols - 1);
                }

                RevealResult rr = game.playTurn(r1, c1, r2, c2);

                if (rr.isMatch) {
                    System.out.println("¡Pareja encontrada! Punto para " + actual.getName());
                    imprimirTableroVisible(board, null, null, false);
                } else {
                    if (mode == GameMode.NORMAL) {
                        System.out.println("No coinciden. Estas fueron tus cartas:");
                        imprimirTableroVisible(board, new int[]{rr.r1, rr.c1}, new int[]{rr.r2, rr.c2}, true);
                        System.out.print("Enter para continuar...");
                        in.nextLine();
                    } else {
                        System.out.println("No coinciden (modo Difícil). Ocultas sin mostrar.");
                    }
                    imprimirTableroVisible(board, null, null, false);
                }
            }

            System.out.println("\n=== Fin de la partida ===");
            System.out.println(p1.getName() + ": " + p1.getScore());
            System.out.println(p2.getName() + ": " + p2.getScore());
            System.out.println(p1.getScore() > p2.getScore() ? "Ganador: " + p1.getName() :
                               p2.getScore() > p1.getScore() ? "Ganador: " + p2.getName() : "Empate.");

            session.addGame(game);
            session.addToTotals(p1, p2);

            System.out.println("\n¿Jugar otra partida? 1) Sí  2) No");
            int otra = leerEnteroEnRango(in, "Selecciona (1-2): ", 1, 2);
            seguir = (otra == 1);
        }

        System.out.println("\n=== Resultados de la sesión ===");
        for (Map.Entry<String, Integer> e : session.getTotals().entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
        in.close();
    }

    private static int leerEnteroEnRango(Scanner in, String prompt, int min, int max) {
        System.out.print(prompt);
        int val = min;
        boolean ok = false;
        while (!ok) {
            try {
                val = Integer.parseInt(in.nextLine().trim());
                if (val < min || val > max) {
                    System.out.print("Fuera de rango. " + prompt);
                } else {
                    ok = true;
                }
            } catch (NumberFormatException ex) {
                System.out.print("Número inválido. " + prompt);
            }
        }
        return val;
    }

    private static void imprimirTableroVisible(Board b, int[] temp1, int[] temp2, boolean mostrarTemporales) {
        int rows = b.getRows(), cols = b.getCols();
        System.out.print("   ");
        for (int c = 0; c < cols; c++) System.out.print(String.format("%3d", c));
        System.out.println();
        for (int r = 0; r < rows; r++) {
            System.out.print(String.format("%3d", r));
            for (int c = 0; c < cols; c++) {
                Tile t = b.get(r, c);
                boolean esTemp = mostrarTemporales &&
                        ((temp1 != null && temp1[0] == r && temp1[1] == c) ||
                         (temp2 != null && temp2[0] == r && temp2[1] == c));
                String cell = "??";
                if (t.isMatched()) cell = t.getSymbol();
                else if (esTemp) cell = t.getSymbol();
                System.out.print(String.format("%3s", cell));
            }
            System.out.println();
        }
    }
}
