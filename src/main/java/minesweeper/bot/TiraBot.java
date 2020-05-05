/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.bot;

/*ArrayList is needed for: interface bot - getPossibleMoves(), 
 it is part of the main program and not used in TiraBot */
import java.util.ArrayList;
import java.util.Random;
import minesweeper.model.Board;
import minesweeper.model.GameStats;
import minesweeper.model.Move;
import minesweeper.model.MoveType;
import minesweeper.model.Highlight;
import minesweeper.model.ShadowSquare;
import minesweeper.util.TiraList;

public class TiraBot implements Bot {

    private TiraList<Move> nextMoves;
    private TiraList<ShadowSquare> nextAlreadyOpenedSquaresToCheck;
    private TiraList<ShadowSquare> candidatesForNextMove;
    private Move latestMove;
    // rng should be implemented, prio last, usage doesn't really affect bot
    private Random rng = new Random();
    private ShadowSquare[][] shadowBoard;
    private int width;
    private int height;
    private Board realBoard;
    private GameStats gameStats;
    private int debugCounter = 0;
    private long startTime;
    private long latestTime;

    public TiraBot(int width, int height) {
        this.width = width;
        this.height = height;
        this.nextMoves = new TiraList<>();
        this.latestMove = new Move(0, 0, Highlight.NONE);
        this.shadowBoard = new ShadowSquare[width][height];
        this.nextAlreadyOpenedSquaresToCheck = new TiraList<>();
        this.candidatesForNextMove = new TiraList<>();
        this.initialize();
        addMoveToQueue(new Move(MoveType.OPEN, 2, 2));
        this.startTime = System.currentTimeMillis();
        this.latestTime = startTime;
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
        //sloooow.. TiraList<ShadowSquare> moves = getUnopenedNotFlaggedSquares();
        System.out.println("count:" + debugCounter
                + " possible moves/unresolved squares:" // + moves.length()
                + " latestmove:" + latestMove.type + "-" + latestMove.x + ":" + latestMove.y
                + " time:" + (latestTime - startTime));
        debugCounter++;
        //debug
        //

        if (!nextMoves.isEmpty()) {
            latestMove = nextMoves.pollFirst();
            this.latestTime = System.currentTimeMillis();
            return latestMove;
        }

        decideNextMove();
        if (!nextMoves.isEmpty()) {
            //debug
            System.out.println("found something from candidates /next move not empty");

            latestMove = nextMoves.pollFirst();
            this.latestTime = System.currentTimeMillis();
            return latestMove;
        }

        //debug
        System.out.println("getting random move next");

        addMoveToQueue(getRandomMove());
        latestMove = nextMoves.pollFirst();
        this.latestTime = System.currentTimeMillis();
        return latestMove;
    }

    /**
     * method used by main program, I just give arraylist of unopened squares
     * This was probably used to give hints to player, I'am not implementing
     * that
     *
     * @param board real board used by main program
     * @return returns list of all unopened squares
     */
    @Override
    public ArrayList<Move> getPossibleMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        TiraList<ShadowSquare> unresolved = getUnopenedNotFlaggedSquares();
        for (int i = 0; i < unresolved.length(); i++) {
            ShadowSquare square = unresolved.get(i);
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
            //debug
            System.out.println("decide next:" + squareToEvaluate.getX() + ":"
                    + squareToEvaluate.getY()
                    + " surrF:" + squareToEvaluate.getSurroundingFlags()
                    + " surrM:" + squareToEvaluate.getNumberOfSurroundingMines());
            if (squareToEvaluate.isResolved()) {
                continue;
            }
            if (squareToEvaluate.getSurroundingFlags() == squareToEvaluate.getSurroundingMines()) {
                addMoveToQueue(new Move(MoveType.CHORD, squareToEvaluate.getX(), squareToEvaluate.getY()));
                System.out.println("chord");
                break;
            }

            if (squareToEvaluate.getSurroundingNotKnown() == squareToEvaluate.getSurroundingUnknownMines()) {
                setSurroundingToFlags(squareToEvaluate);
                System.out.println("surrToFlag");
                break;
            }
            if (squareToEvaluate.getSurroundingMines() == 2 && squareToEvaluate.getSurroundingFlags() == 0) {
                System.out.println("if 2");
                if (checkSquaresTwoMineStatus(squareToEvaluate)) {
                    break;
                }

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
                + " nextAlreadyOpenedSize:" + nextAlreadyOpenedSquaresToCheck.numItemInList());

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
        TiraList<ShadowSquare> surroundingSquares = getSurroundingUnresolvedSquares(sq);

        //debug
        System.out.println("setSurroundingToFlags:" + sq.getX() + ":" + sq.getY()
                + " startNeighbours" + sq.getStartingNeighbours()
                + " unknownsquares:" + sq.getSurroundingNotKnown()
                + " unknownmines" + sq.getSurroundingUnknownMines()
                + " surrFlags:" + sq.getSurroundingFlags()
                + " selfMines:" + sq.getNumberOfSurroundingMines());
        //debug

        for (int i = 0; i < surroundingSquares.length(); i++) {
            ShadowSquare surroundingSq = surroundingSquares.get(i);
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

            TiraList<ShadowSquare> surroundingSquares = getSurroundingUnresolvedSquares(sq);
            for (int i = 0; i < surroundingSquares.length(); i++) {
                ShadowSquare surroundingSq = surroundingSquares.get(i);
                if (!surroundingSq.isResolved()) {
                    surroundingSq.decreaseNotKnown();
                    if (!candidatesForNextMove.contains(surroundingSq)) {
                        candidatesForNextMove.addLast(surroundingSq);
                    };
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
        TiraList<ShadowSquare> surroundingSquares = getSurroundingUnresolvedSquares(sq);
        for (int i = 0; i < surroundingSquares.length(); i++) {
            ShadowSquare surroundingSq = surroundingSquares.get(i);
            if (!surroundingSq.isResolved() && !nextAlreadyOpenedSquaresToCheck.contains(surroundingSq)) {
                nextAlreadyOpenedSquaresToCheck.addLast(surroundingSq);
            }
        }
    }

    /**
     * returns TiraList of unresolved squares surrounding given square
     *
     * @param sq returns neighbours of this ShadowSquare as TiraList
     * @return TiraList of neighbouring ShadowSquares
     */
    protected TiraList<ShadowSquare> getSurroundingUnresolvedSquares(ShadowSquare sq) {
        int squarex = sq.getX();
        int squarey = sq.getY();
        TiraList<ShadowSquare> surroundingSquares = new TiraList<>();
        for (int widthModifier = -1; widthModifier < 2; widthModifier++) {
            for (int heightModifier = -1; heightModifier < 2; heightModifier++) {
                int neighbourX = widthModifier + squarex;
                int neighbourY = heightModifier + squarey;
                if (neighbourX >= 0 && neighbourX < this.width && neighbourY >= 0 && neighbourY < this.height) {
                    if (widthModifier == 0 && heightModifier == 0) {
                        continue;
                    }
                    if (!this.shadowBoard[neighbourX][neighbourY].isResolved()) {
                        surroundingSquares.add(this.shadowBoard[neighbourX][neighbourY]);
                    }
                }
            }
        }
        return surroundingSquares;
    }

    /**
     * return TiraList of unopened squares surrounding given square
     *
     * @param sq ShadowSquare you want neighbours of
     * @return TiraList of ShadowSquares
     */
    protected TiraList<ShadowSquare> getSurroundingUnopenedSquares(ShadowSquare sq) {
        int squarex = sq.getX();
        int squarey = sq.getY();
        TiraList<ShadowSquare> surroundingUnopenedSquares = new TiraList<>();
        for (int widthModifier = -1; widthModifier < 2; widthModifier++) {
            for (int heightModifier = -1; heightModifier < 2; heightModifier++) {
                int neighbourX = widthModifier + squarex;
                int neighbourY = heightModifier + squarey;
                if (neighbourX >= 0 && neighbourX < this.width && neighbourY >= 0 && neighbourY < this.height) {
                    if (widthModifier == 0 && heightModifier == 0) {
                        continue;
                    }
                    if (!this.shadowBoard[neighbourX][neighbourY].isOpened()) {
                        surroundingUnopenedSquares.add(this.shadowBoard[neighbourX][neighbourY]);
                    }
                }
            }
        }
        return surroundingUnopenedSquares;
    }

    /**
     * gives a list of common unopened neighbours of to ShadowSquares
     *
     * @param self ShadowSquare
     * @param other ShadowSquare
     * @return TiraList of ShadowSquares
     */
    protected TiraList<ShadowSquare> getCommonNeighbours(ShadowSquare self, ShadowSquare other) {
        TiraList<ShadowSquare> selfUnopenedNeigbours = getSurroundingUnopenedSquares(self);
        TiraList<ShadowSquare> otherUnopenedNeigbours = getSurroundingUnopenedSquares(other);
        TiraList<ShadowSquare> commonNeighbours = new TiraList<>();
        for (int i = 0; i < selfUnopenedNeigbours.length(); i++) {
            if (otherUnopenedNeigbours.contains(selfUnopenedNeigbours.get(i))) {
                commonNeighbours.add(selfUnopenedNeigbours.get(i));
            }
        }
        return commonNeighbours;
    }

    /**
     * return TiraList of unopened and unresolved squares surrounding given
     * square
     *
     * @param sq ShadowSquare you want neighbours of
     * @return TiraList of ShadowSquares
     */
    protected TiraList<ShadowSquare> getSurroundingUnresolvedAndUnopenedSquares(ShadowSquare sq) {
        int squarex = sq.getX();
        int squarey = sq.getY();
        TiraList<ShadowSquare> surroundingUnopenedSquares = new TiraList<>();
        for (int widthModifier = -1; widthModifier < 2; widthModifier++) {
            for (int heightModifier = -1; heightModifier < 2; heightModifier++) {
                int neighbourX = widthModifier + squarex;
                int neighbourY = heightModifier + squarey;
                if (neighbourX >= 0 && neighbourX < this.width && neighbourY >= 0 && neighbourY < this.height) {
                    if (widthModifier == 0 && heightModifier == 0) {
                        continue;
                    }
                    if (this.shadowBoard[neighbourX][neighbourY].isOpened() && !this.shadowBoard[neighbourX][neighbourY].isResolved()) {
                        surroundingUnopenedSquares.add(this.shadowBoard[neighbourX][neighbourY]);
                    }
                }
            }
        }
        return surroundingUnopenedSquares;
    }

    /**
     * gives information to surrounding squares that this square was flagged
     * also sets surrounding squares to be evaluated for possible next move
     *
     * @param sq ShadowSquare that was flagged
     */
    protected void resolveMoveTypeFlag(ShadowSquare sq) {
        TiraList<ShadowSquare> surroundingSquares = getSurroundingUnresolvedSquares(sq);
        for (int i = 0; i < surroundingSquares.length(); i++) {
            ShadowSquare surroundingSq = surroundingSquares.get(i);
            if (!surroundingSq.isResolved()) {
                surroundingSq.incrementSurroundingFlags();
                candidatesForNextMove.addLast(surroundingSq);
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

        TiraList<ShadowSquare> unopened = getUnopenedNotFlaggedSquares();
        if (!unopened.isEmpty()) {
            ShadowSquare sq = unopened.get(rng.nextInt(unopened.length()));
            //debug
            System.out.println("random move sq:" + sq.getX() + ":" + sq.getY());
            return new Move(MoveType.OPEN, sq.getX(), sq.getY());
        }
        //debug
        System.out.println("green random highlight changed to starting move..");
        return new Move(MoveType.OPEN, 2, 2);
    }

    /**
     * Used to get a list of squares that still need to be resolved
     *
     * @return TiraList of squares
     */
    public TiraList<ShadowSquare> getUnopenedNotFlaggedSquares() {
        TiraList<ShadowSquare> unopenedSquares = new TiraList<>();
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
        TiraList<ShadowSquare> surroundingSquares = getSurroundingUnresolvedSquares(sq);
        for (int x = 0; x < surroundingSquares.length(); x++) {
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
    public TiraList<Move> getNextMoves() {
        return nextMoves;
    }

    /**
     *
     * @return normal getter
     */
    public TiraList<ShadowSquare> getNextAlreadyOpenedSquaresToCheck() {
        return nextAlreadyOpenedSquaresToCheck;
    }

    /**
     *
     * @return normal getter
     */
    public TiraList<ShadowSquare> getCandidatesForNextMove() {
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

    private boolean checkSquaresTwoMineStatus(ShadowSquare squareToEvaluate) {
        TiraList<ShadowSquare> surroundingSq = getSurroundingUnresolvedSquares(squareToEvaluate);
        if (surroundingSq.length() == 5) {
            TiraList<ShadowSquare> opened = new TiraList<>();
            TiraList<ShadowSquare> unopened = new TiraList<>();
            for (int i = 0; i < surroundingSq.length(); i++) {
                ShadowSquare sq = surroundingSq.get(i);
                if (sq.isOpened()) {
                    if (sq.getSurroundingUnknownMines() == 1) {
                        opened.add(sq);
                    }
                }
                if (!sq.isOpened()) {
                    unopened.add(sq);
                }
            }
            if (opened.length() == 2 && unopened.length() == 3) {
                int x = unopened.get(0).getX();
                int y = unopened.get(0).getY();
                if (x == unopened.get(1).getX() && x == unopened.get(2).getX() && opened.get(0).getX() == opened.get(1).getX()) {
                    y = squareToEvaluate.getY();
                    addMoveToQueue(new Move(MoveType.OPEN, x, y));
                    addMoveToQueue(new Move(MoveType.FLAG, x, y + 1));
                    addMoveToQueue(new Move(MoveType.FLAG, x, y - 1));
                    return true;
                }
                if (y == unopened.get(1).getY() && y == unopened.get(2).getY() && opened.get(0).getY() == opened.get(1).getY()) {
                    x = squareToEvaluate.getX();
                    addMoveToQueue(new Move(MoveType.OPEN, x, y));
                    addMoveToQueue(new Move(MoveType.FLAG, x + 1, y));
                    addMoveToQueue(new Move(MoveType.FLAG, x - 1, y));
                    return true;
                }
            }
        }
        return false;
    }

}
