import java.util.List;

/** Manages the balances of Players. */
public class Bank implements CreditsOwner {

	/** The singleton instance of this class. */
	private static Bank singleton;
    public static final String BANK = "BANK";
    public static final int MAGIC_THOUSAND = 1000;
    public static final int ZERO = 0;

    /** The credits store of this bank. */
	private CreditsStore credits = new CreditsStore(this, MAGIC_THOUSAND * MAGIC_THOUSAND);

	/** Constructor for the bank. */
    protected Bank() {
    }

	/** Returns a singleton instance of this class. */
	public static Bank getInstance() {
		if (singleton == null) {
			singleton = new Bank();
		}
		return singleton;
	}

	/** Pay player for all Experts placed in opponent houses. */
	public void paySalaries(Player receiver) {

		List<Player> players = GameEngine.getInstance().getAllPlayers();
		int amount;
		for (Player curPlayer : players) {
			if (curPlayer == receiver) {
				continue;
			}
			House curHouse = curPlayer.getHouse();
			amount = curHouse.getSalariesAmount(receiver);
			if (amount != ZERO) {
				this.credits.spendPayment(receiver, amount);
			}
		}

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

	/** Returns name of the bank. */
	public String toString() {
		return BANK;
	}

}
