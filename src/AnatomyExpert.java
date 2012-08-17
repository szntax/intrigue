/**
 * Expert with profession Anatomy.
 */
public class AnatomyExpert extends Expert {

    public static final String ANATOMIST = "Anatomist";

    /**
     * Constructor for Expert.
     */
    public AnatomyExpert(Player owner, String rank) {
        super(owner, rank);
    }

    /**
     * Returns the profession of this Expert.
     */
    public String getProfession() {

        return ANATOMIST;
    }

}
