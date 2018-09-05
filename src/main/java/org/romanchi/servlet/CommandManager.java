/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.servlet;

import java.util.HashMap;
import java.util.Map;

import org.romanchi.commands.*;

/**
 *
 * @author Roman
 */
public class CommandManager {
    private Map<String, Command> commands = new HashMap<>(); 
    
    private CommandManager(){
        commands.put("profile", new ProfileCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("signup", new SignupCommand());
        commands.put("team", new TeamCommand());
        commands.put("delete", new DeleteProfileCommand());
        commands.put("teams",new TeamsCommand());
        commands.put("createteam", new CreateTeamCommand());
        commands.put("deleteteam", new DeleteTeamCommand());
        commands.put("startgame", new StartGameCommand());
        commands.put("endgame", new EndGameCommand());
        commands.put("question", new QuestionCommand());
        commands.put("checkanswer", new CheckAnswerCommand());
        commands.put("winner", new WinnerCommand());
        commands.put("hint", new HintCommand());
    }
    private static class ControllerManagerHolder{
        private static final CommandManager commandManager = new CommandManager();
    }
    public static CommandManager getInstance(){
        return ControllerManagerHolder.commandManager;
    }
    public Command getCommand(String commandName){
        Command command = commands.get(commandName);
        return (command == null) ? new NoCommand(): command;
    }
}
