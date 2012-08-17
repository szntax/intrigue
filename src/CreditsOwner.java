/**
 * A person or organization that owns credits and is therefore able to spend and
 * receive payments.
 */
public interface CreditsOwner {

    /**
     * Spends a payment - this method should always call method receivePayment()
     * of the player that receives the payment.
     */
    public void spendPayment(CreditsOwner receiver, int amount);

    /**
     * Receives a payment - this method should only be called from within method
     * spendPayment() by the credit owner that spends the payment.
     */
    public void receivePayment(CreditsOwner spender, int amount);

}
