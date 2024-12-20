import controller.ImageController;
import controller.ImageGUIController;
import javax.swing.SwingUtilities;
import model.EnhancedImage;
import view.ImageProcessorGUI;

/**
 * The entry point of the image processing application. This class contains the `main` method which
 * determines the mode of the application based on the provided arguments: - Launches a GUI if no
 * arguments are provided. - Runs a script if the `-file` argument with a file path is provided. -
 * Launches an interactive command-line interface if the `-text` argument is provided. If the
 * arguments are invalid, a message is displayed to guide the user.
 */
public class Main {

  /**
   * The entry point for the application.
   *
   * <p>This method determines the mode of operation based on the command-line arguments provided:
   * <ul>
   *   <li>No arguments: Launches the GUI.</li>
   *   <li><code>-file &lt;file path&gt;</code>: Runs the script specified by the file path.</li>
   *   <li><code>-text</code>: Launches the command-line interactive mode.</li>
   *   <li>Invalid arguments: Displays usage instructions.</li>
   * </ul>
   *
   * @param args the command-line arguments specifying the mode of operation.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      loadGui();
    } else if (args.length == 2 && args[0].equals("-file")) {
      runScript(args[1]);
    } else if (args.length == 1 && args[0].equals("-text")) {
      launchInteractiveMode();
    } else {
      System.out.println(
          "Invalid arguments. Use -file <file path> to run a script or -text to run the command"
              + " line interface.");
    }
  }

  /**
   * Loads and initializes the graphical user interface (GUI) for the image processing application.
   * Creates instances of the necessary models, controllers, and the GUI, and launches the GUI on
   * the Event Dispatch Thread.
   */
  private static void loadGui() {
    EnhancedImage model = new EnhancedImage();
    ImageController imageController = new ImageController(model);
    ImageGUIController guiController = new ImageGUIController(model, imageController);
    SwingUtilities.invokeLater(() -> new ImageProcessorGUI(guiController));
  }

  /**
   * Runs a script located at the specified file path. The script contains commands that are
   * processed by the image controller.
   *
   * @param scriptPath the path to the script file containing commands to be executed
   */
  private static void runScript(String scriptPath) {
    EnhancedImage model = new EnhancedImage();
    ImageController imageController = new ImageController(model);
    imageController.handleScript(new String[]{"run-script", scriptPath});
  }

  /**
   * Launches the interactive command-line mode for the image processing application. The user is
   * presented with a menu of available operations, and the application waits for the user's input
   * to process commands.
   */
  private static void launchInteractiveMode() {
    EnhancedImage model = new EnhancedImage();
    ImageController imageController = new ImageController(model);
    imageController.printMenu();
    imageController.run();
  }
}
