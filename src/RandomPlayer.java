/**
 * Class of RandomPlayer. RandomPlayer determines most of its actions randomly.
 */
public class RandomPlayer extends Player {

    /**
     * Constructor for RandomPlayer
     */
    public RandomPlayer(String name) {
        super(name);
        this.Type = Main.STRATEGY_r;
    }

    /**
     * decide which Expert is going to be placed on the position, may be null.
     */
    public Expert solveConflict(Position curPosition) {
        return solveConflictsRandomly(curPosition);
    }

    @Override
    public void realize(Incident incident) {
    }

    @Override
    public boolean isEmotional() {
        return false;
    }

    @Override
    public int determineSendOutBribe(Position position) {
        return determineSendOutBribeRandomly(position);
    }

    @Override
    public Position determineSendOutPosition() {
        return determineSendOutPositionRandomly();
    }

}
