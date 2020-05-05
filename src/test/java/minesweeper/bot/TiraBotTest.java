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

    @Before
    public void setUp() {
        this.generator = new MinefieldGenerator();
        this.realBoard = new Board(generator, 10, 10, 3);
        this.tiraBot = new TiraBot(this.realBoard.width, this.realBoard.height);
    }

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
    public void surroundingSquaresReturnCorrectAmountCorner00(){
        ShadowSquare sq = this.tiraBot.getSquare(0, 0);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq);
        assertEquals(3, neighbours.length());        
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountCorner99(){
        ShadowSquare sq = this.tiraBot.getSquare(9, 9);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq);
        assertEquals(3, neighbours.length());        
    }
    
    @Test
    public void surroundingSquaresReturnCorrectAmountSide03(){
        ShadowSquare sq = this.tiraBot.getSquare(0, 3);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq);
        assertEquals(5, neighbours.length());        
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountSide30(){
        ShadowSquare sq = this.tiraBot.getSquare(3, 0);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq);
        assertEquals(5, neighbours.length());        
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountSide93(){
        ShadowSquare sq = this.tiraBot.getSquare(9, 3);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq);
        assertEquals(5, neighbours.length());        
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountSide39(){
        ShadowSquare sq = this.tiraBot.getSquare(3, 9);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq);
        assertEquals(5, neighbours.length());
    }

    @Test
    public void surroundingSquaresReturnCorrectAmountMiddle33(){
        ShadowSquare sq = this.tiraBot.getSquare(3, 3);
        TiraList<ShadowSquare> neighbours = this.tiraBot.getSurroundingUnresolvedSquares(sq);
        assertEquals(8, neighbours.length());
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
