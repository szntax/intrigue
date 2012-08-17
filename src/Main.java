import java.util.ArrayList;
import java.util.List;

/**
 * Main Class.
 */
public class Main {
    public static int argsLength = 0;
    public static final int STRATEGY_r = 0;
    public static final int STRATEGY_e = 1;
    public static final int STRATEGY_n = 2;
    public static final int STRATEGY_el = 3;
    public static final int STRATEGY_ex = 4;
    public static final int STRATEGY_ha = 5;
    public static final int STRATEGY_con = 6;
    public static final int STRATEGY_ti = 7;
    public static final int STRATEGY_bo = 8;
    public static final int STRATEGY_sad = 9;
    public static final int STRATEGY_an = 10;
    public static final int STRATEGY_h = 11;
    static List<Integer> allPlayerTypes;

    /**
     * Main method
     */

    public static void main(String[] args) {

        GameEngine engine = GameEngine.getInstance();

        if (args.length == 1) {
            argsLength = 1;
            int rounds = Integer.parseInt(args[0]);
            engine.startSimulation(rounds);
        } else if (args.length == 2 && args[0].intern().equals("all")) {
            argsLength = 2;
            Properties.EMOTION_RESET = getResetInfo(args[1].intern());
            engine.startSimulation();
        } else if (args.length == 5) {
            argsLength = 5;
            allPlayerTypes = new ArrayList<Integer>();
            int player1 = convertToType(args[0].intern());
            int player2 = convertToType(args[1].intern());
            int player3 = convertToType(args[2].intern());
            int player4 = convertToType(args[3].intern());
            Properties.EMOTION_RESET = getResetInfo(args[4].intern());
            allPlayerTypes.add(player1);
            allPlayerTypes.add(player2);
            allPlayerTypes.add(player3);
            allPlayerTypes.add(player4);
            engine.startSimulation(true);
        } else if (args.length == 6) {
            argsLength = 6;
            allPlayerTypes = new ArrayList<Integer>();
            int player1 = convertToType(args[0].intern());
            int player2 = convertToType(args[1].intern());
            int player3 = convertToType(args[2].intern());
            int player4 = convertToType(args[3].intern());
            Properties.EMOTION_RESET = getResetInfo(args[4].intern());
            Properties.FILE_PATH_WINDOWS = args[5].intern();
            allPlayerTypes.add(player1);
            allPlayerTypes.add(player2);
            allPlayerTypes.add(player3);
            allPlayerTypes.add(player4);
            engine.startSimulation(true);
        } else {
            throw new RuntimeException(
                    "Invalid amount of parameters, number must be 0, 1 or 5.");
        }
    }

    /**
     * Returns if reset is enabled.
     */
    private static boolean getResetInfo(String resetInfo) {

        if (resetInfo.equals("yes") || resetInfo.equals("y") || resetInfo.equals("r")
                || resetInfo.equals("reset") || resetInfo.equals("res")) {
            return true;
        } else if (resetInfo.equals("no") || resetInfo.equals("n") || resetInfo.equals("nr")
                || resetInfo.equals("noreset") || resetInfo.equals("nores")) {
            return false;
        }
        return false;

    }

    /**
     * Converts Strategy String to STRATEGY constant.
     */
    private static int convertToType(String player) {

        if (player.equals("r") || player.equals("random") || player.equals("0")) {
            return Main.STRATEGY_r;
        } else if (player.equals("e") || player.equals("emotional") || player.equals("1")) {
            return Main.STRATEGY_e;
        } else if (player.equals("n") || player.equals("neutral") || player.equals("2")) {
            return Main.STRATEGY_n;
        } else if (player.equals("el") || player.equals("elated") || player.equals("3")) {
            return Main.STRATEGY_el;
        } else if (player.equals("ex") || player.equals("excited") || player.equals("4")) {
            return Main.STRATEGY_ex;
        } else if (player.equals("ha") || player.equals("happy") || player.equals("5")) {
            return Main.STRATEGY_ha;
        } else if (player.equals("con") || player.equals("content") || player.equals("6")) {
            return Main.STRATEGY_con;
        } else if (player.equals("ti") || player.equals("tired") || player.equals("7")) {
            return Main.STRATEGY_ti;
        } else if (player.equals("bo") || player.equals("bored") || player.equals("8")) {
            return Main.STRATEGY_bo;
        } else if (player.equals("sad") || player.equals("9")) {
            return Main.STRATEGY_sad;
        } else if (player.equals("an") || player.equals("angry") || player.equals("10")) {
            return Main.STRATEGY_an;
        } else if (player.equals("h") || player.equals("human") || player.equals("11")) {
            return Main.STRATEGY_h;
        } else {
            throw new IllegalArgumentException(
                    "Wrong player types. Allowed players: r e ex n el ha con ti bo sad an ");

        }
    }
}
