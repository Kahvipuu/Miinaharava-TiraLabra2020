/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.bot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import minesweeper.model.Board;
import minesweeper.model.GameStats;
import minesweeper.model.Move;
import minesweeper.model.MoveType;
import minesweeper.model.Highlight;
import minesweeper.model.Pair;
import minesweeper.model.ShadowSquare;
import minesweeper.model.Square;

public class TiraBot implements Bot {

    private ArrayDeque<Move> nextMoves;
    private ArrayDeque<ShadowSquare> nextSquaresToCheck;
    private Move latestMove;
    private Random rng = new Random();
    private ShadowSquare[][] shadowBoard;
    private int width;
    private int height;
    private boolean firstMove;

    public TiraBot(Board board) {
        this.width = board.width;
        this.height = board.height;
        this.nextMoves = new ArrayDeque<>();
        this.latestMove = new Move(0, 0, Highlight.NONE);
        this.shadowBoard = new ShadowSquare[width][height];

        this.initialize();
    }

    @Override
    public Move makeMove(Board board) {

        processLastMove(board);

        if (!nextMoves.isEmpty()) {
            latestMove = nextMoves.pollFirst();
            return latestMove;
        }

        Move randomMove = getRandomMove(board);
        latestMove = randomMove;
        return randomMove;
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Board board) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGameStats(GameStats gameStats) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    public void addMoveToQueue(Move move) {
        this.nextMoves.addLast(move);
    }

    private Move getRandomMove(Board board) {
        HashSet<Square> opened = board.getOpenSquares();
        int x;
        int y;

        while (true) {
            x = rng.nextInt(board.width);
            y = rng.nextInt(board.height);
            if (!opened.contains(board.board[x][y])) {
                return new Move(MoveType.OPEN, x, y);
            }
        }

    }

    private void initialize() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.shadowBoard[x][y] = new ShadowSquare(x, y);
            }
        }
    }

    private void processLastMove(Board board) {
        if (firstMove == true) {
            firstMove = false;
            return;
        }
        int x = latestMove.x;
        int y = latestMove.y;
        MoveType type = latestMove.type;

        int mines = board.getSquareAt(x, y).surroundingMines();
        shadowBoard[x][y].setSurroundingMines(mines);

        if (mines == 0) {
            for (int xx = -1; xx < 2; xx++) {
                for (int yy = -1; yy < 2; yy++) {
                    if (x + xx >= 0 && y + yy >= 0 && xx + yy != 0) {
                        ShadowSquare sq = shadowBoard[x + xx][y + yy];
                        nextSquaresToCheck.add(sq);                        
                    }
                }
            }

        }
        // nämä pitäisi laittaa nextToCheck jonoon... 
        if (type == MoveType.FLAG) {
            for (int xx = -1; xx < 2; xx++) {
                for (int yy = -1; yy < 2; yy++) {
                    if (x + xx >= 0 && y + yy >= 0 && xx + yy != 0) {
                        ShadowSquare sq = shadowBoard[x + xx][y + yy];
                        sq.incrementFlags();
                        if (sq.surroundingFlags() == sq.surroundingMines()) {
                            nextMoves.add(new Move(MoveType.CHORD, sq.getX(), sq.getY()));
                        }
                    }
                }
            }
        }

        if (type == MoveType.OPEN) {
            for (int xx = -1; xx < 2; xx++) {
                for (int yy = -1; yy < 2; yy++) {
                    if (x + xx >= 0 && y + yy >= 0 && xx + yy != 0) {
                        ShadowSquare sq = shadowBoard[x + xx][y + yy];
                        sq.decreaseNotKnown();
                        if (sq.surroundingMines() == sq.surroundingNotKnown()) {
                            setSurroundingToFlags(sq.getX(), sq.getY());
                        }
                    }
                }
            }
        }

    }

    private void setSurroundingToFlags(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
