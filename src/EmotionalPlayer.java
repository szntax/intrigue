import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class of an Emotional Player. EmotionalPlayer determines most of its actions
 * according to the actual strategy. If no strategy is available, Emotional
 * Player acts like RandomPlayer.
 */
public class EmotionalPlayer extends Player {

	/** Represents the level of emotional activation or deactivation. */
	private int activation;

	/** Value to trigger the factor of activation reactions. */
	private double activationSensitivity;

	/** Trims the activation influences, default value is 0. */
	private double activationTrim = Properties.ACTIVATION_TRIM;

	/** Trims the amount of bribe, default is 0 */
	private int negativeCreditTrim = Properties.NEGATIVE_CREDIT_TRIM;

	/** Emotional value triggering the emotional axis of pleasure and unpleasure. */
	private int pleasure;

	/** Value to trigger the factor of pleasure reactions. */
	private double pleasureSensitivity;

	/** Trims the pleasure influences, default value is 0. */
	private double pleasureTrim = Properties.PLEASURE_TRIM;

	/** Trims the amount of bribe, default is 0 */
	private int positiveCreditTrim = Properties.POSITIVE_CREDIT_TRIM;

	/** Decides whether EmotionalPlayer has dynamic Emotions. */
	private boolean staticEmotion;

	/**
	 * Creates a new emotional Player.
	 */
	public EmotionalPlayer(String name) {
		super(name);
		this.activationSensitivity = Properties.ACTIVATION_SENSIVITY;
		this.pleasureSensitivity = Properties.PLEASURE_SENSITIVITY;
		staticEmotion = false;
		Type = Main.STRATEGY_e;
	}

	/**
	 * Constructor to create parametrized Player.
	 */
	public EmotionalPlayer(String name, int initialActivation,
			int initialPleasure) {
		super(name);
		this.activation = initialActivation;
		this.pleasure = initialPleasure;
		staticEmotion = true;
		Type = Emotion.toStrategy(this.activation, this.pleasure);
	}

	/**
	 * Returns a ranking value between 1 and 10 regarding indicating the
	 * conflict goodness of chosen position.
	 */
	public int determineConflictRanking(Position position, int strategy) {
		switch (strategy) {
		case Strategy.INVOKE_CONFLICT: {

			if (position.getExpert() == null) {
				return 1;
			} else if (position.getRequests().size() > 0
					&& position.getExpert() == null) {
				return 4;
			} else if (position.getExpert() != null
					&& position.getRequests().size() == 0) {
				return 7;
			} else if (position.getExpert() != null
					&& position.getRequests().size() > 0) {
				return 10;
			}

		}
		case Strategy.AVOID_CONFLICT: {

			if (position.getExpert() == null) {
				return 10;
			} else if (position.getRequests().size() > 0
					&& position.getExpert() == null) {
				return 7;
			} else if (position.getExpert() != null
					&& position.getRequests().size() == 0) {
				return 4;
			} else if (position.getExpert() != null
					&& position.getRequests().size() > 0) {
				return 1;
			}

		}
		case Strategy.RANDOMLY: {
			return Utils.random(9) + 1;

		}
		default:
			throw new IllegalArgumentException("Invalid strategy: " + strategy);

		}

	}

	@Override
	public int determineSendOutBribe(Position position) {

		int bribe;
		int value = position.getValue();
		int emotion = Emotion.select(activation, pleasure);

		if (emotion == Emotion.EXCITED || emotion == Emotion.ELATED
				|| emotion == Emotion.HAPPY) {

			bribe = value + (Utils.random(value * positiveCreditTrim));

		}

		else if (emotion == Emotion.CONTENT || emotion == Emotion.SAD
				|| emotion == Emotion.ANGRY) {

			bribe = value - (Utils.random(value * negativeCreditTrim));

		} else if (emotion == Emotion.NEUTRAL || emotion == Emotion.TIRED
				|| emotion == Emotion.BORED) {

			bribe = value;

		}

		else {
			throw new RuntimeException("No legal strategy available!");
		}

		if (bribe <= 0) {
			bribe = 1;
		}

		return bribe;

	}

	/**
	 * Sends out a single expert of this player.
	 */

	public Position determineSendOutPosition() {

		int emotion = Emotion.select(activation, pleasure);

		switch (emotion) {

		case Emotion.EXCITED:
			return determineSendOutPosition(Strategy.INVOKE_CONFLICT,
					Strategy.HIGHEST_POSITION);

		case Emotion.ELATED:
			return determineSendOutPosition(Strategy.INVOKE_CONFLICT,
					Strategy.HIGHEST_POSITION);

		case Emotion.HAPPY:
			return determineSendOutPosition(Strategy.RANDOMLY,
					Strategy.RANDOMLY);

		case Emotion.CONTENT:
			return determineSendOutPosition(Strategy.AVOID_CONFLICT,
					Strategy.LOWEST_POSITION);

		case Emotion.TIRED:
			return determineSendOutPosition(Strategy.AVOID_CONFLICT,
					Strategy.LOWEST_POSITION);

		case Emotion.BORED:
			return determineSendOutPosition(Strategy.AVOID_CONFLICT,
					Strategy.LOWEST_POSITION);

		case Emotion.SAD:
			return determineSendOutPosition(Strategy.RANDOMLY,
					Strategy.RANDOMLY);

		case Emotion.ANGRY:
			return determineSendOutPosition(Strategy.INVOKE_CONFLICT,
					Strategy.HIGHEST_POSITION);

		case Emotion.NEUTRAL:
			return determineSendOutPosition(Strategy.RANDOMLY,
					Strategy.RANDOMLY);

		default:
			throw new RuntimeException("No specific emotion identified.");
		}

	}

	/**
	 * Determines which position should be chosen to send out an expert
	 * according to selected strategies.
	 */

	public Position determineSendOutPosition(int conflictStrategy,
			int positionValueStrategy) {

		List<Position> availablePositions = new ArrayList<Position>();
		for (Position curPosition : GameEngine.getInstance().getAllPositions()) {
			if (this.hasExpertToPlace(curPosition)) {
				availablePositions.add(curPosition);
			}
		}
		if (availablePositions.size() == 0) {
			return null;
		}

		int ranking;
		int maxRanking = 0;
		HashMap<Position, Integer> allRankings = new HashMap<Position, Integer>();

		for (Position curPosition : availablePositions) {

			int conflictRanking = determineConflictRanking(curPosition,
					conflictStrategy);
			int valueRanking = determineValueRanking(curPosition,
					positionValueStrategy);
			ranking = valueRanking * conflictRanking;

			allRankings.put(curPosition, ranking);

			if (ranking >= maxRanking) {
				maxRanking = ranking;
			}
		}

		// collect all positions with a maximum ranking
		List<Position> preferredPositions = new ArrayList<Position>();
		for (Position curPosition : availablePositions) {
			if (allRankings.get(curPosition) == maxRanking) {
				preferredPositions.add(curPosition);
			}
		}

		// choose one of the preferred positions randomly
		return preferredPositions.get(Utils
				.random(preferredPositions.size() - 1));

	}

	/**
	 * Returns a ranking value between 1 and 10 regarding indicating the value
	 * goodness of chosen position.
	 */
	public int determineValueRanking(Position position, int strategy) {
		switch (strategy) {
		case Strategy.HIGHEST_POSITION: {

			if (position instanceof TenThousandPosition) {
				return 10;
			} else if (position.getValue() == 6.000) {
				return 6;
			} else if (position.getValue() == 3.000) {
				return 3;
			} else if (position.getValue() == 1.000) {
				return 1;
			}

		}
		case Strategy.LOWEST_POSITION: {

			if (position.getValue() == 1.000) {
				return 10;
			} else if (position.getValue() == 3.000) {
				return 6;
			} else if (position.getValue() == 6.000) {
				return 3;
			} else if (position.getValue() == 10.000) {
				return 1;
			}

		}
		case Strategy.RANDOMLY: {
			return Utils.random(9) + 1;

		}
		default:
			throw new IllegalArgumentException("Invalid strategy: " + strategy);

		}

	}

	/** returns the activation value. */
	public int getActivation() {
		return this.activation;
	}

	/** returns the pleasure value. */
	public int getPleasure() {
		return this.pleasure;
	}

	/**
	 * Determines if there are any experts available for given position
	 */
	public boolean hasExpertToPlace(Position position) {

		for (Expert expert : this.getAllExperts()) {
			if (position.canPickUp(expert)
					&& expert.getStatus() == Expert.AVAILABLE) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Initializes this player.
	 */
	protected void init() {
		super.init();
		if (Properties.EMOTION_RESET && !staticEmotion) {
			this.activation = 0;
			this.pleasure = 0;
		}

	}

	@Override
	public boolean isEmotional() {
		return true;
	}

	/**
	 * Allows this player to react to incidents.
	 */
	public void realize(Incident incident) {
		switch (incident.getType()) {
		case Incident.EXPERT_ACCEPTED: {
			realizeExpertAccepted(incident);
			break;
		}

		case Incident.EXPERT_BANISHED: {
			realizeExpertBanished(incident);
			break;
		}

		case Incident.BALANCE_INCREASED: {
			realizeBalanceIncreased(incident);
			break;
		}

		case Incident.BALANCE_DECREASED: {
			realizeBalanceDecreased(incident);
			break;
		}
		default: {
			throw new IllegalArgumentException("Illegal incident type: "
					+ incident.getType());
		}
		}
	}

	/**
	 * Realizes that balance has been decreased.
	 */
	public void realizeBalanceDecreased(Incident incident) {

		int amount = (int) (this.pleasureSensitivity * incident.getIntensity() * (1 - this.pleasureTrim));

		Logger.log("  --- $ --- " + this
				+ "s Emotions negatively influenced by " + incident.getCauser()
				+ ", amount: " + amount);

		this.pleasure -= amount;

		if (this.pleasure < -60) {
			this.pleasure = -60;
		}

	}

	/**
	 * Realizes that balance has been increased.
	 */
	public void realizeBalanceIncreased(Incident incident) {

		int amount = (int) (this.pleasureSensitivity * incident.getIntensity() * (1 + this.pleasureTrim));

		Logger.log("  +++ $ +++ " + this
				+ "s Emotions positively influenced by " + incident.getCauser()
				+ ", amount: " + amount);

		this.pleasure += amount;

		if (this.pleasure > 60) {
			this.pleasure = 60;
		}

	}

	/**
	 * Realizes that an expert has been accepted.
	 */
	public void realizeExpertAccepted(Incident incident) {
		Logger
				.log("  +++ E +++ " + this
						+ "s Emotions positively influenced by "
						+ incident.getCauser());

		this.activation += this.activationSensitivity * incident.getIntensity()
				* (1 + this.activationTrim);

		if (this.activation > 60) {
			this.activation = 60;
		}

	}

	/**
	 * Realizes that an expert has been banished.
	 */
	public void realizeExpertBanished(Incident incident) {
		Logger
				.log("  --- E --- " + this
						+ "s Emotions negatively influenced by "
						+ incident.getCauser());

		this.activation -= this.activationSensitivity * incident.getIntensity()
				* (1 - this.activationTrim);

		if (this.activation < -60) {
			this.activation = -60;
		}

	}


	/**
	 * Determines which experts should be placed on the given position.
	 */
	public Expert solveConflict(Position position) {

		int emotion = Emotion.select(activation, pleasure);

		if (emotion == Emotion.ELATED || emotion == Emotion.HAPPY
				|| emotion == Emotion.CONTENT) {
			return solveConflict(position, Strategy.PREFER_NEW);

		} else if (emotion == Emotion.BORED || emotion == Emotion.SAD
				|| emotion == Emotion.ANGRY) {
			return solveConflict(position, Strategy.AVOID_NEW);

		} else if (emotion == Emotion.EXCITED || emotion == Emotion.TIRED
				|| emotion == Emotion.NEUTRAL) {
			return solveConflict(position, Strategy.RANDOMLY);
		} else {
			throw new RuntimeException("No specific emotion identified.");
		}
	}

	/**
	 * Determines which expert should be accepted according to selected
	 * strategy.
	 */
	public Expert solveConflict(Position position, int acceptingStrategy) {

		// create list with all new requests plus an extra (dummy) request for
		// the expert already placed on the position
		List<Request> allRequests = new ArrayList<Request>();
		allRequests.addAll(position.getRequests());
		if (position.getExpert() != null) {
			allRequests.add(new Request(position, position.getExpert(), 0));
		}

		// check: neither new requests nor placed expert
		if (allRequests.isEmpty()) {
			return null;
		}

		// determine maximum bribe
		int maxBribe = 0;
		for (Request curRequest : allRequests) {
			if (curRequest.getBribe() > maxBribe) {
				maxBribe = curRequest.getBribe();
			}
		}

		// determine ranking for all requests
		Map<Request, Integer> allRankings = new HashMap<Request, Integer>();
		int maxRanking = 0;
		for (Request curRequest : allRequests) {

			// determine default ranking (highest bribe)
			int defaultRanking;
			if (maxBribe != 0) {
				defaultRanking = curRequest.getBribe() / maxBribe * 10;
			} else {
				defaultRanking = 5;
			}

			// determine emotion-based ranking
			int emotionalRanking;
			if (acceptingStrategy == Strategy.PREFER_NEW) {
				if (curRequest.getExpert() != position.getExpert()) {
					emotionalRanking = 10;
				} else {
					emotionalRanking = 0;
				}
			} else if (acceptingStrategy == Strategy.AVOID_NEW) {
				if (curRequest.getExpert() == position.getExpert()) {
					emotionalRanking = 10;
				} else {
					emotionalRanking = 0;
				}
			} else if (acceptingStrategy == Strategy.RANDOMLY) {
				emotionalRanking = 5;
			} else {
				throw new IllegalArgumentException(
						"No accepting Strategy specified.");
			}

			// add combined ranking to rankings
			int totalRanking = defaultRanking + emotionalRanking;
			allRankings.put(curRequest, totalRanking);
			if (totalRanking > maxRanking) {
				maxRanking = totalRanking;
			}

		}

		// determine preferred requests
		List<Request> preferredRequests = new ArrayList<Request>();
		for (Request curRequest : allRequests) {
			if (allRankings.get(curRequest) == maxRanking) {
				preferredRequests.add(curRequest);
			}
		}

		// choose request
		int chosenIndex = Utils.random(preferredRequests.size() - 1);
		Request chosenRequest = preferredRequests.get(chosenIndex);
		return chosenRequest.getExpert();
	}

}
