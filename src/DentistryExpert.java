/**
 * Expert with profession Dentistry
 */
public class DentistryExpert extends Expert {

    public static final String DENTIST = "Dentist";

    /**
     * Constructor for Expert.
     */
    public DentistryExpert(Player owner, String rank) {
        super(owner, rank);
    }

    /**
     * returns the profession of this Expert.
     */
    public String getProfession() {

        return DENTIST;
    }

}
