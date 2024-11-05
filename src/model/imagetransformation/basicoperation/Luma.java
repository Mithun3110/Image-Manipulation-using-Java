package model.imagetransformation.basicoperation;

/**
 * This class represents an operation that calculates the Luma (brightness) of a pixel based on its
 * RGB color values. It extends the AbstractBasicOperation class.
 */
public class Luma extends AbstractBasicOperation {

  /**
   * Calculates the Luma of a pixel given its red, green, and blue color values. The Luma is
   * computed using the formula: Luma = 0.2126 * R + 0.7152 * G + 0.0722 * B
   *
   * @param r The red color value of the pixel (0-255).
   * @param g The green color value of the pixel (0-255).
   * @param b The blue color value of the pixel (0-255).
   * @return The calculated Luma value, rounded to the nearest integer.
   */
  @Override
  public int properties(int r, int g, int b) {
    int luma = (int) Math.round(0.2126 * r + 0.7152 * g + 0.0722 * b);
    return luma;
  }

}