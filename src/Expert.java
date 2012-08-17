/**
 * Abstract class for Experts.
 */
public abstract class Expert {

    /**
     * Constant for expert status available.
     */
    public static final int AVAILABLE = 0;

    /**
     * Constant for expert status banished.
     */
    public static final int BANISHED = -1;

    /**
     * Constant for first expert of a profession.
     */
    public static final String FIRST = "first";

    /**
     * Constant for expert status pending.
     */
    public static final int PENDING = 1;

    /**
     * Constant for expert status placed.
     */
    public static final int PLACED = 2;

    /**
     * Constant for second expert of a profession.
     */
    public static final String SECOND = "second";

    /**
     * Division factor.
     */
    public static final double MAGIC_NUMBER_REALIZE = 0.5;

    /**
     * The name of the owner of this expert.
     */
    private Player owner;

    /**
     * The position that this expert has been placed to.
     */
    private Position position;

    /**
     * The rank of the expert
     */
    private String rank;

    /**
     * The status of an expert.
     */
    private int status;

    /**
     * Constructor to initialize experts.
     */
    public Expert(Player owner, String rank) {
        this.owner = owner;
        this.status = 0;
        this.rank = rank;
        this.position = null;
    }

    /**
     * Banishes this expert. The internal position variable is set to null and
     * the status is set to BANISHED.
     */
    public void banish() {
        Player causer;
        if (this.position != null) {
            causer = this.position.getPlayer();
            Logger.log(this + " gets banished from position " + this.position
                    + " by " + causer);

            Position tmpPosition = this.position;
            this.position = null;
            if (tmpPosition.getExpert() == this) {
                tmpPosition.removeExpert();
            }
            this.owner.realize(new Incident(Incident.EXPERT_BANISHED, causer,
                    MAGIC_NUMBER_REALIZE));

            this.owner.decrementBanished();
        }
        this.status = BANISHED;
        setRequest(null);
    }

    /**
     * The Name of the owners Expert.
     */
    public Player getPlayer() {
        return this.owner;
    }

    /**
     * The profession of the Expert.
     */
    public abstract String getProfession();

    /**
     * Returns the current status of this expert, see constants AVAILABLE,
     * PENDING, PLACED and BANISHED.
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Places this expert on the given position.
     */
    public void place(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("No position given.");
        }
        if (this.position != position) {
            Logger.log(this + " gets placed on posititon " + position);
            this.position = position;
            position.setExpert(this);
            this.status = PLACED;
            this.owner.realize(new Incident(Incident.EXPERT_ACCEPTED, position
                    .getPlayer(), 0.5));
            this.owner.incrementPlaced();
            setRequest(null);
        }
    }

    /**
     * Adds a request for this expert to the given position with the given
     * bribe.
     */
    public void sendOut(Position position, int bribe) {
        position.addRequest(this, bribe);
        this.status = PENDING;
    }

    /**
     * Sets the request that contains this expert.
     */
    public void setRequest(Request request) {
        if (request != null && this.status == AVAILABLE) {
        }
        if (request == null && this.status == PENDING) {
        }
    }

    /**
     * Translates status to String.
     */
    public String statusToString(int status) {
        switch (status) {
            case BANISHED:
                return "banished";
            case AVAILABLE:
                return "available";
            case PENDING:
                return "pending";
            case PLACED:
                return "placed";

        }
        return null;

    }

    /**
     * Expert String.
     */
    public String toString() {
        return ((this.getPlayer() + "s " + this.rank + " " + this
                .getProfession()));
    }

}
