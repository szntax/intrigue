/**
 * Class of the occurring emotionally relevant Events.
 */
public class Incident {

    /**
     * Constant for event Expert accepted.
     */
    public static final int EXPERT_ACCEPTED = 1;

    /**
     * Constant for event Expert banished.
     */
    public static final int EXPERT_BANISHED = 2;

    /**
     * Constant for event Balance increased.
     */
    public static final int BALANCE_INCREASED = 3;

    /**
     * Constant for event Balance decreased.
     */
    public static final int BALANCE_DECREASED = 4;

    /**
     * The type of this incident.
     */
    private int type;

    /**
     * The causer of this incident.
     */
    private Object causer;

    /**
     * The intensity of this incident, ranges between 0 and 1.
     */
    private double intensity;

    /**
     * Creates an incident object with the given type, causer and intensity. The
     * given type must be one of the type constants of this class. The given
     * causer may be null, e.g. if the bank pays credits. The given intensity
     * may range from 0 to 1, the default value is 0.5.
     */
    public Incident(int type, Object causer, double intensity) {
        if (type < 1 || type > 4) {
            throw new IllegalArgumentException("Undefined type: " + type);
        }
        if (intensity < 0 || intensity > 1) {
            throw new IllegalArgumentException("Intensity out of range: "
                    + intensity);
        }
        this.type = type;
        this.causer = causer;
        this.intensity = intensity;
    }

    /**
     * Returns the type of this incident;
     */
    public int getType() {
        return this.type;
    }

    /**
     * Returns the causer of this incident.
     */
    public Object getCauser() {
        return this.causer;
    }

    /**
     * Returns the intensity of this incident.
     */
    public double getIntensity() {
        return this.intensity;
    }
}
