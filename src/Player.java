import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Abstract class for Players.
 */
public abstract class Player extends Bank implements CreditsOwner {

    /**
     * Name of Player One
     */
    public static final String ADAM = "Adam";

    /**
     * Name of Player Two
     */
    public static final String BETTY = "Betty";

    /**
     * Name of Player Three
     */
    public static final String CARRY = "Carry";

    /**
     * Name of Player Four
     */
    public static final String DYLAN = "Dylan";

    /**
     * Name of Player Five
     */
    public static final String EDDY = "Eddy";

    /**
     * Name of Player Six
     */
    //    public static final String FELICIA = "Felicia";

    /**
     * Initial Balance of each Player
     */
    public static final int INITIAL_BALANCE = 32000;
    public static final int MAGIC_SEVEN = 7;
    public static final int MAGIC_EIGHT = 8;
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 1;
    public static final int DEFAULT_PAYMENT = 15000;
    public static final int MAGIC_TWO = 2;

    /**
     * The experts owned by this player.
     */
    private LinkedList<Expert> allExperts;

    /**
     * Counter used to count the amount of experts this player banished
     */
    private int banished;

    /**
     * Credit store of this player
     */
    private CreditsStore credits;

    /**
     * House of Player
     */
    private House house;

    /**
     * Name of Player
     */
    private String name;

    /**
     * Counter used to count the amount of expert this player placed
     */
    private int placed;

    protected int Type;

    /**
     * Constructor to create AbstractPlayer
     */
    public Player(String name) {
        super();
        this.name = name;
        init();
    }

    /**
     * Decrements banished counter
     */
    public void decrementBanished() {
        this.banished--;
    }

    /**
     * Determines the appropriate bribe for a request on the given position.
     */
    public abstract int determineSendOutBribe(Position position);

    /**
     * Determines the bribe for a request on the given position randomly.
     */
    public int determineSendOutBribeRandomly(Position position) {
        // randomize bribe would be an enhancement
        return Utils.random(MAGIC_TWO * position.getValue());
    }

    /**
     * Determines an available expert for the given position. (not final for
     * HumanPlayer)
     */
    public final Expert determineSendOutExpert(Position position) {

        // choose one expert randomly
        int expertOffset = Utils.random(MAGIC_SEVEN);

        // loop over all experts, starting by the random one
        for (int expertIndex = expertOffset; expertIndex < expertOffset + MAGIC_EIGHT; expertIndex++) {

            Expert curExpert = this.allExperts.get(expertIndex % MAGIC_EIGHT);

            if (curExpert.getStatus() == Expert.AVAILABLE
                    && position.canPickUp(curExpert)) {
                return curExpert;
            }
        }

        throw new RuntimeException("No expert available for position: "
                + position);
    }

    /**
     * Determines the position to which an available expert can be send out.
     * NOTE: This method may only return a position an expert is available for
     * or null if there is no such position.
     */
    public abstract Position determineSendOutPosition();

    /**
     * Determines the position to which an available expert can be send out
     * randomly.
     */
    public Position determineSendOutPositionRandomly() {

        // choose one expert randomly
        int expertOffset = Utils.random(MAGIC_SEVEN);

        // loop over all experts, starting by the random one
        for (int expertIndex = expertOffset; expertIndex < expertOffset + MAGIC_EIGHT; expertIndex++) {

            Expert curExpert = this.allExperts.get(expertIndex % MAGIC_EIGHT);

            // check: expert is available
            if (curExpert.getStatus() == Expert.AVAILABLE) {

                // choose one player randomly
                List<Player> allPlayers = GameEngine.getInstance()
                        .getAllPlayers();
                int playerOffset = Utils.random(allPlayers.size() - 1);

                // loop over all players, starting by the random one
                for (int playerIndex = playerOffset; playerIndex < playerOffset
                        + allPlayers.size(); playerIndex++) {

                    Player curPlayer = allPlayers.get(playerIndex
                            % allPlayers.size());
                    if (curPlayer == this) {
                        continue;
                    }

                    // choose one position randomly
                    House chosenHouse = curPlayer.getHouse();
                    List<Position> allPositions = chosenHouse.getAllPositions();
                    int positionOffset = Utils.random(3);

                    // loop over all positions, starting by the random one
                    for (int positionIndex = positionOffset; positionIndex < positionOffset + 4; positionIndex++) {
                        Position curPosition = allPositions
                                .get(positionIndex % 4);

                        // check if the chosen position can pick up the expert
                        if (curPosition.canPickUp(curExpert)) {
                            return curPosition;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns a list of all experts
     */
    public final LinkedList<Expert> getAllExperts() {
        return this.allExperts;
    }

    /**
     * Returns the balance of this player.
     */
    public final int getBalance() {
        return this.credits.getBalance();
    }

    /**
     * Returns the value of this counter
     */
    public final int getBanished() {
        return this.banished;
    }

    /**
     * Returns the name of this players house.
     */
    public final House getHouse() {

        return this.house;
    }

    /**
     * Returns the name of current player
     */
    public final String getName() {

        return this.name;

    }

    /**
     * Returns the value of this counter
     */
    public final int getPlaced() {
        return this.placed;
    }

    /**
     * Increments placed counter
     */
    public void incrementPlaced() {
        this.placed++;
    }

    /**
     * Initializes Player
     */
    protected void init() {
        this.credits = new CreditsStore(this, INITIAL_BALANCE);
        this.allExperts = new LinkedList<Expert>();
        this.allExperts.add(new AnatomyExpert(this, AnatomyExpert.FIRST));
        this.allExperts.add(new AnatomyExpert(this, AnatomyExpert.SECOND));
        this.allExperts.add(new BiologyExpert(this, BiologyExpert.FIRST));
        this.allExperts.add(new BiologyExpert(this, BiologyExpert.SECOND));
        this.allExperts.add(new ChemistryExpert(this, ChemistryExpert.FIRST));
        this.allExperts.add(new ChemistryExpert(this, ChemistryExpert.SECOND));
        this.allExperts.add(new DentistryExpert(this, DentistryExpert.FIRST));
        this.allExperts.add(new DentistryExpert(this, DentistryExpert.SECOND));
        this.house = new House(this);
        this.placed = 0;
        this.banished = 0;
    }

    /**
     * Determines whether Player is emotional or not.
     */
    public abstract boolean isEmotional();

    /**
     * Method determines how Player is dealing with incoming requests.
     */
    public final void processRequests() {
        List<Position> positions = getHouse().getAllPositions();
        for (Position curPosition : positions) {

            Expert selectedExpert = solveConflict(curPosition);

            for (Request curRequest : curPosition.getRequests()) {

                curRequest.spendPayment(this, curRequest.getBribe());

                if (curRequest.getExpert() == selectedExpert) {
                    selectedExpert.place(curPosition);
                } else {
                    curRequest.getExpert().banish();
                }
            }
            curPosition.clearRequests();
        }

    }

    /**
     * Abstract method to indicate that an emotionally relevant event occurred.
     */
    public abstract void realize(Incident incident);

    /**
     * @see CreditsOwner
     */
    public final void receivePayment(CreditsOwner spender, int amount) {
        this.credits.receivePayment(spender, amount);
        double intensity = (double) amount / DEFAULT_PAYMENT;
        if (intensity < MIN_VALUE) {
            intensity = MIN_VALUE;
        }
        if (intensity > MAX_VALUE) {
            intensity = MAX_VALUE;
        }
        this.realize(new Incident(Incident.BALANCE_INCREASED, spender,
                intensity));
    }

    /**
     * Sends out two experts of this player.
     */
    public final void sendOutExperts() {
        for (int i = 0; i < 2; i++) {
            sendOutSingleExpert();
        }
    }

    /**
     * Sends out a single expert of this player.
     */
    public final void sendOutSingleExpert() {
        Position position = determineSendOutPosition();
        Logger.log("Determined position: " + position);
        // no possible position available
        if (position == null) {
            return;
        }
        Expert expert = determineSendOutExpert(position);
        Logger.log("Determined expert: " + expert);
        int bribe = determineSendOutBribe(position);
        Logger.log("Determined bribery: " + bribe);
        expert.sendOut(position, bribe);
        Logger.log("Requested Position " + position + " for " + expert
                + ", Bribery: " + bribe);

    }

    /**
     * decides what to do if a conflict occurs.
     */
    public abstract Expert solveConflict(Position curPosition);

    /**
     * decides randomly what to do if a "conflict" occurs.
     */
    public Expert solveConflictsRandomly(Position curPosition) {

        Expert selectedExpert = null;

        List<Request> curRequests = curPosition.getRequests();

        for (Request curRequest : curRequests) {

            Random r = new Random();
            int oneortwo = Math.abs(r.nextInt()) % 2;
            if ((oneortwo == 1)
                    || ((curRequests.size() - 1) == curRequests
                    .indexOf(curRequest) && curPosition.getExpert() == null)) {
                selectedExpert = curRequest.getExpert();
                break;
            }

        }
        return selectedExpert;

    }

    /**
     * @see CreditsOwner
     */
    public final void spendPayment(CreditsOwner receiver, int amount) {
        this.credits.spendPayment(receiver, amount);
        double intensity = (double) amount / DEFAULT_PAYMENT;
        if (intensity < MIN_VALUE) {
            intensity = MIN_VALUE;
        }
        if (intensity > MAX_VALUE) {
            intensity = MAX_VALUE;
        }
        this.realize(new Incident(Incident.BALANCE_DECREASED, receiver,
                intensity));
    }

    /**
     * toString
     */
    public final String toString() {
        return getName();
    }

}
