/** Expert with profession Chemistry. */
public class ChemistryExpert extends Expert {

    public static final String CHEMIST = "Chemist";

    /** Constructor for Expert. */
	public ChemistryExpert(Player owner, String rank) {
		super(owner, rank);
	}

	/** Returns the profession of this Expert. */
	public String getProfession() {
		return CHEMIST;
	}

}
