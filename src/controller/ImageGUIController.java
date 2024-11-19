package controller;

import model.EnhancedImage;
import model.EnhancedImageModel;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.basicoperation.Flip.Direction;
import view.ImageProcessorGUI;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.function.Consumer;

public class ImageGUIController extends ImageController implements ImageGUIControllerInterface {
  String latest;
  private final ImageController imageController;
  ImageProcessorGUI gui;
  EnhancedImageModel i1;
  String latesthistogram;
  String original;

  public ImageGUIController(EnhancedImageModel image, ImageController imageController) {
    super(image);
    this.imageController = imageController;
    i1 = new EnhancedImage();
  }

  // New method to handle image preview
  public void previewOperation(String operation) {
    try {
      // Get the preview image based on the operation
      BufferedImage previewImage = imageController.getPreviewImage(operation);
      if (previewImage != null) {
        gui.displayImage(previewImage); // Display the preview image in the GUI
      } else {
        showError("Error generating preview.");
      }
    } catch (IllegalArgumentException ex) {
      showError("Error generating preview: " + ex.getMessage());
    } catch (Exception ex) {
      showError("An unexpected error occurred while generating preview: " + ex.getMessage());
    }
  }

  @Override
  public void handleLoad(ImageProcessorGUI gui, String key) {
    this.gui = gui;
    original = key;
    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(null);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filename = selectedFile.getAbsolutePath();

      int lastDotIndex = key.lastIndexOf('.');
      if (lastDotIndex > 0) {
        key = key.substring(0, lastDotIndex); // Remove the file extension
      }
      latest = key;

      try {
        imageController.handleLoad(new String[]{"load", filename, key});
        i1.storePixels(key, ImageUtil.loadImage(filename));
        Pixels[][] pixels = i1.getStoredPixels(key);
        BufferedImage image = convertPixelsToBufferedImage(pixels);
        gui.displayImage(image);
      } catch (IllegalArgumentException ex) {
        showError("Error loading image: " + ex.getMessage());
      } catch (Exception ex) {
        showError("An unexpected error occurred: " + ex.getMessage());
      }
    }
  }

  private BufferedImage convertPixelsToBufferedImage(Pixels[][] pixels) {
    if (pixels == null || pixels.length == 0) {
      throw new IllegalArgumentException("No pixels to convert.");
    }

    int height = pixels.length;
    int width = pixels[0].length;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (pixels[y][x] instanceof RGBPixel) {
          RGBPixel rgbPixel = (RGBPixel) pixels[y][x];
          int rgb = (rgbPixel.getRed() << 16) | (rgbPixel.getGreen() << 8) | rgbPixel.getBlue();
          image.setRGB(x, y, rgb);
        }
      }
    }

    return image;
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public String getLatestKey() {
    return imageController.getLatestKey();
  }

  @Override
  public void applyOperation(String[] args) {
    String operation = args[0];
    String key = latest;
    String dest = key + "_" + operation;
    String[] command = {operation, key, dest};
    imageController.applyOperation(command);

    // Preview the image after applying the operation
    previewOperation(operation);  // This will display the preview after operation

    latest = dest;
    displayImageByKey(gui, dest);
  }

  public void applyHistogram(String[] args) {
    String key = latest;
    String dest = latest + "_histogram";
    String[] command = {"histogram", key, dest};
    imageController.applyOperation(command);
    Pixels[][] pixels = imageModel.getStoredPixels(dest);
    BufferedImage image = convertPixelsToBufferedImage(pixels);
    gui.displayHistogram(image);
  }

  public void displayImageByKey(ImageProcessorGUI gui, String key) {
    try {
      Pixels[][] pixels = imageModel.getStoredPixels(key);
      if (pixels == null) {
        throw new IllegalArgumentException("No image found with key: " + key);
      }
      BufferedImage image = convertPixelsToBufferedImage(pixels);
      gui.displayImage(image);
    } catch (IllegalArgumentException ex) {
      showError("Error displaying image: " + ex.getMessage());
    } catch (Exception ex) {
      showError("An unexpected error occurred: " + ex.getMessage());
    }
  }

  @Override
  public Map<String, Consumer<String[]>> getCommandMap() {
    return Map.of();
  }

  @Override
  public void run() {}

  @Override
  public void handleLoad(String[] args) { }

  @Override
  public void handleSave(String[] args) {
    // Check if the latest key is not null or empty
    if (latest == null || latest.isEmpty()) {
      showError("No image loaded to save. Please load an image first.");
      return;
    }

    // Create a JFileChooser to let the user choose a directory and file name
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image");

    // Add file filters for different image formats
    javax.swing.filechooser.FileFilter pngFilter = new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png");
    javax.swing.filechooser.FileFilter jpgFilter = new javax.swing.filechooser.FileNameExtensionFilter("JPG Images", "jpg");
    javax.swing.filechooser.FileFilter ppmFilter = new javax.swing.filechooser.FileNameExtensionFilter("PPM Images", "ppm");

    // Add the file filters to the file chooser
    fileChooser.addChoosableFileFilter(pngFilter);
    fileChooser.addChoosableFileFilter(jpgFilter);
    fileChooser.addChoosableFileFilter(ppmFilter);

    // Set the default filter (optional, you can choose one to start with)
    fileChooser.setFileFilter(pngFilter); // Default filter, for example, PNG

    // Open the file chooser dialog to select the file to save
    int userSelection = fileChooser.showSaveDialog(null);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      // User selected a file
      File fileToSave = fileChooser.getSelectedFile();

      // Get the selected file filter
      javax.swing.filechooser.FileFilter selectedFilter = fileChooser.getFileFilter();

      // Determine the file extension based on the selected filter
      String extension = "";
      if (selectedFilter == pngFilter) {
        extension = "png";
      } else if (selectedFilter == jpgFilter) {
        extension = "jpg";
      } else if (selectedFilter == ppmFilter) {
        extension = "ppm";
      }

      // If the file does not already have the correct extension, append it
      String fileName = fileToSave.getName();
      if (!fileName.toLowerCase().endsWith("." + extension)) {
        fileToSave = new File(fileToSave.getAbsolutePath() + "." + extension);
      }

      // Create the command array to call the controller's handleSave method
      String[] command = {"save", fileToSave.getAbsolutePath(), latest};

      try {
        // Call the controller's handleSave method to save the image
        imageController.handleSave(command);
        // Optionally, display the saved image (if needed)
        displayImageByKey(gui, latest);

        // Show a success message
        JOptionPane.showMessageDialog(null, "Image saved successfully to " + fileToSave.getAbsolutePath(),
            "Save Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception e) {
        showError("An error occurred while saving the image: " + e.getMessage());
      }
    } else {
      // The user canceled the file save operation
      showError("Save operation was canceled.");
    }
  }

  // Handle undo, and other methods...
  @Override
  public void handleUndo(){
    // Logic for undo
  }

  @Override
  public void printMenu() {}

  @Override
  public void handleShowOriginalImage() {
    String key = original;
    String dest = key;
    latest = key;
    i1.storePixels(dest,i1.getStoredPixels(key));
    BufferedImage originalImage = convertPixelsToBufferedImage(i1.getStoredPixels(dest)); // Fetch the original image from the controller
    if (originalImage != null) {
      gui.displayImage(originalImage); // Display the original image
      latest = dest;
    } else {
      showError("No original image available.");
    }
  }

}
