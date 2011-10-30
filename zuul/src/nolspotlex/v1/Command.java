package nolspotlex.v1;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * This class holds information about a command that was issued by the user. A
 * command currently consists of two parts: a CommandWord and a string (for
 * example, if the command was "take map", then the two parts are TAKE and
 * "map").
 * 
 * The way this is used is: Commands are already checked for being valid command
 * words. If the user entered an invalid command (a word that is not known) then
 * the CommandWord is UNKNOWN.
 * 
 * If the command had only one word, then the second word is <null>.
 * 
 * @author Michael Kolling and David J. Barnes 
 * @version 2008.03.30
 */

public abstract class Command {
    private String secondWord;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null. The command word should be null to
     * indicate that this was a command that is not recognised by this game.
     */
    public Command()
    {
        secondWord = null;
    }

    /**
     * Return the second word of this command. If no
     * second word was entered, the result is null.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * Check whether a second word was entered for this 
     * command.
     */
    public boolean hasSecondWord()
    {
        return secondWord != null;
    }

    /**
     * Define the second word of this command (the word
     * entered after the command word). Null indicates that 
     * there was no second word.
     */
    public void setSecondWord(String secondWord)
    {
        this.secondWord = secondWord;
    }

    /**
     * Execute this command. A flag is returned indicating whether
     * the game is over as a result of this command.
     * 
     * @return True, if game should exit; false otherwise.
     */
    public abstract boolean execute(Player player);
}
