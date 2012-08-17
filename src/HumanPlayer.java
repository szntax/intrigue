import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Class of a player than can be controlled by a human player.
 */
public class HumanPlayer extends Player {

    /**
     * Constructor to initialize HumanPlayer.
     */
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public int determineSendOutBribe(Position position) {
        int bribe;
        Scanner scanner = new Scanner(System.in);
        Logger.log(this.getName()
                + ". Please determine Bribery - default value ("
                + position.getValue() + "): ");

        try {
            bribe = scanner.nextInt();
            if (bribe < 1) {
                Logger
                        .log("Bribe must be at least 1! Choosing default value ("
                                + position.getValue() + ")");
                return position.getValue();

            } else {
                return bribe;
            }
        } catch (InputMismatchException e) {
            // Wrong input must not result in a crash.
            Logger.log("Wrong Input! Choosing default value!");
            return position.getValue();

        }

    }

    @Override
    public Position determineSendOutPosition() {

        while (true) {
            Logger.log(this.getName() + "s Avalaible positions: ");

            // number of the chosen position

            int i = 0;

            List<Position> availablePositions = new ArrayList<Position>();

            for (Position curPosition : GameEngine.getInstance()
                    .getAllPositions()) {

                if (this.hasExpertToPlace(curPosition)) {
                    availablePositions.add(curPosition);
                    Logger.log(" (" + i + ") - " + curPosition.toString());
                    i++;
                }

            }
            if (availablePositions.size() == 0) {
                return null;
            } else {
                try {
                    int selectedNumber;
                    Scanner scanner = new Scanner(System.in);
                    Logger.log(this.getName() + ". Please select Position: ");
                    selectedNumber = scanner.nextInt();

                    for (Expert curExpert : this.getAllExperts()) {
                        if (availablePositions.get(selectedNumber).canPickUp(
                                curExpert)) {
                            return availablePositions.get(selectedNumber);
                        }
                    }
                    if (selectedNumber < 0
                            || selectedNumber >= availablePositions.size()) {
                        Logger.log("Wrong input!");
                    }
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Wrong value type: " + e);
                } catch (IndexOutOfBoundsException ie) {
                    // Wrong input must not result in a crash.
                    Logger.log("Wrong Value!");

                } catch (InputMismatchException e) {
                    // Wrong input must not result in a crash.
                    Logger.log("Wrong Input!");

                }
            }
        }
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
     * boolean method to determine if HumanPlayer is emotional. Although humans
     * should be considered as emotional, their gaming strategy is yet not
     * identifiable or connected to emotional Players.
     */
    @Override
    public boolean isEmotional() {
        return false;
    }

    @Override
    public void realize(Incident incident) {

    }

    @Override
    public Expert solveConflict(Position curPosition) {

        List<Request> curRequests = curPosition.getRequests();

        while (true) {
            if (curRequests.size() > 1) {
                int i = 0;
                for (Request curRequest : curRequests) {
                    Logger.log("(" + i + ") - " + curRequest.toString());
                    i++;
                }

                try {
                    int selectExpert;
                    Scanner scanner = new Scanner(System.in);
                    Logger.log(this.getName()
                            + ". Please select expert for position: "
                            + curPosition);
                    selectExpert = scanner.nextInt();
                    return curRequests.get(selectExpert).getExpert();
                } catch (IndexOutOfBoundsException ie) {
                    // Wrong input must not result in a crash.
                    Logger.log("Please select a correct number!");
                }
            } else if (curRequests.size() == 1) {
                return curRequests.get(0).getExpert();
            } else
                return null;

        }
    }
}
