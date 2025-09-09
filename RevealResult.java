public class RevealResult {
    // DTO para comunicar a Main lo que se debe mostrar
    public final int r1, c1, r2, c2;
    public final String s1, s2;
    public final boolean isMatch;

    public RevealResult(int r1, int c1, String s1, int r2, int c2, String s2, boolean isMatch) {
        this.r1 = r1; this.c1 = c1; this.s1 = s1;
        this.r2 = r2; this.c2 = c2; this.s2 = s2;
        this.isMatch = isMatch;
    }
}
