package minesweeper.bot;

import minesweeper.model.Move;
import minesweeper.generator.MinefieldGenerator;
import minesweeper.model.Board;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TiraBotTest {
    
    private Bot tiraBot;
    private MinefieldGenerator generator;
    private Board board;
            
    @Before
    public void setUp() {
        // toivottavaa ilmeisesti vaihtaa tähän: this.tiraBot = BotSelect.getBot();
        this.tiraBot = new TiraBot();
        this.generator = new MinefieldGenerator();
        this.board = new Board(generator, 10, 10, 3);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void tiraBotGivesMove() {
        Move move = this.tiraBot.makeMove(this.board);
        assertTrue(move.x == 1);
        assertTrue(move.y == 1);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
