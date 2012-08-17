/**
 * Request for Position.
 */
public class Request implements CreditsOwner {

    /**
     * The position of this request.
     */
    private Position position;

    /**
     * The expert for this request.
     */
    private Expert expert;

    /**
     * The bribe for this request.
     */
    private CreditsStore credits;

    /**
     * Constructor, also collects the bribe from the owner of the expert.
     */
    public Request(Position position, Expert expert, int bribe) {
        this.position = position;
        this.expert = expert;
        expert.setRequest(this);
        this.credits = new CreditsStore(expert.getPlayer(), 0);
        expert.getPlayer().spendPayment(this, bribe);
    }

    /**
     * Returns the expert of this request.
     */
    public Expert getExpert() {
        return this.expert;
    }

    /**
     * Returns the bribe amount of this request.
     */
    public int getBribe() {
        return this.credits.getBalance();
    }

    /**
     * @see CreditsOwner
     */
    public void receivePayment(CreditsOwner spender, int amount) {
        this.credits.receivePayment(spender, amount);
    }

    /**
     * @see CreditsOwner
     */
    public void spendPayment(CreditsOwner receiver, int amount) {
        this.credits.spendPayment(receiver, amount);
    }

    public String toString() {

        return "Request: " + this.expert + " on " + this.position;
    }

}
