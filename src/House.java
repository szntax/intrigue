import java.util.ArrayList;
import java.util.List;

/**
 * The House of the Player.
 */
public class House {

    public static final int ZERO = 0;

    /**
     * The owner of the House.
     */
    private Player owner;

    /**
     * List of all Positions in this House.
     */
    private List<Position> allPositions;

    /**
     * Constructor to initialize House of Player
     */
    public House(Player player) {
        owner = player;
        allPositions = new ArrayList<Position>();
        allPositions.add(new OneThousandPosition(this));
        allPositions.add(new ThreeThousandPosition(this));
        allPositions.add(new SixThousandPosition(this));
        allPositions.add(new TenThousandPosition(this));
    }

    /**
     * The Owner of this House.
     */
    public Player getOwner() {

        return this.owner;
    }

    /**
     * Gets all Positions of this house
     */
    public List<Position> getAllPositions() {
        return this.allPositions;
    }

    /**
     * Gets the amount of credits distributed amongst all the positions of the
     * house.
     */
    public int getSalariesAmount(Player player) {
        int amount = ZERO;
        for (Position position : this.allPositions) {
            amount += position.getSalary(player);

        }
        return amount;
    }

}
