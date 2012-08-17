import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for Positions.
 */
public abstract class Position {

    /**
     * House of this position.
     */
    private House house;

    /**
     * Expert on this position.
     */
    private Expert expert;

    /**
     * List of all requests for this position.
     */
    private List<Request> allRequests;

    /**
     * Constructor for this Position.
     */
    public Position(House house) {
        this.house = house;
        this.expert = null;
        this.allRequests = new ArrayList<Request>();
    }

    /**
     * Method to add a request to allRequest List including bribe.
     */
    public void addRequest(Expert expert, int bribe) {
        if (!canPickUp(expert)) {
            throw new IllegalArgumentException("Illegal request : " + expert);
        }
        this.allRequests.add(new Request(this, expert, bribe));
    }

    /**
     * Returns a list of all requests.
     */
    public List<Request> getRequests() {
        return this.allRequests;
    }

    /**
     * Abstract method returns the value of the Position.
     */
    public abstract int getValue();

    /**
     * Returns the house if current Player.
     */
    public House getHouse() {
        return this.house;
    }

    /**
     * Returns this Expert.
     */
    public Expert getExpert() {
        return this.expert;
    }

    /**
     * Returns this Player.
     */
    public Player getPlayer() {

        return getHouse().getOwner();

    }

    /**
     * Returns the salary for the given player. If there is an expert of the
     * given player on this position, the value of this position is returned.
     * Otherwise 0 is returned. This is the case when this position is empty or
     * if it's occupied by an expert of another player than the given one.
     */
    public int getSalary(Player p) {
        int amount = 0;
        Expert expert = this.getExpert();
        if (expert != null && p == expert.getPlayer()) {
            Logger.log(expert.toString() + " gets cash " + getValue());
            amount += getValue();
        }
        return amount;
    }

    /**
     * Places the given expert on the selected position, bans the old expert if
     * it's another expert than the given one. Does nothing if the given expert
     * is already placed on this position.
     */
    public void setExpert(Expert expert) {
        if (expert == null) {
            throw new NullPointerException();
        } else {
            if (this.expert != expert) {
                if (this.expert != null) {
                    this.expert.banish();
                }
                this.expert = expert;
                expert.place(this);
            }
        }
    }

    /**
     * Removes the current expert from this position and banishes him. Does
     * nothing if there is no expert.
     */
    public void removeExpert() {
        if (this.expert != null) {
            Expert tmpExpert = this.expert;
            this.expert = null;
            tmpExpert.banish();

        }
    }

    /**
     * Returns a string containing the name of the player that owns this
     * position and the value of this position.
     */
    public String toString() {
        return (this.getHouse().getOwner().getName() + " " + this.getValue());
    }

    /**
     * Returns debug information as string.
     */
    public String debugInfo() {
        String result = "--Position info--\n" + "player: " + getPlayer() + "\n"
                + "value: " + getValue() + "\n" + "current expert: "
                + getExpert() + "\n" + "pending requests:\n";
        for (Request curRequest : getRequests()) {
            result += curRequest + "\n";
        }
        return result;
    }

    /**
     * Clears all requests on this position, should be called once after player
     * has processed all requests.
     */
    public void clearRequests() {
        this.allRequests.clear();
    }

    /**
     * Returns the class of the expert that is placed on this position, or the
     * class of the experts that are in a request for this position, or null if
     * the position is empty and there are no requests.
     */
    @SuppressWarnings("unchecked")
    public Class getExpertType() {
        if (this.expert != null) {
            return this.expert.getClass();
        } else if (!this.allRequests.isEmpty()) {
            return this.allRequests.get(0).getExpert().getClass();
        } else {
            return null;
        }
    }

    /**
     * Returns true if this position can pick up the given expert. This is the
     * case if the given expert is does not belong to the owner of this position
     * and if there is no expert of the same player and type on this position or
     * its requests. * If this position is empty and has no requests, the method
     * takes all other positions inside the same house into account, including
     * their requests.
     */
    public boolean canPickUp(Expert expert) {

        if (expert.getPlayer() == this.getPlayer()) {
            return false;
        }
        if (getExpertType() != null) {
            return getExpertType() == expert.getClass();
        }

        House house = getPlayer().getHouse();

        for (Position curPosition : house.getAllPositions()) {
            if (curPosition != this
                    && curPosition.getExpertType() == expert.getClass()) {
                return false;
            }
        }
        return true;
    }

}
