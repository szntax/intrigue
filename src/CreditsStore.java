/**
 * A store for credits.
 */
public class CreditsStore {

    /**
     * The balance of this credits store.
     */
    private int balance;

    /**
     * The owner of this credits store.
     */
    private CreditsOwner owner;

    /**
     * Creates a credits store for the given owner with the given starting
     * balance.
     */
    public CreditsStore(Bank owner, int startingBalance) {
        this.owner = owner;
        this.balance = startingBalance;
    }

    /**
     * Spends a payment - this method should always call method receivePayment()
     * of the player that receives the payment.
     */
    public void spendPayment(CreditsOwner receiver, int amount) {
        if (receiver == null) {
            throw new IllegalArgumentException("Illegal receiver: " + receiver);
        }
        this.balance -= amount;
        receiver.receivePayment(this.owner, amount);
    }

    /**
     * Receives a payment - this method should only be called from within method
     * spendPayment() by the credit owner that spends the payment.
     */
    public void receivePayment(CreditsOwner spender, int amount) {
        if (spender == null) {
            throw new IllegalArgumentException("Illegal spender: " + spender);
        }
        this.balance += amount;
    }

    /**
     * Returns the balance of this store.
     */
    public int getBalance() {
        return this.balance;
    }

}
