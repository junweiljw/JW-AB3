package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author junweiljw
/**
 * Parser class to extract keywords from user input
 * Searches the args[] string for keywords and copies them in different strings
 */
public class Processor {
     /*
    String userInput;
     public Processor(String userInput) {
        this.userInput = userInput;
   }
    */
    /**
     * Adds the string tokens in sortedArray into a single string, ignoring null entries in the array
     *
     * @param s the sorted string array to be concatenated
     * @param glue the space to be added in between each array index
     * @return a concatenated string
     */
    private String combine(String[] s, String glue) {
        int k = s.length;
        if (k == 0) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        out.append(s[0].trim());
        for (int i = 1; i < k; i++) {
            if (s[i] != null) {
                out.append(glue).append(s[i].trim());
            }
        }
        return out.toString();
    }
    /**
     * Boolean function that checks if a given String is an integer
     *
     * @param s the string to be checked
     * @return true if String is integer, false if otherwise
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
    /**
     * Sorts the tokens in args[] into the correct sequence and concatenates them into a string
     * @param userInput the line input by user
     * @return the sorted string
     * @throws ParseException
     */
    public String commandParser(String userInput) throws ParseException {
        // splits userInput by whitespaces and saves the tokens in args[]
        String[] args = userInput.split("\\s+", 0);
        String newInput;
        String targetIndex = null;
        String sortedInput;
        String[] sortedArray = new String[10];
        int tagIndex;
        String argument;
        // identifies the first instance of an integer and saves it as the targetIndex (assuming that the first integer
        // is the index)
        for (int i = 0; i < args.length; i++) {
            if (isInteger(args[i])) {
                targetIndex = args[i];
            }
        }
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                // "COMMAND_WORD + PREFIX + DATA" argument pattern
                case AddCommand.COMMAND_WORD:
                case AddCommand.COMMAND_ALIAS:
                    // replaces the COMMAND_WORD in the userInput string with an empty string
                    newInput = userInput.replace(args[i], "");
                    newInput = newInput.trim().replaceAll(" +", " ");
                    // splits the remaining userInput by "/" and saves the tokens in a prefixArguments[]
                    String[] prefixArguments = newInput.split("/", 0);
                    tagIndex = 5;
                    // first index of the array will be command, to be recognised by addressbookParser
                    sortedArray[0] = args[i];
                    for (int j = 0; j < (prefixArguments.length - 1); j++) {
                        // assigns the last character of each token as the prefix, since it was tokenized by "/"
                        String prefix = prefixArguments[j].substring(prefixArguments[j].length() - 1);
                        if (j == (prefixArguments.length - 2)) {
                            argument = prefixArguments[j + 1];
                        } else {
                            // removes the last 2 characters of each string in the prefixArguments array, which is a
                            // whitespace + prefix
                            argument = prefixArguments[j + 1].substring(0, prefixArguments[j + 1].length() - 2);
                        }
                        switch (prefix) {
                            case ("n"):
                                // j+1 because the prefix belongs to the next token, meaning the arguments are in the next index
                                sortedArray[1] = "n/" + argument;
                                break;
                            case ("p"):
                                sortedArray[2] = "p/" + argument;
                                break;
                            case ("e"):
                                sortedArray[3] = "e/" + argument;
                                break;
                            case ("a"):
                                sortedArray[4] = "a/" + argument;
                                break;
                            case ("t"):
                                sortedArray[tagIndex++] = "t/" + argument;
                                break;
                            default:
                        }
                    }
                    // concatenates the strings in sortedArray, ignoring null entries (missing parameters)
                    sortedInput = combine(sortedArray, " ");
                    return sortedInput;
                // "COMMAND_WORD + INDEX + PREFIX + DATA" argument pattern
                case EditCommand.COMMAND_WORD:
                case EditCommand.COMMAND_ALIAS:
                    newInput = userInput.replace(" " + args[i], "");
                    newInput = newInput.trim().replaceAll(" +", " ");
                    prefixArguments = newInput.split("/", 0);
                    tagIndex = 6;
                    sortedArray[0] = args[i];
                    sortedArray[1] = targetIndex;
                    for (int j = 0; j < (prefixArguments.length - 1); j++) {
                        String prefix = prefixArguments[j].substring(prefixArguments[j].length() - 1);
                        if (j == (prefixArguments.length - 2)) {
                            argument = prefixArguments[j + 1];
                        } else {
                            argument = prefixArguments[j + 1].substring(0, prefixArguments[j + 1].length() - 2);
                        }
                        switch (prefix) {
                            case ("n"):
                                sortedArray[2] = "n/" + argument;
                                break;
                            case ("p"):
                                sortedArray[3] = "p/" + argument;
                                break;
                            case ("e"):
                                sortedArray[4] = "e/" + argument;
                                break;
                            case ("a"):
                                sortedArray[5] = "a/" + argument;
                                break;
                            case ("t"):
                                sortedArray[tagIndex++] = "t/" + argument;
                                break;
                            default:
                        }
                    }
                    sortedInput = combine(sortedArray, " ");
                    return sortedInput;
                //fallthrough, similar "COMMAND_WORD + INDEX" argument pattern
                case SelectCommand.COMMAND_WORD:
                case SelectCommand.COMMAND_ALIAS:
                case DeleteCommand.COMMAND_WORD:
                case DeleteCommand.COMMAND_ALIAS:
                    int junkIndex = 2;

                    sortedArray[0] = args[i];
                    sortedArray[1] = targetIndex;
                    for (int j = 0; j < args.length; j++) {
                        if ((j != i) && (args[j] != targetIndex)) {
                            sortedArray[junkIndex++] = args[j];
                        }
                    }
                    sortedInput = combine(sortedArray, " ");
                    return sortedInput;
                case FindCommand.COMMAND_WORD:
                case FindCommand.COMMAND_ALIAS:
                    sortedArray[0] = args[i];
                    int keywordIndex = 1;
                    for (int j = 0; j < args.length; j++) {
                        sortedArray[keywordIndex++] = args[j];
                    }
                    sortedInput = combine(sortedArray, " ");
                    return sortedInput;
                // fallthrough, similar "COMMAND_WORD" argument pattern
                case ClearCommand.COMMAND_WORD:
                case ClearCommand.COMMAND_ALIAS:
                case ListCommand.COMMAND_WORD:
                case ListCommand.COMMAND_ALIAS:
                case HistoryCommand.COMMAND_WORD:
                case HistoryCommand.COMMAND_ALIAS:
                case UndoCommand.COMMAND_WORD:
                case UndoCommand.COMMAND_ALIAS:
                case RedoCommand.COMMAND_WORD:
                case RedoCommand.COMMAND_ALIAS:
                case HelpCommand.COMMAND_WORD:
                case HelpCommand.COMMAND_ALIAS:
                case ExitCommand.COMMAND_WORD:
                case ExitCommand.COMMAND_ALIAS:
                    sortedArray[0] = args[i];
                    sortedInput = combine(sortedArray, " ");
                    return sortedInput;
                default:
            }
        }
        // if this statement is reached, there is no COMMAND_WORD within the userInput string
        return userInput;
    }
}