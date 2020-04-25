package minesweeper.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.concurrent.TimeUnit;

/**
 * Class used for tracking game statistics
 */
public class GameStats {

    public ObservableList<Move> moves;
    public double cumulativeEuclidianDistance = 0;
    public double cumulativeTime = 0;
    public long startTime = System.nanoTime();

    private boolean firstMove = true;
    private Move lastMove;

    public GameStats() {
        moves = FXCollections.observableArrayList();
    }

    /**
     * Update the statistics with a new move
     *
     * @param move Move object created by the GUI or a Bot
     */
    public void update(Move move) {
        Double dtime;
        if (firstMove) {
            dtime = (Double) deltaTimeInMicroSeconds(startTime, move);
            move.setEuclideanDistance(0d);
            firstMove = false;
        } else {
            dtime = (Double) deltaTimeInMicroSeconds(lastMove, move);
            move.setEuclideanDistance(deltaEuclideanDistance(lastMove, move));
        }
        move.deltaTime = dtime;

        moves.add(0, move);

        cumulativeTime += dtime;
        cumulativeEuclidianDistance += move.euclideanDistance;
        lastMove = move;
    }

    /**
     * Find the euclidean distance between two moves
     *
     * @param move1 Sequentially the first move
     * @param move2 Sequentially the second move
     * @return double
     */
    public double deltaEuclideanDistance(Move move1, Move move2) {
        return Math.hypot(move2.x - move1.x, move2.y - move1.y);
    }

    public static double deltaTimeInMicroSeconds(Move lastMove, Move currentMove) {
        return (double) TimeUnit.NANOSECONDS.toMicros(currentMove.timestamp - lastMove.timestamp);
    }

    public static double deltaTimeInMicroSeconds(long firstTime, Move move) {
        return (double) TimeUnit.NANOSECONDS.toMicros(move.timestamp - firstTime);
    }
}
