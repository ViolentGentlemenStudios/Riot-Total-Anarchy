package com.violentgentlemenstudios.riot;

public class SceneCommand {
    private String command = "";
    private Object[] arguments = new Object[0];
            
    public SceneCommand(String command, Object... arguments) {
        this.command = command;
        this.arguments = arguments;
    }
    
    public String getCommand() {
        return command;
    }
    
    public Object[] getArguments() {
        return arguments;
    }
}
