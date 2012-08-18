import java.util.ArrayList;
import java.util.List;

/**
 * Class of Properties.
 */
public class Properties {

    /**
     * Emotion Reset Info
     */
    public static boolean EMOTION_RESET = false;

    /**
     * Path where csv Files will be saved.
     */
    public static String FILE_PATH = "c:/";


    /**
     * Calibration Value
     */
    public static final double ACTIVATION_TRIM = -0.42;

    /**
     * Calibration Value
     */
    public static final double PLEASURE_TRIM = -0.44;

    /**
     * Calibration Value
     */
    public static final int ACTIVATION_SENSIVITY = 30;

    /**
     * Calibration Value
     */
    public static final int PLEASURE_SENSITIVITY = 24;

    /**
     * Calibration Value
     */
    public static final int POSITIVE_CREDIT_TRIM = 10;

    /**
     * Calibration Value
     */
    public static final int NEGATIVE_CREDIT_TRIM = 10;

    /**
     * Adds Player to List.
     */
    public static void addPlayers(List<Player> allPlayers) {

        allPlayers.add(new RandomPlayer(Player.ADAM));
        allPlayers.add(new RandomPlayer(Player.BETTY));
        allPlayers.add(new EmotionalPlayer(Player.CARRY));
        allPlayers.add(new RandomPlayer(Player.DYLAN));
        // allPlayers.add(new RandomPlayer(Player.EDDY));
        // allPlayers.add(new RandomPlayer(Player.FELICIA));

    }

    /**
     * Returns a List of Filenames.
     */
    public static List<String> getFileNames() {

        List<String> allFileNames = new ArrayList<String>();

        String logFile1;
        String logFile2;
        String logFile3;
        String logFile4;

        logFile1 = FILE_PATH + "/c-" + getString();

        logFile2 = FILE_PATH + "/e-" + getString();

        logFile3 = FILE_PATH + "/x-" + getString();

        logFile4 = FILE_PATH + "/s-" + getString();

        allFileNames.add(logFile1);
        allFileNames.add(logFile2);
        allFileNames.add(logFile3);
        allFileNames.add(logFile4);

        return allFileNames;
    }

    /**
     * Returns the filename String
     */
    private static String getString() {
        return GameEngine.getInstance().getAllPlayers().get(0).Type + "-"
                + GameEngine.getInstance().getAllPlayers().get(1).Type + "-"
                + GameEngine.getInstance().getAllPlayers().get(2).Type + "-"
                + GameEngine.getInstance().getAllPlayers().get(3).Type + "-"
                + resetOrNoReset() + ".csv";
    }

    /**
     * States if reset is enabled.
     */
    private static String resetOrNoReset() {
        if (EMOTION_RESET) {
            return "r";
        } else {
            return "nr";
        }
    }
}
