import java.util.Random;

/**
 * Class for helper methods.
 */
public class Utils {

    /**
     * Static random value
     */
    private static Random rand;


    /**
     * Returns a random integer x so that 0 <= x <= max.
     */
    public static int random(int max) {
        if (rand == null) {
            rand = new Random();
        }
        return Math.abs(rand.nextInt()) % (max + 1);
    }

}
