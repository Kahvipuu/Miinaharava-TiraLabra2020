/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package minesweeper;

import java.io.File;
import java.io.PrintStream;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import minesweeper.model.GameStats;
import minesweeper.model.Move;
import minesweeper.model.Board;
import minesweeper.generator.MinefieldGenerator;
import minesweeper.bot.Bot;
import minesweeper.bot.BotExecutor;
import minesweeper.bot.TiraBot;

public class TestApp {

    public GameStats gameStats;
    public Board board;
    /* Place your bot here */
    public Bot bot = new TiraBot(32, 60);

    public TestApp(long seed, int width, int height, int mines, int i) {
        //System.out.println("seed:" + seed);
        board = new Board(new MinefieldGenerator(seed), width, height, mines);
        BlockingQueue<Move> moveQueue = new LinkedBlockingQueue<>();
        BotExecutor botExecutor = new BotExecutor(moveQueue, bot, board);
        this.gameStats = new GameStats();
        this.gameStats.startTime = System.nanoTime();
        botExecutor.run();
        while (!moveQueue.isEmpty()) {
            this.gameStats.update(moveQueue.poll());
        }
    }

    public static void main(String[] args) {
        /*
         * !NOTE!
         * Feel free to rewrite/replace this code to fit your needs.
         * !NOTE!
         */
        int height = 32;
        int width = 60;
        int mines = 396;
        long loop = 10000;
        long startTime;
        
        //Values saved as Pairs, this is needed to access the values of both board and gamestats.
        ArrayList<Pair<GameStats, Board>> stats = new ArrayList<>();
        //Play 100 games and save the stats and board to array
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            TestApp app = new TestApp(new Random().nextLong(), height, width, mines, i);
            stats.add(new Pair<GameStats, Board>(app.gameStats, app.board));
        }
        System.out.println("Total time: " +(System.currentTimeMillis()-startTime) );
        //Sets the out stream to file test.txt in root of project.
        try {
            System.setOut(new PrintStream(new File("test.txt")));
        } catch (Exception e) {

        }

        //Print the stats, board and game ending status to file.
        stats.stream().forEach(s -> {
            System.out.println("---------------------");
            System.out.println("Game: " + s.getValue().gameWon);
            System.out.println(s.getValue());
            s.getKey().moves.stream().forEach(k -> System.out.println(k + " at (" + k.x + "," + k.y + ")"));
        });
    }
}
