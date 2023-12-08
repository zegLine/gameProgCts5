package test_chatgame;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    public static String commandError = "";

    private static Map<String, CommandFunction> commandMap = new HashMap<>();

    public static boolean validateCommand(String unsafeCommand) {
        if (unsafeCommand.isEmpty()) return false;

        return true;
    }

    public static boolean executeCommand(String safeCommand) {
        // Separate by spaces
        String[] tokens = safeCommand.split(" ");

        // Check if there are at least 2 tokens (command and parameters)
        if (tokens.length < 2) {
            commandError = "cmd not complete";
            System.out.println("Invalid command format.");
            return false;
        }

        // The first word is the command
        String commandName = tokens[0];

        // Check if the command exists in the map
        if (commandMap.containsKey(commandName)) {
            // Get the associated function
            CommandFunction commandFunction = commandMap.get(commandName);

            // Extract parameters (excluding the command itself)
            String[] parameters = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, parameters, 0, tokens.length - 1);

            // Execute the function
            return commandFunction.execute(parameters);
        } else {
            commandError = "cmd unknown";
            System.out.println("Unknown command: " + commandName);
            return false;
        }
    }

    // Define a functional interface for command functions
    @FunctionalInterface
    public interface CommandFunction {
        boolean execute(String[] parameters);
    }

    public static void initCommand(String name, CommandFunction f) {
        commandMap.put(name, f);
    }

}
