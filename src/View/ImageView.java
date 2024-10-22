package View;

import Controller.ImageController;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class ImageView {

  private ImageController controller;
  private Scanner scanner;
  private final Map<String, Consumer<String[]>> commandMap;

  public Map<String, Consumer<String[]>> getCommandMap() {
    return commandMap;
  }

  public ImageView(ImageController controller) {
    this.controller = controller;
    this.scanner = new Scanner(System.in);
    this.commandMap = new HashMap<>();

    commandMap.put("load",this::handleLoad);
    commandMap.put("save",this::handleSave);
    commandMap.put("brighten",this::handleBrighten);
    commandMap.put("rgb-combine",this::handleCombine);
    commandMap.put("rgb-split",this::handleSplit);
    commandMap.put("blur",this::handleOperation);
    commandMap.put("sharpen",this::handleOperation);
    commandMap.put("horizontal-flip",this::handleOperation);
    commandMap.put("vertical-flip",this::handleOperation);
    commandMap.put("greyscale",this::handleOperation);
    commandMap.put("value-component",this::handleOperation);
    commandMap.put("luma-component",this::handleOperation);
    commandMap.put("intensity-component",this::handleOperation);
    commandMap.put("sepia",this::handleOperation);
    commandMap.put("exit", args -> System.exit(0));
    commandMap.put("run-script", this::handleScript);
  }


  public void run() {
    boolean running = true;
    while (running) {
      try {
        System.out.print("\nEnter command: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
          continue;
        }

        String[] parts = input.split("\\s+");
        String command = parts[0].toLowerCase();

        if (commandMap.containsKey(command)) {
          commandMap.get(command).accept(parts);
          if (command.equals("exit")) {
            running = false;
          }
        } else {
          System.out.println("Unknown command: " + command);
        }

      } catch (Exception e) {
        System.out.println("Error processing command: " + e.getMessage());
      }
    }
  }

  public void printMenu() {
    System.out.println("\nMenu");
    System.out.println("\tLoad Image");
    System.out.println("\tSave Image");
    System.out.println("\tblur");
    System.out.println("\thorizontal-flip");
    System.out.println("\tvertical-flip");
    System.out.println("\tgreyscale");
    System.out.println("\tsepia");
    System.out.println("\tsharpen");
    System.out.println("\trgb-split");
    System.out.println("\trgb-combine");
    System.out.println("\tbrighten");
    System.out.println("\tvalue-component");
    System.out.println("\tluma-component");
    System.out.println("\tintensity-component");
    System.out.println("\trun-script");
    System.out.println("\tExit Program");
  }

  private void handleLoad(String[] args) {
    if (args.length == 3) {
      System.out.println("Loaded Image" + args[2]);
      controller.loadIMage(args[2], args[1]);
    } else {
      System.out.println("Invalid load command. Usage: load <filename> <key>");
    }
  }

  private void handleSave(String[] args) {
    if (args.length == 3) {
      System.out.println("Saved Image" + args[2]);
      controller.saveImage(args[2], args[1]);
    } else {
      System.out.println("Invalid save command. Usage: save <filename> <key> ");
    }
  }

  private void handleOperation(String[] args) {
    if (args.length == 3) {
      System.out.println("Operation on " + args[1]);
      controller.applyOperations(args[0], args[1], args[2]);
    } else {
      System.out.println("Invalid command. Usage: " + args[0] + " <srcKey> <destKey>");
    }
  }

  private void handleBrighten(String[] args) {
    if (args.length == 4) {
      try {
        System.out.println("Brightened Image " + args[2] + " by " + args[1]);
        int factor = Integer.parseInt(args[1]);
        controller.brighten(factor,args[2], args[3] );
      } catch (NumberFormatException e) {
        System.out.println("Invalid brighten factor. Please enter an integer.");
      }
    } else {
      System.out.println("Invalid brighten command. Usage: brighten <factor> <srcKey> <destKey> ");
    }
  }

  private void handleSplit(String[] args) {
    if (args.length == 5) {
      System.out.println("Split Image " + args[2]);
      controller.split(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println("Invalid split command. Usage: split <srcKey> <redKey> <greenKey> <blueKey>");
    }
  }


  private void handleCombine(String[] args) {
    if (args.length == 5) {
      System.out.println("Combined Image " + args[2] + "," + args[3] + " and" + args[4]);
      controller.combine(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println("Invalid combine command. Usage: combine <destKey> <redKey> <greenKey> <blueKey>");
    }
  }

  private void handleScript(String[] args) {
    if (args.length < 2) {
      System.out.println("Please provide a script file path");
      return;
    }
    System.out.println("Loading script from " + args[1]);
    ScriptReader scriptReader = new ScriptReader(this);
    scriptReader.readScript(args[1]);
  }

}
