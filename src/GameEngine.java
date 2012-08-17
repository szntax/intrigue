import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Initializes and performs the game.
 */
public class GameEngine {

    public static final int THOUSAND = 1000;
    /**
     * List of all players in the game.
     */
    private List<Player> allPlayers;

    /**
     * The singleton instance of this class.
     */
    private static GameEngine singleton;

    /**
     * Returns a singleton instance of this class.
     */
    public static GameEngine getInstance() {

        if (singleton == null) {
            singleton = new GameEngine();

        }
        return singleton;

    }

    /**
     * Default constructor.
     */
    private GameEngine() {

        if (Main.argsLength == 1) {
            this.allPlayers = new ArrayList<Player>();
            Properties.addPlayers(this.allPlayers);
        }

    }

    /**
     * adds a Player to the GameEngine according to his type.
     */
    private void addPlayerByType(int playerType, int number) {
        switch (playerType) {
            case Main.STRATEGY_r:
                allPlayers.add(new RandomPlayer(this.NameByNumber(number)));
                break;
            case Main.STRATEGY_e:
                allPlayers.add(new EmotionalPlayer(this.NameByNumber(number)));
                break;
            case Main.STRATEGY_n:
                allPlayers
                        .add(new EmotionalPlayer(this.NameByNumber(number), 0, 0));
                break;
            case Main.STRATEGY_el:
                allPlayers.add(new EmotionalPlayer(this.NameByNumber(number), 60,
                        60));
                break;
            case Main.STRATEGY_ex:
                allPlayers
                        .add(new EmotionalPlayer(this.NameByNumber(number), 60, 0));
                break;
            case Main.STRATEGY_ha:
                allPlayers
                        .add(new EmotionalPlayer(this.NameByNumber(number), 0, 60));
                break;
            case Main.STRATEGY_con:
                allPlayers.add(new EmotionalPlayer(this.NameByNumber(number), -60,
                        60));
                break;
            case Main.STRATEGY_ti:
                allPlayers.add(new EmotionalPlayer(this.NameByNumber(number), 0,
                        -60));
                break;
            case Main.STRATEGY_bo:
                allPlayers.add(new EmotionalPlayer(this.NameByNumber(number), -60,
                        -60));
                break;
            case Main.STRATEGY_sad:
                allPlayers.add(new EmotionalPlayer(this.NameByNumber(number), -60,
                        0));
                break;
            case Main.STRATEGY_an:
                allPlayers.add(new EmotionalPlayer(this.NameByNumber(number), 60,
                        -60));
                break;
            case Main.STRATEGY_h:
                allPlayers.add(new HumanPlayer(this.NameByNumber(number)));
                break;
            default:
                throw new RuntimeException("Wrong Player type specified.");
        }

    }

    /**
     * returns a list of all Players.
     */
    public List<Player> getAllPlayers() {

        return this.allPlayers;

    }

    /**
     * returns a list of all positions
     */
    public List<Position> getAllPositions() {
        List<Position> allPositions = new ArrayList<Position>();
        for (Player player : getInstance().allPlayers) {
            House curHouse = player.getHouse();
            for (Position position : curHouse.getAllPositions()) {
                allPositions.add(position);
            }
        }

        return allPositions;
    }

    /* returns the name of Name of a player by given player number. */
    public String NameByNumber(int playerNumber) {
        switch (playerNumber) {
            case 1:
                return Player.ADAM;
            case 2:
                return Player.BETTY;
            case 3:
                return Player.CARRY;
            case 4:
                return Player.DYLAN;
            case 5:
                return Player.EDDY;
            case 6:
                return Player.DYLAN;

            default:
                throw new IllegalArgumentException("Illegal player value: "
                        + playerNumber);
        }
    }

    /**
     * Starts a full game.
     */
    public void playGame(List<FileWriter> allFileWriters) {

        // start game
        for (Player player : this.allPlayers) {
            player.init();
        }

        // start rounds
        for (int j = 1; j <= 6; j++) {
            playRound(j);
        }

        Logger.printBalanceStatistics();
        Logger.printExpertStatistics();
        Logger.printPositionStatistics();
        Logger.printEmotionStatistics();

        Statistics.writeCsvCredits(this.allPlayers, allFileWriters.get(0));
        Statistics.writeCsvEmotions(this.allPlayers, allFileWriters.get(1));
        Statistics.writeCsvExperts(this.allPlayers, allFileWriters.get(2));
        Statistics.writeCsvStrategy(this.allPlayers, allFileWriters.get(3));

        Logger.log("\n*** End ***");

    }

    /**
     * Play a regular round
     */
    private void playRound(int round) {
        Logger.log("\nPlaying round " + round + " ...");
        Bank b = Bank.getInstance();
        // play every players turn
        for (Player p : allPlayers) {
            Logger.log("Playing " + p.getName() + "s turn ...");

            Logger.printBalances(p);

            Logger.log("Action 1: " + p.getName()
                    + " is fetching cash from bank ...");
            b.paySalaries(p);

            Logger.printBalances(p);

            Logger.log("Action 2: " + p.getName()
                    + " is processing request queue ...");
            p.processRequests();

            Logger.printBalances(p);

            Logger.log("Action 3: " + p.getName()
                    + " is sending out experts ...");
            p.sendOutExperts();

            Logger.printBalances(p);

        }

    }

    /* Starts a complete simulation run. */
    public void startSimulation() {

        for (int player1 = 10; player1 <= 10; player1++) {
            for (int player2 = 0; player2 <= 10; player2++) {
                for (int player3 = 0; player3 <= 10; player3++) {
                    for (int player4 = 0; player4 <= 10; player4++) {
                        this.allPlayers = new ArrayList<Player>();
                        addPlayerByType(player1, 1);
                        addPlayerByType(player2, 2);
                        addPlayerByType(player3, 3);
                        addPlayerByType(player4, 4);
                        startSimulation(1000);
                    }
                }
            }
        }
    }

    /**
     * Start a number of games.
     */
    public void startSimulation(int games) {
        Date d = new Date();

        /** Play a specified number of rounds, default value = 1 */
        int i = 1;

        List<FileWriter> allFileWriters = new LinkedList<FileWriter>();
        try {

            List<String> fileNames = Properties.getFileNames();

            FileWriter fWriter1 = new FileWriter(fileNames.get(0));
            FileWriter fWriter2 = new FileWriter(fileNames.get(1));
            FileWriter fWriter3 = new FileWriter(fileNames.get(2));
            FileWriter fWriter4 = new FileWriter(fileNames.get(3));

            Statistics.writeCsvCreditHeader(this.allPlayers, fWriter1);
            Statistics.writeCsvEmotionHeader(this.allPlayers, fWriter2);
            Statistics.writeCsvExpertsHeader(this.allPlayers, fWriter3);
            Statistics.writeCsvStrategyHeader(this.allPlayers, fWriter4);

            allFileWriters.add(fWriter1);
            allFileWriters.add(fWriter2);
            allFileWriters.add(fWriter3);
            allFileWriters.add(fWriter4);

            while (i <= games) {
                Logger.log("\nPlaying game number " + i + " ...");
                playGame(allFileWriters);
                Logger.log("\nGame " + i + " Over.");
                Date dn = new Date();
                Logger.log("Milliseconds: " + (dn.getTime() - d.getTime()));
                i++;
            }

        } catch (IOException e) {
            throw new RuntimeException("Error creating csv files.", e);
        }

    }


    /* Starts a game with a certain number of rounds. */
    public void startSimulation(boolean placeHolder) {
        if (placeHolder) {
            this.allPlayers = new ArrayList<Player>();
            int number = 0;
            for (int playerType : Main.allPlayerTypes) {
                number++;
                addPlayerByType(playerType, number);

            }

            startSimulation(THOUSAND);
        }

    }

}
