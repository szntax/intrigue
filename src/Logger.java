import java.util.List;

/**
 * Class to log results and statistics.
 */
public class Logger {

    /**
     * Prints every output on console.
     */
    public static void log(String message) {
        // Comment next line to avoid any output.

        if (Main.argsLength != 2) {
            System.out.println(message);
        }
    }

    /**
     * Prints the current status information for all positions, that is the
     * status of the gameboard.
     */
    public static void printPositionStatistics() {

        Logger.log("\n*** Position Statistics ***");
        // Print all houses and their current experts
        for (Player p : GameEngine.getInstance().getAllPlayers()) {
            House curHouse = p.getHouse();
            for (Position curPosition : curHouse.getAllPositions()) {
                Logger.log(curPosition.debugInfo());
            }
        }
    }

    /**
     * Prints the current status information for all players.
     */
//    public static List<Object> printPlayerStatistics() {
//
//        List<Object> allCredits = new ArrayList<Object>();
//        Logger.log("\n*** Player Statistics ***");
//        // Print all Players and their Balance
//        for (Player p : GameEngine.getInstance().getAllPlayers()) {
//
//            Logger.log(p.getName() + ": " + p.getBalance() + " credits (Type:"
//                    + p.getClass().getName() + ")");
//
//        }
//
//        return allCredits;
//    }

    /**
     * Prints the current status information for all experts.
     */
    public static void printExpertStatistics() {
        Logger.log("\n*** Experts ***");
        List<Player> allPlayers = GameEngine.getInstance().getAllPlayers();

        for (Player ap : allPlayers) {
            for (Expert curExpert : ap.getAllExperts()) {

                Logger.log(curExpert + " status: "
                        + curExpert.statusToString((curExpert.getStatus())));

            }

        }

    }

    /**
     * Prints the current status information for all players' emotions.
     */
    public static void printEmotionStatistics() {
        Logger.log("\n*** Emotions ***");
        List<Player> allPlayers = GameEngine.getInstance().getAllPlayers();

        for (Player ap : allPlayers) {

            if (ap.isEmotional()) {
                EmotionalPlayer eplayer = (EmotionalPlayer) ap;

                Logger.log(eplayer.getName() + "s Activation value: "
                        + eplayer.getActivation());
                Logger.log(eplayer.getName() + "s Pleasure value: "
                        + eplayer.getPleasure());

                Logger.log(eplayer.getName()
                        + " is "
                        + Emotion.toString(Emotion.select(eplayer
                        .getActivation(), eplayer.getPleasure()))
                        + "\n");

            }

        }

    }

    /**
     * Prints the balances of the player.
     */
    public static void printBalances(Player p) {

        Logger.log(p.getName() + ": " + p.getBalance() + " credits");

    }

    /**
     * Prints the balances of all players.
     */
    public static void printBalanceStatistics() {
        Logger.log("\n*** Credits ***");
        List<Player> allPlayers = GameEngine.getInstance().getAllPlayers();

        for (Player curPlayer : allPlayers) {
            printBalances(curPlayer);
        }
    }
}
