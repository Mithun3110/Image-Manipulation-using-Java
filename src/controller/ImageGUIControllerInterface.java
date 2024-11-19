package controller;

import view.ImageProcessorGUI;

public interface ImageGUIControllerInterface extends ImageControllerInterface{

  void handleLoad(ImageProcessorGUI gui, String key);
  public void handleShowOriginalImage();
  public void handleUndo();
}
