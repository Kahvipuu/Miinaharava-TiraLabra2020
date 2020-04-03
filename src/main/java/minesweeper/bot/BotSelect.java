package minesweeper.bot;

import minesweeper.model.Board;

/**
 * This class allows selecting the bot among multiple implementations
 * The bot is selected by changing the static method to return the
 * desired Bot implementation
 */
public class BotSelect {
    /**
     * Returns the currently used Bot implementation
     * @return An object implementing the Bot interface
     * @param width of the real board
     * @param height of the real board
     */
    public static Bot getBot(int width, int height) {
        // CHANGE THIS LINE TO USE YOUR OWN BOT
        return new TiraBot(width, height);
    }
}
