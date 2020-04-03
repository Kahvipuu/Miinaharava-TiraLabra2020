package minesweeper.bot;

import minesweeper.model.Move;
import minesweeper.generator.MinefieldGenerator;
import minesweeper.model.Board;
import minesweeper.model.MoveType;

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
    public void hello() {
    }


    /* list of what to test:
        public Move makeMove(Board board)
        public ArrayList<Move> getPossibleMoves(Board board)
        public void setGameStats(GameStats gameStats)
        protected void decideNextMove()
        protected void addMoveToQueue(Move move)
        protected void processLastMove()
        protected void setSurroundingToFlags(ShadowSquare sq)
        protected void resolveMoveTypeOpen(ShadowSquare sq)
        protected void setSurroundingOpenedSquaresToBeChecked(ShadowSquare sq)
        protected ArrayList<ShadowSquare> getSurroundingSquares(ShadowSquare sq)
        protected void resolveMoveTypeFlag(ShadowSquare sq)
        protected void setNumberOfMines(ShadowSquare sq, int mines)
        protected Move getRandomMove()
        public ArrayList<ShadowSquare> getUnresolvedSquares()
        protected void setSurroundingSquaresToResolved(ShadowSquare sq)
        public ShadowSquare getSquare(int x, int y)
        private void initialize()
    
    */
    
}
