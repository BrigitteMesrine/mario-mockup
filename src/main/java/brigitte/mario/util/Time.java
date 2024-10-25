package brigitte.mario.util;

public class Time {
    // moment when game started (sort of .now)
    public static float timeStarted = System.nanoTime();

    // time elapsed ("now" minus timeStarted, converted to seconds (1s = 1E9 ns))
    public static float getTime() {
        return (float) ((System.nanoTime() - timeStarted) * 1E-9);
    }
}
