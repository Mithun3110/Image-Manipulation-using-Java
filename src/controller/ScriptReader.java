package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The ScriptReader class is responsible for reading commands from a script file and executing them
 * using the associated ImageController instance. The script file contains commands that are mapped
 * to specific image processing operations, which are executed in sequence.
 */
public class ScriptReader {

  private final Map<String, Consumer<String[]>> commandMap;

  /**
   * Constructs a new ScriptReader that will execute commands using the provided ImageController.
   *
   * @param imageController the ImageController instance used to execute commands from the script
   */
  public ScriptReader(ImageController imageController) {
    this.commandMap = imageController.getCommandMap();
  }

  /**
   * Reads and executes commands from the specified script file. Each line in the script is treated
   * as a separate command. Lines that are empty or start with '#' are ignored. If a command is
   * unrecognized, an error message is printed to the console.
   *
   * @param scriptPath the path to the script file to be read
   * @throws IOException       if an I/O error occurs reading from the file, or if the file is
   *                           malformed or contains unmappable byte sequences
   * @throws SecurityException if access to the script file is denied
   */
  public void readScript(String scriptPath) throws IOException {
    if (!Files.exists(Paths.get(scriptPath))) {
      throw new IOException("Script file does not exist: " + scriptPath);
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(scriptPath))) {
      String line;

      while ((line = reader.readLine()) != null) {
        line = line.trim();

        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        String[] parts = line.split("\\s+");
        String command = parts[0].toLowerCase();

        if (commandMap.containsKey(command)) {
          commandMap.get(command).accept(parts);
        } else {
          System.out.println("Unknown command in Script: " + command);
        }
      }
    } catch (SecurityException e) {
      throw new IOException("Access to script file denied: " + scriptPath, e);
    }
  }
}