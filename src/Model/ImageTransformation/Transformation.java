package Model.ImageTransformation;

import Model.ColorScheme.RGBPixel;
import java.util.HashMap;
/**
 * An interface that represents a transformation operation to be applied to an image.
 * Implementations of this interface should define specific transformations that
 * can be applied to RGB pixel data.
 */
public interface Transformation {
  /**
   * Applies the transformation to the image data associated with the specified key.
   *
   * @param key the key used to retrieve the input pixel data from the provided map
   * @param h1 a map containing the RGB pixel data associated with various keys
   * @return a 2D array of RGBPixel representing the transformed image
   */
  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1);

}
