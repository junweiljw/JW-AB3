package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        out.append(s[0]);
        for (int i = 1; i < k; i++) {
            if (s[i] != null)
                out.append(glue).append(s[i]);
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

    public String commandParser(String userInput) throws ParseException {
        String[] args = userInput.split(" ");
        String sortedInput;
        String[] sortedArray = new String[10];

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {

            // "COMMAND_WORD + PREFIX + DATA" argument pattern
            case AddCommand.COMMAND_WORD:
                sortedArray[0] = args[i];
                int tagIndex = 5;

                for (int j = 0; j < args.length; j++) {
                    if (args[j].startsWith("n/")) {
                        sortedArray[1] = args[j];
                    } else if (args[j].startsWith("p/")) {
                        sortedArray[2] = args[j];
                    } else if (args[j].startsWith("e/")) {
                        sortedArray[3] = args[j];
                    } else if (args[j].startsWith("a/")) {
                        sortedArray[4] = args[j];
                    } else if (args[j].startsWith("t/")) {
                        sortedArray[tagIndex++] = args[j];
                    }
                }
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            // "COMMAND_WORD + INDEX + PREFIX + DATA" argument pattern
            case EditCommand.COMMAND_WORD:
                tagIndex = 6;

                for (int j = 0; j < args.length; j++) {
                    if (isInteger(args[j])) {
                        sortedArray[1] = args[j];
                    } else if (args[j].startsWith("n/")) {
                        sortedArray[2] = args[j];
                    } else if (args[j].startsWith("p/")) {
                        sortedArray[3] = args[j];
                    } else if (args[j].startsWith("e/")) {
                        sortedArray[4] = args[j];
                    } else if (args[j].startsWith("a/")) {
                        sortedArray[5] = args[j];
                    } else if (args[j].startsWith("t/")) {
                        sortedArray[tagIndex++] = args[j];
                    }
                }
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            //fallthrough, similar "COMMAND_WORD + INDEX" argument pattern
            case SelectCommand.COMMAND_WORD:
            case DeleteCommand.COMMAND_WORD:
                sortedArray[0] = args[i];
                int intIndex = 1;

                for (int j = 0; j < args.length; j++) {
                    if (isInteger(args[j])) {
                        sortedArray[intIndex++] = args[j];
                    }
                }
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            case FindCommand.COMMAND_WORD:
                sortedArray[0] = args[i];
                int keywordIndex = 1;
                for (int j = 0; j < args.length; j++) {
                    sortedArray[keywordIndex++] = args[j];
                }
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            // fallthrough, similar "COMMAND_WORD" argument pattern
            case ClearCommand.COMMAND_WORD:
            case ListCommand.COMMAND_WORD:
            case HistoryCommand.COMMAND_WORD:
            case UndoCommand.COMMAND_WORD:
            case RedoCommand.COMMAND_WORD:
            case HelpCommand.COMMAND_WORD:
            case ExitCommand.COMMAND_WORD:
                sortedArray[0] = args[i];
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            default:
            }
        }
        throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
    }
}
