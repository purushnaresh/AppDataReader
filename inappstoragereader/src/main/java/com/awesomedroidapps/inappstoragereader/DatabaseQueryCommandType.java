package com.awesomedroidapps.inappstoragereader;

/**
 * Created by anshul on 27/03/17.
 */

public enum DatabaseQueryCommandType {
  SELECT("SELECT"),
  UPDATE("UPDATE"),
  DELETE("DELETE"),
  INSERT("INSERT"),
  RAW_QUERY("RAW_QUERY");


  private String command;

  DatabaseQueryCommandType(String command) {
    this.command = command;
  }

  public String getCommand() {
    return command;
  }

  public static DatabaseQueryCommandType getCommand(String command) {
    for (DatabaseQueryCommandType commands : DatabaseQueryCommandType.values()) {
      if (commands.getCommand().equalsIgnoreCase(command)) {
        return commands;
      }
    }
    return RAW_QUERY;
  }
}
