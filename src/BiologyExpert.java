/** Expert with profession Biology. */
public class BiologyExpert extends Expert {

    public static final String BIOLOGIST = "Biologist";

    /** Constructor for Expert. */
	public BiologyExpert(Player owner, String rank) {
		super(owner, rank);
	}

	/** Returns the profession of this Expert. */
	public String getProfession() {
		return BIOLOGIST;
	}

}
