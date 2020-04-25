/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.bot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;
import minesweeper.model.Board;
import minesweeper.model.GameStats;
import minesweeper.model.Move;
import minesweeper.model.MoveType;
import minesweeper.model.Highlight;
import minesweeper.model.ShadowSquare;

public class TiraBot implements Bot {

    private ArrayDeque<Move> nextMoves;
    private ArrayDeque<ShadowSquare> nextAlreadyOpenedSquaresToCheck;
    private ArrayDeque<ShadowSquare> candidatesForNextMove;
    private Move latestMove;
    private Random rng = new Random();
    private ShadowSquare[][] shadowBoard;
    private int width;
    private int height;
    private Board realBoard;
    private GameStats gameStats;
    private int debugCounter = 0;

    public TiraBot(int width, int height) {
        this.width = width;
        this.height = height;
        this.nextMoves = new ArrayDeque<>();
        this.latestMove = new Move(0, 0, Highlight.NONE);
        this.shadowBoard = new ShadowSquare[width][height];
        this.nextAlreadyOpenedSquaresToCheck = new ArrayDeque<>();
        this.candidatesForNextMove = new ArrayDeque<>();
        this.initialize();
        addMoveToQueue(new Move(MoveType.OPEN, 2, 2));
    }

    /**
     * main method of giving moves to minesweeper program AI always starts with
     * move open 2,2 and evaluates previous responses to decide next moves. If
     * moves are not found then random open move is given
     *
     * @param board real board given by minesweeper program
     * @return gives AI created move to minesweeper program
     */
    @Override
    public Move makeMove(Board board) {

        this.realBoard = board;
        processLastMove();

        //debug
        ArrayList<ShadowSquare> moves = getUnopenedNotFlaggedSquares();
        System.out.println("count:" + debugCounter
                + " possible moves/unresolved squares:" + moves.size()
                + " latestmove:" + latestMove.type + "-" + latestMove.x + ":" + latestMove.y);
        debugCounter++;
        //debug
        //

        if (!nextMoves.isEmpty()) {
            latestMove = nextMoves.pollFirst();
            return latestMove;
        }

        decideNextMove();
        if (!nextMoves.isEmpty()) {
            //debug
            System.out.println("found something from candidates /next move not empty");

            latestMove = nextMoves.pollFirst();
            return latestMove;
        }

        //debug
        System.out.println("getting random move next");

        addMoveToQueue(getRandomMove());
        latestMove = nextMoves.pollFirst();
        return latestMove;
    }

    /**
     * method used by main program, I just give arraylist of unopened squares
     * This was propably used to give hints to player, I'am not implementing
     * that
     *
     * @param board real board used by main program
     * @return returns list of all unopened squares
     */
    @Override
    public ArrayList<Move> getPossibleMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<ShadowSquare> unresolved = getUnopenedNotFlaggedSquares();
        for (ShadowSquare square : unresolved) {
            moves.add(new Move(MoveType.OPEN, square.getX(), square.getY()));
        }
        return moves;
    }

    /**
     * method used by main program, I haven't found use for this
     *
     * @param gameStats has some info thought to be useful by somebody else
     */
    @Override
    public void setGameStats(GameStats gameStats) {
        this.gameStats = gameStats;
    }

    /**
     * uses list provided by processing last move to decide next move if move is
     * not found the main makeMove method handles it
     */
    protected void decideNextMove() {
        while (!candidatesForNextMove.isEmpty()) {
            ShadowSquare squareToEvaluate = candidatesForNextMove.pollFirst();
            if (squareToEvaluate.isResolved()) {
                continue;
            }
            if (squareToEvaluate.surroundingFlags() == squareToEvaluate.getSurroundingMines()) {
                addMoveToQueue(new Move(MoveType.CHORD, squareToEvaluate.getX(), squareToEvaluate.getY()));
                continue;
            }
            if (squareToEvaluate.getSurroundingNotKnown() == squareToEvaluate.getSurroundingUnknownMines()) {
                setSurroundingToFlags(squareToEvaluate);
            }
        }
    }

    /**
     * adds given move to queue of moves, queue should not be used outside of
     * this method method should prune invalid moves
     *
     * @param move is a move added to queue of moves to be made
     */
    protected void addMoveToQueue(Move move) {
        ShadowSquare sq = shadowBoard[move.x][move.y];
        if (!sq.isResolved()) {
            if (move.type == MoveType.CHORD) {
                sq.setToResolved();
                this.nextMoves.addLast(move);
            }
            if (move.type == MoveType.FLAG && !sq.isOpened()) {
                sq.setToResolved();
                sq.setToFlagged();
                this.nextMoves.addLast(move);
            }
            if (move.type == MoveType.OPEN && !sq.isOpened()) {
                this.nextMoves.addLast(move);
            }
        }
    }

    /**
     * Should process information from previous move and add promising squares
     * to a list that is used to decide next move
     */
    protected void processLastMove() {
        int x = latestMove.x;
        int y = latestMove.y;
        MoveType type = latestMove.type;
        ShadowSquare latestMoveSq = shadowBoard[x][y];

        if (type == MoveType.OPEN) {
            int mines = realBoard.getSquareAt(x, y).surroundingMines();
            setNumberOfMines(latestMoveSq, mines);
            resolveMoveTypeOpen(latestMoveSq);
        }

        if (type == MoveType.CHORD) {
            setSurroundingOpenedSquaresToBeChecked(latestMoveSq);
        }

        //debug
        System.out.println("count:" + debugCounter
                + " nextAlreadyOpenedSize:" + nextAlreadyOpenedSquaresToCheck.size());

        while (!nextAlreadyOpenedSquaresToCheck.isEmpty()) {
            ShadowSquare squareToCheck = nextAlreadyOpenedSquaresToCheck.pollFirst();
            setNumberOfMines(squareToCheck,
                    realBoard.getSquareAt(squareToCheck.getX(), squareToCheck.getY()).surroundingMines());
            resolveMoveTypeOpen(squareToCheck);
        }

        if (type == MoveType.FLAG) {
            resolveMoveTypeFlag(latestMoveSq);
        }

    }

    /**
     * Method should only be used when previous moves have been evaluated gives
     * moves to minesweeper program that sets squares surrounding this one to
     * flags
     *
     * @param sq shadowsquare you want to surround with flags
     */
    protected void setSurroundingToFlags(ShadowSquare sq) {
        ArrayList<ShadowSquare> surroundingSquares = getSurroundingSquares(sq);

        //debug
        System.out.println("setSurroundingToFlags:" + sq.getX() + ":" + sq.getY()
                + " startNeighbours" + sq.getStartingNeighbours()
                + " unknownsquares:" + sq.getSurroundingNotKnown()
                + " unknownmines" + sq.getSurroundingUnknownMines()
                + " surrFlags:" + sq.surroundingFlags()
                + " selfMines:" + sq.getNumberOfSurroundingMines());
        //debug

        for (int x = 0; x < surroundingSquares.size(); x++) {
            ShadowSquare surroundingSq = surroundingSquares.get(x);
            if (!surroundingSq.isResolved() && !surroundingSq.isOpened()) {
                addMoveToQueue(new Move(MoveType.FLAG, surroundingSq.getX(), surroundingSq.getY()));
            }
        }
    }

    /**
     * sets square to opened status and gives surrounding squares information
     * that this one was opened should not open square more than once also sets
     * surrounding squares to be evaluated for possible next move
     *
     * @param sq shadowsquare to process
     */
    protected void resolveMoveTypeOpen(ShadowSquare sq) {
        if (!sq.isOpened()) {
            sq.setToOpened();

            ArrayList<ShadowSquare> surroundingSquares = getSurroundingSquares(sq);
            for (ShadowSquare surroundingSq : surroundingSquares) {
                if (!surroundingSq.isResolved()) {
                    surroundingSq.decreaseNotKnown();
                    candidatesForNextMove.add(surroundingSq);
                }
            }
        }
    }

    /**
     * Used for chord moves and squares with zero mines, sets surrounding
     * squares to be evaluated, since they have been opened
     *
     * @param sq square that was opened with chord or contains zero mines
     */
    protected void setSurroundingOpenedSquaresToBeChecked(ShadowSquare sq) {
        ArrayList<ShadowSquare> surroundingSquares = getSurroundingSquares(sq);
        for (ShadowSquare surroundingSq : surroundingSquares) {
            if (!surroundingSq.isResolved() && !nextAlreadyOpenedSquaresToCheck.contains(surroundingSq)) {
                nextAlreadyOpenedSquaresToCheck.addLast(surroundingSq);
            }
        }
    }

    /**
     * returns ArrayList of squares surrounding given square uglyish code that I
     * didn't want to repeat in multiple places
     *
     * @param sq returns neighbours of this ShadowSquare as ArrayList (list type
     * subject to change)
     * @return ArrayList of neighbouring ShadowSquares
     */
    protected ArrayList<ShadowSquare> getSurroundingSquares(ShadowSquare sq) {
        int x = sq.getX();
        int y = sq.getY();
        ArrayList<ShadowSquare> surroundingSquares = new ArrayList<>();
        for (int xx = -1; xx < 2; xx++) {
            for (int yy = -1; yy < 2; yy++) {
                int xxx = xx + x;
                int yyy = yy + y;
                if (xxx >= 0 && xxx < this.width && yyy >= 0 && yyy < this.height) {
                    if (xx == 0 && yy == 0) {
                        continue;
                    }
                    surroundingSquares.add(this.shadowBoard[xxx][yyy]);
                    //mildly ugly, should be cleaned if I get a better idea
                }
            }
        }
        return surroundingSquares;
    }

    /**
     * gives information to surrounding squares that this square was flagged
     * also sets surrounding squares to be evaluated for possible next move
     *
     * @param sq ShadowSquare that was flagged
     */
    protected void resolveMoveTypeFlag(ShadowSquare sq) {
        ArrayList<ShadowSquare> surroundingSquares = getSurroundingSquares(sq);
        for (ShadowSquare surroundingSq : surroundingSquares) {
            if (!surroundingSq.isResolved()) {
                surroundingSq.incrementSurroundingFlags();
                candidatesForNextMove.add(surroundingSq);
            }
        }
    }

    /**
     * sets number of mines to shadow square from what is given by the program
     * adds square to a list of possible next moves to be evaluated and if mines
     * are zero then checks surrounding squares and marks this square as
     * evaluated
     *
     * @param sq square to set number of mines
     * @param mines number of mines around this square
     */
    protected void setNumberOfMines(ShadowSquare sq, int mines) {
        sq.setNumberOfSurroundingMines(mines);
        if (mines > 0) {
            candidatesForNextMove.addLast(sq);
        }
        if (mines == 0) {
            sq.setToResolved();
            setSurroundingOpenedSquaresToBeChecked(sq);
        }
    }

    /**
     * When all information has been used and still there is no good move to be
     * made
     *
     * @return gives random open -type move from list of unresolved squares
     */
    protected Move getRandomMove() {
        if (realBoard.gameWon || realBoard.gameLost) {
            System.out.println("game won/lost");
            return new Move(MoveType.OPEN, 2, 2);
        }

        ArrayList<ShadowSquare> unopened = getUnopenedNotFlaggedSquares();
        if (!unopened.isEmpty()) {
            ShadowSquare sq = unopened.get(rng.nextInt(unopened.size()));
            System.out.println("random move sq:" + sq.getX() + ":" + sq.getY());
            return new Move(MoveType.OPEN, sq.getX(), sq.getY());
        }
        System.out.println("green random highlight changed to starting move..");
        return new Move(MoveType.OPEN, 2, 2);
    }

    /**
     * Used to get a list of squares that still need to be resolved
     *
     * @return ArrayList of squares
     */
    public ArrayList<ShadowSquare> getUnopenedNotFlaggedSquares() {
        ArrayList<ShadowSquare> unopenedSquares = new ArrayList<>();
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (!shadowBoard[x][y].isOpened() && !shadowBoard[x][y].isFlagged()) {
                    unopenedSquares.add(shadowBoard[x][y]);
                }
            }
        }
        return unopenedSquares;
    }

    /**
     *
     * @param sq is the fake square At the moment this is method not used for
     * anything.. to be deleted if use is not found..
     */
    protected void setSurroundingSquaresToResolved(ShadowSquare sq) {
        ArrayList<ShadowSquare> surroundingSquares = getSurroundingSquares(sq);
        for (int x = 0; x < surroundingSquares.size(); x++) {
            ShadowSquare surroundingSq = surroundingSquares.get(x);
            surroundingSq.setToResolved();
        }
    }

    /**
     * returns shadowSquare at coordinates xy
     *
     * @param x width
     * @param y height
     * @return ShadowSquare type object
     */
    public ShadowSquare getSquare(int x, int y) {
        return this.shadowBoard[x][y];
    }

    /**
     * Initializes a fake board to be used to save information gained from the
     * minesweeper program
     */
    private void initialize() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                // givin borders and corners different neighbours
                if (x == 0 || y == 0 || x == this.width - 1 || y == this.height - 1) {
                    //sides
                    if (x == 0 && y > 0 && y < height - 1) {
                        this.shadowBoard[x][y] = new ShadowSquare(x, y, 5);
                    }
                    if (x == width - 1 && y > 0 && y < height - 1) {
                        this.shadowBoard[x][y] = new ShadowSquare(x, y, 5);
                    }
                    if (y == 0 && x > 0 && x < width - 1) {
                        this.shadowBoard[x][y] = new ShadowSquare(x, y, 5);
                    }
                    if (y == height - 1 && x > 0 && x < width - 1) {
                        this.shadowBoard[x][y] = new ShadowSquare(x, y, 5);
                    }
                    //corners
                    if (x == 0 && y == 0) {
                        this.shadowBoard[x][y] = new ShadowSquare(x, y, 3);
                    }
                    if (x == 0 && y == this.height - 1) {
                        this.shadowBoard[x][y] = new ShadowSquare(x, y, 3);
                    }
                    if (x == this.width - 1 && y == 0) {
                        this.shadowBoard[x][y] = new ShadowSquare(x, y, 3);
                    }
                    if (x == this.width - 1 && y == this.height - 1) {
                        this.shadowBoard[x][y] = new ShadowSquare(x, y, 3);
                    }
                } else {
                    this.shadowBoard[x][y] = new ShadowSquare(x, y, 8);
                }
            }
        }
    }

    /**
     *
     * @return normal getter
     */
    public ArrayDeque<Move> getNextMoves() {
        return nextMoves;
    }

    /**
     *
     * @return normal getter
     */
    public ArrayDeque<ShadowSquare> getNextAlreadyOpenedSquaresToCheck() {
        return nextAlreadyOpenedSquaresToCheck;
    }

    /**
     *
     * @return normal getter
     */
    public ArrayDeque<ShadowSquare> getCandidatesForNextMove() {
        return candidatesForNextMove;
    }

    /**
     *
     * @return normal getter
     */
    public Move getLatestMove() {
        return latestMove;
    }

    /**
     *
     * @return normal getter
     */
    public ShadowSquare[][] getShadowBoard() {
        return shadowBoard;
    }

}
