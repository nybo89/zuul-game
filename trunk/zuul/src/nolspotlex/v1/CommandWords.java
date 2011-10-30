package nolspotlex.v1;

import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game. It is
 * used to recognise commands as they are typed in.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class CommandWords {
    // A mapping between a command word and the CommandWord
    // associated with it.
    private HashMap<String, Command> commands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        commands = new HashMap<String, Command>();
        commands.put("go", new GoCommand());
        commands.put("help", new HelpCommand(this));
        commands.put("quit", new QuitCommand());
        commands.put("take", new TakeCommand());
        commands.put("use", new UseCommand());
        commands.put("beamer", new BeamerCommand());
    }

    /**
     * Given a command word, find and return the matching command object.
     * Return null if there is no command with this name.
     */
    public Command get(String word)
    {
        return (Command)commands.get(word);
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(Iterator i = commands.keySet().iterator(); i.hasNext(); ) {
            System.out.print(i.next() + "  ");
        }
        System.out.println();
    }
}
