/**
 * A utility class to handle emotion values. Emotion values are integers,
 * therefore no emotion objects are used. This class contains methods to define
 * and convert emotion values.
 */
public class Emotion {

    /**
     * Indicates the emotional state of neutrality.
     */
    public static final int NEUTRAL = 0;

    /**
     * Indicates the emotional state of excitement.
     */
    public static final int EXCITED = 1;

    /**
     * Indicates the emotional state of elation.
     */
    public static final int ELATED = 2;

    /**
     * Indicates the emotional state of joy.
     */
    public static final int HAPPY = 3;

    /**
     * Indicates the emotional state of contentment.
     */
    public static final int CONTENT = 4;

    /**
     * Indicates the emotional state of sleepiness.
     */
    public static final int TIRED = 5;

    /**
     * Indicates the emotional state of boredom.
     */
    public static final int BORED = 6;

    /**
     * Indicates the emotional state of sadness.
     */
    public static final int SAD = 7;

    /**
     * Indicates the emotional state of anger.
     */
    public static final int ANGRY = 8;
    public static final int MIN_LEVEL = -20;
    public static final int MAX_LEVEL = 20;

    /**
     * Private constructor, prevents creation of emotion objects.
     */
    private Emotion() {
    }

    /**
     * Selects an emotion according to the given activation and pleasure.
     */
    public static int select(int activation, int pleasure) {

        if (neutral(activation) && neutral(pleasure)) {
            return NEUTRAL;
        } else if (high(activation) && neutral(pleasure)) {
            return EXCITED;
        } else if (high(activation) && high(pleasure)) {
            return ELATED;
        } else if (neutral(activation) && high(pleasure)) {
            return HAPPY;
        } else if (low(activation) && high(pleasure)) {
            return CONTENT;
        } else if (neutral(activation) && low(pleasure)) {
            return TIRED;
        } else if (low(activation) && low(pleasure)) {
            return BORED;
        } else if (low(activation) && neutral(pleasure)) {
            return SAD;
        } else if (high(activation) && low(pleasure)) {
            return ANGRY;
        } else {
            throw new IllegalArgumentException("Parameter out of range: "
                    + activation + ", " + pleasure);

        }
    }

    /**
     * Helper method
     */
    private static boolean low(int level) {
        return (level < MIN_LEVEL);
    }

    /**
     * Helper method
     */
    private static boolean neutral(int level) {
        return (level >= MIN_LEVEL && level <= MAX_LEVEL);
    }

    /**
     * Helper method
     */
    private static boolean high(int level) {
        return (level > MAX_LEVEL);
    }

    /**
     * toString()
     */
    public static String toString(int emotion) {

        switch (emotion) {
            case NEUTRAL:
                return "NEUTRAL";
            case EXCITED:
                return "EXCITED";
            case ELATED:
                return "ELATED";
            case HAPPY:
                return "HAPPY";
            case CONTENT:
                return "CONTENT";
            case TIRED:
                return "TIRED";
            case BORED:
                return "BORED";
            case SAD:
                return "SAD";
            case ANGRY:
                return "ANGRY";

            default:
                throw new IllegalArgumentException("Illegal emotion value: "
                        + emotion);
        }

    }

    public static int toStrategy(int activation, int pleasure) {
        if (neutral(activation) && neutral(pleasure)) {
            return Main.STRATEGY_n;
        } else if (high(activation) && neutral(pleasure)) {
            return Main.STRATEGY_ex;
        } else if (high(activation) && high(pleasure)) {
            return Main.STRATEGY_el;
        } else if (neutral(activation) && high(pleasure)) {
            return Main.STRATEGY_ha;
        } else if (low(activation) && high(pleasure)) {
            return Main.STRATEGY_con;
        } else if (neutral(activation) && low(pleasure)) {
            return Main.STRATEGY_ti;
        } else if (low(activation) && low(pleasure)) {
            return Main.STRATEGY_bo;
        } else if (low(activation) && neutral(pleasure)) {
            return Main.STRATEGY_sad;
        } else if (high(activation) && low(pleasure)) {
            return Main.STRATEGY_an;
        } else {
            throw new IllegalArgumentException("Parameter out of range: "
                    + activation + ", " + pleasure);

        }

    }

}
