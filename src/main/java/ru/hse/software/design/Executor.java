package ru.hse.software.design;

import ru.hse.software.design.commands.Command;

import java.util.List;

/**
 * Class for calling the main components of the application.
 * Contains paths to directories containing external programs and
 * CLI object as a private fields.
 * Also contains main method 'execute'.
 **/
public class Executor {
    private final Path path;
    private final CLI cli;

    /**
     * Makes cli same as given value, initializes path
     * with the directories listed in the PATH environment variable.
     *
     * @param cli CLI object
     **/
    public Executor(CLI cli) {
        this.path = new Path(System.getenv("PATH").split(":"));
        this.cli = cli;
    }

    /**
     * Takes a user-supplied string as input and calls the required components in the correct order.
     *
     * @param commandString user-supplied string
     * @return Return code
     **/
    public int execute(String commandString) {
        try {
            List<Token> tokens = Lexer.getTokens(commandString);
            List<Token> preProcessedTokens = PreProcessor.preProcess(tokens);
            List<CommandTokens> commandTokens = Parser.preProcess(preProcessedTokens);
            List<Command> commands = CommandBuilder.build(commandTokens, path, cli);
            String prevCommandOutput = "";
            int returnCode = 0;
            for (Command command : commands) {
                returnCode = command.execute(prevCommandOutput);
                if (command.getCommand().equals("exit")) {
                    return returnCode;
                }
                if (returnCode != 0) {
                    return returnCode;
                }
                prevCommandOutput = command.getOutput();
            }
            System.out.println(prevCommandOutput);
            return returnCode;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1;
        }
    }
}