package minesweeper.bot;

import java.util.ArrayList;
import minesweeper.model.Move;
import minesweeper.generator.MinefieldGenerator;
import minesweeper.model.Board;
import minesweeper.model.GameStats;
import minesweeper.model.MoveType;
import minesweeper.model.ShadowSquare;
import minesweeper.util.TiraList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TiraBotTest {

    private TiraBot tiraBot;
    private MinefieldGenerator generator;
    private Board realBoard;
    private ShadowSquare sq;

    @Before
    public void setUp() {
        this.generator = new MinefieldGenerator(1234); //seed 1234
        this.realBoard = new Board(generator, 10, 10, 10);
        this.tiraBot = new TiraBot(this.realBoard.width, this.realBoard.height);
        this.sq = this.tiraBot.getSquare(0, 0);
    }

    /*
board if needed
0000012210
000001xx21
000001222x
1100111011
x1002x2000
12112x2011
13x211101x
1xx2000011
1221000000
0000000000
     */
    @After
    public void tearDown() {
    }

    @Test
    public void tiraBotGivesFirstMove() {
        Move move = this.tiraBot.makeMove(this.realBoard);
        assertTrue(move.type == MoveType.OPEN);
    }

    @Test
    public void rightAmountOfPossibleMovesGiven() {
        ArrayList<Move> moves = this.tiraBot.getPossibleMoves(realBoard);
        assertEquals(100, moves.size());
    }

    @Test
    public void gameStatsGiven() {

    }

    @Test
    public void surroundingSquaresReturnCorrectAmountCorner00() {
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq);
        assertEquals(3, neighbours.length());
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountCorner99() {
        ShadowSquare sq99 = this.tiraBot.getSquare(9, 9);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq99);
        assertEquals(3, neighbours.length());
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountSide03() {
        ShadowSquare sq03 = this.tiraBot.getSquare(0, 3);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq03);
        assertEquals(5, neighbours.length());
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountSide30() {
        ShadowSquare sq30 = this.tiraBot.getSquare(3, 0);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq30);
        assertEquals(5, neighbours.length());
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountSide93() {
        ShadowSquare sq93 = this.tiraBot.getSquare(9, 3);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq93);
        assertEquals(5, neighbours.length());
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountSide39() {
        ShadowSquare sq39 = this.tiraBot.getSquare(3, 9);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq39);
        assertEquals(5, neighbours.length());
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountMiddle33() {
        ShadowSquare sq33 = this.tiraBot.getSquare(3, 3);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq33);
        assertEquals(8, neighbours.length());
    }

    @Test
    public void setNumberOfMinesIfZero() {
        this.tiraBot.setNumberOfMines(sq, 0);
        assertTrue(this.tiraBot.getSquare(0, 0).isResolved());
        assertFalse(this.tiraBot.getNextAlreadyOpenedSquaresToCheck().isEmpty());
    }

    @Test
    public void setNumberOfMinesGreaterThanZero() {
        this.tiraBot.setNumberOfMines(sq, 1);
        assertFalse(this.tiraBot.getCandidatesForNextMove().isEmpty());
    }

    @Test
    public void moveTypeFlagIncrementsFlagsKnownToSurrounding() {
        this.tiraBot.resolveMoveTypeFlag(sq);
        assertEquals(1, this.tiraBot.getSquare(0, 1).getSurroundingFlags());
    }

    @Test
    public void moveTypeFlagAddsMoveCandidates() {
        this.tiraBot.resolveMoveTypeFlag(sq);
        assertFalse(this.tiraBot.getCandidatesForNextMove().isEmpty());
    }

    @Test
    public void getSurroundingUnresOpenedSquares() {
        this.tiraBot.getSquare(0, 1).setToOpened();
        TiraList<ShadowSquare> sqList = this.tiraBot.getSurroundingUnresolvedAndOpenedSquares(sq);
        assertEquals(this.tiraBot.getSquare(0, 1), sqList.pollFirst());
    }

    @Test
    public void getSurroundingUnresUnopenedSquares() {
        this.tiraBot.getSquare(0, 1).setToOpened();
        TiraList<ShadowSquare> sqList = this.tiraBot.getSurroundingUnresolvedAndUnopenedSquares(sq);
        assertTrue(sqList.contains(this.tiraBot.getSquare(1, 1)));
    }

    @Test
    public void getSurroundingUnopenedUnFlaggedSquares() {
        this.tiraBot.getSquare(0, 1).setToFlagged();
        TiraList<ShadowSquare> sqList = this.tiraBot.getSurroundingUnopenedAndUnflaggedSquares(sq);
        assertTrue(sqList.contains(this.tiraBot.getSquare(1, 1)));
        assertTrue(sqList.contains(this.tiraBot.getSquare(1, 0)));
        assertEquals(2, sqList.length());
    }

    @Test
    public void getCommonNeigbours() {
        TiraList<ShadowSquare> sqList = this.tiraBot.getCommonNeighbours(sq, this.tiraBot.getSquare(0, 2));
        assertTrue(sqList.contains(this.tiraBot.getSquare(1, 1)));
        assertTrue(sqList.contains(this.tiraBot.getSquare(0, 1)));
        assertEquals(2, sqList.length());
    }

    @Test
    public void setSurroundingAlreadyOpenedToChecked() {
        this.tiraBot.getSquare(0, 1).setToOpened();
        this.tiraBot.setSurroundingOpenedSquaresToBeChecked(sq);
        TiraList<ShadowSquare> sqList = this.tiraBot.getNextAlreadyOpenedSquaresToCheck();
        assertEquals(3, sqList.length());
        assertTrue(sqList.contains(this.tiraBot.getSquare(0, 1)));
        assertTrue(sqList.contains(this.tiraBot.getSquare(1, 1)));
        assertTrue(sqList.contains(this.tiraBot.getSquare(1, 0)));
    }

    @Test
    public void resolveMoveTypeWhenIsOpened() {
        sq.setToOpened();
        this.tiraBot.resolveMoveTypeOpen(sq);
        assertTrue(this.tiraBot.getCandidatesForNextMove().isEmpty());
    }

    @Test
    public void resolveMoveTypeWhenNotOpen() {
        this.tiraBot.resolveMoveTypeOpen(sq);
        assertEquals(4, this.tiraBot.getSquare(0, 1).getSurroundingNotKnown());
        assertEquals(3, this.tiraBot.getCandidatesForNextMove().length());
    }

    @Test
    public void setSurroundingToFlags() {
        this.tiraBot.getSquare(0, 1).setToOpened();
        this.tiraBot.setSurroundingToFlags(sq);
        assertFalse(this.tiraBot.getSquare(0, 1).isFlagged());
        assertTrue(this.tiraBot.getSquare(1, 1).isFlagged());
        assertTrue(this.tiraBot.getSquare(1, 0).isFlagged());
    }

    @Test
    public void addChordToQueue() {
        this.tiraBot.addMoveToQueue(new Move(MoveType.CHORD, 0, 0));
        assertTrue(sq.isResolved());
        assertFalse(sq.isFlagged());
        assertFalse(this.tiraBot.getNextMoves().isEmpty());
    }

    @Test
    public void addFlagToQueue() {
        this.tiraBot.addMoveToQueue(new Move(MoveType.FLAG, 0, 0));
        assertTrue(sq.isFlagged());
        assertFalse(this.tiraBot.getNextMoves().isEmpty());
    }

    @Test
    public void addOpenToQueue() {
        this.tiraBot.addMoveToQueue(new Move(MoveType.OPEN, 0, 0));
        assertFalse(this.tiraBot.getNextMoves().isEmpty());
    }

    @Test
    public void randomMoveGivesMove() {
        this.tiraBot.makeMove(realBoard);
        Move move = this.tiraBot.getRandomMove();
        assertEquals(move.type, MoveType.OPEN);
    }

    @Test
    public void processLatestMoveOpen() {
        realBoard.makeMove(this.tiraBot.makeMove(realBoard));
        this.tiraBot.setLatestMove(new Move(MoveType.OPEN, 0, 0));
        this.tiraBot.processLastMove();
        assertEquals(0, sq.getSurroundingMines());
    }

    @Test
    public void processLatestMoveChord() {
        realBoard.makeMove(this.tiraBot.makeMove(realBoard));
        Move flagMove04 = new Move(MoveType.FLAG, 0, 4);
        Move chordMove14 = new Move(MoveType.CHORD, 1, 4);

        this.tiraBot.addMoveToQueue(flagMove04);
        this.tiraBot.addMoveToQueue(chordMove14);

        realBoard.makeMove(this.tiraBot.makeMove(realBoard));
        realBoard.makeMove(this.tiraBot.makeMove(realBoard));
        this.tiraBot.processLastMove();
        assertEquals(1, this.tiraBot.getSquare(0, 5).getNumberOfSurroundingMines());
    }

    @Test
    public void processLatestMoveFlag() {
        realBoard.makeMove(this.tiraBot.makeMove(realBoard));
        Move flagMove04 = new Move(MoveType.FLAG, 0, 4);
        this.tiraBot.addMoveToQueue(flagMove04);
        realBoard.makeMove(this.tiraBot.makeMove(realBoard));
        this.tiraBot.processLastMove();
        assertEquals(1, this.tiraBot.getSquare(1, 5).getSurroundingFlags());
        assertEquals(2, this.tiraBot.getSquare(0, 5).getSurroundingNotKnown());
    }

    /* list of what to test:
        public Move makeMove(Board board)
        public void setGameStats(GameStats gameStats)
        protected void decideNextMove()
        protected void addMoveToQueue(Move move)
        protected void processLastMove()
        protected void setSurroundingToFlags(ShadowSquare sq)
        protected void resolveMoveTypeOpen(ShadowSquare sq)
        protected void setSurroundingOpenedSquaresToBeChecked(ShadowSquare sq)
        protected void resolveMoveTypeFlag(ShadowSquare sq)
        protected void setNumberOfMines(ShadowSquare sq, int mines)
        protected Move getRandomMove()
        public ArrayList<ShadowSquare> getUnresolvedSquares()
        protected void setSurroundingSquaresToResolved(ShadowSquare sq)
        public ShadowSquare getSquare(int x, int y)
        private void initialize()
    
        public ArrayDeque<Move> getNextMoves()
        public ArrayDeque<ShadowSquare> getNextAlreadyOpenedSquaresToCheck()
        public ArrayDeque<ShadowSquare> getCandidatesForNextMove()
        public Move getLatestMove()
        public ShadowSquare[][] getShadowBoard()
     */
}
