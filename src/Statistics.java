import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Class to create and write csv Files.
 */
public class Statistics {

    /**
     * Constructor for Statistics class
     */
    private Statistics() {

    }

    /**
     * Generates the header for a credit csv File.
     */
    public static void writeCsvCreditHeader(List<Player> allPlayers,
                                            FileWriter fWriter) {

        try {
            for (Player curPlayer : allPlayers) {

                fWriter.write(curPlayer + ",");
            }
            fWriter.write("\n");
            fWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing credit.csv header. " + e);

        }
    }

    /**
     * Generates the header for emotions csv File.
     */
    public static void writeCsvEmotionHeader(List<Player> allPlayers,
                                             FileWriter fWriter) {

        try {
            for (Player curPlayer : allPlayers) {
                if (curPlayer.isEmotional()) {
                    fWriter.write(curPlayer + "(activation)," + curPlayer
                            + "(pleasure)" + ",");
                }

            }
            fWriter.write("\n");
            fWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing emotion.csv header. " + e);
        }
    }

    /**
     * Generates the header for expert csv File.
     */
    public static void writeCsvExpertsHeader(List<Player> allPlayers,
                                             FileWriter fWriter) {

        try {
            for (Player curPlayer : allPlayers) {

                fWriter.write(curPlayer + " (#placed)," + curPlayer
                        + " (#banned)" + ",");
            }
            fWriter.write("\n");
            fWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing expert.csv header. " + e);
        }

    }

    /**
     * Generates the header for strategy csv File.
     */
    public static void writeCsvStrategyHeader(List<Player> allPlayers,
                                              FileWriter fWriter) {

        try {
            for (Player curPlayer : allPlayers) {
                if (curPlayer.isEmotional()) {
                    fWriter.write(curPlayer + ",");
                }

            }
            fWriter.write("\n");
            fWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing strategy.csv header. "
                    + e);
        }
    }

    /**
     * Writes the balances of each player to file.
     */
    public static void writeCsvCredits(List<Player> allPlayers, Writer fWriter) {

        try {

            for (Player player : allPlayers) {

                fWriter.write(player.getBalance() + ",");

            }
            fWriter.write("\n");
            fWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException("Error while writing file " + fWriter
                    + "." + e);
        }

    }

    /**
     * Writes the activation and pleasure value of each player to file.
     */
    public static void writeCsvEmotions(List<Player> allPlayers,
                                        FileWriter fWriter) {

        try {

            for (Player curPlayer : allPlayers) {

                if (curPlayer.isEmotional()) {

                    EmotionalPlayer emoPlayer = (EmotionalPlayer) curPlayer;
                    fWriter.write(emoPlayer.getActivation() + ","
                            + emoPlayer.getPleasure() + ",");

                }

            }
            fWriter.write("\n");
            fWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException("Error while writing file " + fWriter
                    + "." + e);
        }

    }

    /**
     * Writes a file with the information how many experts a player banished and
     * how many he successfully placed.
     */
    public static void writeCsvExperts(List<Player> allPlayers,
                                       FileWriter fWriter) {

        try {
            for (Player curPlayer : allPlayers) {

                fWriter.write(curPlayer.getPlaced() + ","
                        + curPlayer.getBanished() + ",");

            }
            fWriter.write("\n");
            fWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing expert values." + e);
        }

    }

    /**
     * Writes a file with the information in which emotional state a player is
     * after the end of the game.
     */
    public static void writeCsvStrategy(List<Player> allPlayers,
                                        FileWriter fWriter) {

        try {

            for (Player curPlayer : allPlayers) {

                if (curPlayer.isEmotional()) {

                    EmotionalPlayer emoPlayer = (EmotionalPlayer) curPlayer;
                    fWriter.write(Emotion.toString(Emotion.select(emoPlayer
                            .getActivation(), emoPlayer.getPleasure()))
                            + ",");

                }

            }
            fWriter.write("\n");
            fWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException("Error while writing file " + fWriter
                    + "." + e);
        }

    }


}
