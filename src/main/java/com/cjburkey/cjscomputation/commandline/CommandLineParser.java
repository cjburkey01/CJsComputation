package com.cjburkey.cjscomputation.commandline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.process.ProcessCommandLineCall;
import com.cjburkey.cjscomputation.process.ProcessHost;

public class CommandLineParser {
    
    private static final Map<String, CommandLineCall> commandLineCalls = new HashMap<>();
    
    public static void execute(ProcessHost host, String input) {
        ParsedResults[] parsedResults = tokenize(input);
        String[] tokens = new String[parsedResults.length];
        for (int i = 0; i < parsedResults.length; i ++) {
            if (!parsedResults[i].success) {
                // TODO: PARSE FAILURE
                Debug.log("Parse failed on token \"{}\"", parsedResults[i].token);
                return;
            }
            tokens[i] = parsedResults[i].token;
        }
        if (tokens.length < 1) {
            return;     // Return successfully if nothing happened because it probably happened successfully (nothing can't go wrong, after all)
        }
        if (!getCommandExists(tokens[0])) {
            // TODO: COMMAND NOT FOUND
            Debug.log("Command not found \"{}\"", tokens[0]);
            return;
        }
        CommandLineCall commandLineCall = getCommand(tokens[0]);
        if ((tokens.length - 1) < commandLineCall.minArguments || ((tokens.length - 1) > commandLineCall.arguments.length && commandLineCall.limitArgs)) {
            // TODO: INVALID ARGUMENTS
            Debug.log("Invalid args");
            return;
        }
        String[] arguments = (tokens.length > 1) ? Arrays.copyOfRange(tokens, 1, tokens.length) : new String[0];
        host.appendProcess(new ProcessCommandLineCall(commandLineCall, arguments));
    }
    
    public static boolean addCommand(CommandLineCall commandLineCall) {
        if (commandLineCalls.containsKey(commandLineCall.getName())) {
            return false;
        }
        commandLineCalls.put(commandLineCall.getName(), commandLineCall);
        return true;
    }
    
    public static CommandLineCall getCommand(String name) {
        if (getCommandExists(name)) {
            return commandLineCalls.get(name);
        }
        return null;
    }
    
    public static boolean getCommandExists(String name) {
        return commandLineCalls.containsKey(name);
    }
    
    private static ParsedResults[] tokenize(String input) {
        if (input == null || (input = input.trim()).isEmpty()) {
            return new ParsedResults[0];
        }
        List<ParsedResults> tokens = new ArrayList<>();
        String[] pureTokens = input.split("\\s");
        for (String pureToken : pureTokens) {
            if (pureToken == null || (pureToken = pureToken.trim()).isEmpty()) {
                continue;
            }
            if (!pureToken.matches("[A-Za-z0-9_]+")) {  // Require a 1+ character sequence of alpha-numeric-underscore characters
                tokens.add(new ParsedResults(false, pureToken));   // Fail to parse
                break;  // Break because error was already found
            }
            tokens.add(new ParsedResults(true, pureToken));
        }
        return tokens.toArray(new ParsedResults[0]);
    }
    
    public static final class ParsedResults {
        
        public final boolean success;
        public final String token;
        
        public ParsedResults(boolean success, String token) {
            this.success = success;
            this.token = token;
        }
        
    }
    
}