package Model;

import java.util.HashMap;

public class Split extends AbstractBasicOperation {


  public HashMap<String, RGBPixel[][]> apply(HashMap<String, RGBPixel[][]> h1,RGBPixel[][] originalPixels,String key) {

    int height = originalPixels.length;
    int width = originalPixels[0].length;

    // Create three 2D arrays for the Red, Green, and Blue channels
    RGBPixel[][] redChannel = new RGBPixel[height][width];
    RGBPixel[][] greenChannel = new RGBPixel[height][width];
    RGBPixel[][] blueChannel = new RGBPixel[height][width];

    // HashMap to store the channels with "Red", "Green", "Blue" as keys
    HashMap<String, RGBPixel[][]> channelsMap = new HashMap<>();

    // Iterate over the original pixels to split them into separate color channels
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!(originalPixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Input image must contain RGBPixels");
        }

        RGBPixel currentPixel = originalPixels[i][j];

        // Set the individual channel values in each channel's array
        redChannel[i][j] = new RGBPixel(currentPixel.getRed(), 0, 0);
        greenChannel[i][j] = new RGBPixel(0, currentPixel.getGreen(), 0);
        blueChannel[i][j] = new RGBPixel(0, 0, currentPixel.getBlue());
      }
    }

    // Store the channel arrays in the HashMap
    channelsMap.put(key+"-Red", redChannel);
    channelsMap.put(key+"-Green", greenChannel);
    channelsMap.put(key+"-Blue", blueChannel);

    return channelsMap;
  }
}
