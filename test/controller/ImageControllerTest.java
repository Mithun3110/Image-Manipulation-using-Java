package controller;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import model.ImageModel;
import model.colorscheme.Pixels;
import model.imagetransformation.basicoperation.Flip.Direction;
import org.junit.Before;
import org.junit.Test;

public class ImageControllerTest {

  private StringBuilder output;
  private Readable in;
  private ImageController imageController;
  private ImageModel mockImage;

  @Before
  public void setUp() {
    output = new StringBuilder();
    mockImage = new MockImage(output);
    in = new StringReader("");
    imageController = new ImageController(mockImage, in, output);
  }

  // Mock image class implementing ImageModel
  private static class MockImage implements ImageModel {
    private final StringBuilder output;

    @Override
    public void greyScale(String key, String saveKey) {
      output.append("greyScale with key: ").append(key).append("\n");

    }

    @Override
    public void sepia(String key, String saveKey) {
      output.append("sepia with key: ").append(key).append("\n");

    }

    @Override
    public void sharpen(String key, String saveKey) {
      output.append("sharpen with key: ").append(key).append("\n");

    }

    @Override
    public void luma(String key, String saveKey) {
      output.append("luma with key: ").append(key).append("\n");

    }

    @Override
    public void value(String key, String saveKey) {
      output.append("Value with key: ").append(key).append("\n");

    }

    @Override
    public void intensity(String key, String saveKey) {

      output.append("Intensity with key: ").append(key).append("\n");
    }

    @Override
    public void compress(String key, String saveKey, double compressionRatio) {

      output.append("Applying compression to test1 with ratio ").append(compressionRatio).append("\n");

    }

    @Override
    public void colorCorrection(String key, String saveKey) {

      output.append("Color Corrected with key: ").append(key).append("\n");

    }

    @Override
    public void adjustLevel(int black, int mid, int white, String key, String saveKey) {
      output.append("Level adjusted with key: ").append(key).append("\n");

    }

    @Override
    public void histogram(String key, String saveKey) {

      output.append("Histogram created: ").append(key).append("\n");

    }

    @Override
    public void splitAndTransform(String key, String saveKey, int splitValue, String operation,
        int... params) {

      output.append("Split and transformed with key: ").append(key).append("\n");

    }

    MockImage(StringBuilder output) {
      this.output = output;
    }

    @Override
    public void storePixels(String key, Pixels[][] pixels) {
      output.append("Stored pixels with key: ").append(key).append("\n");
    }

    @Override
    public Pixels[][] getStoredPixels(String key) {
      output.append("Retrieved pixels with key: ").append(key).append("\n");
      return new Pixels[0][0];
    }

    @Override
    public void getRedChannel(String key, String saveKey) {
      output.append("Red component extracted from ").append(key).append(" saved as ").append(saveKey).append("\n");
    }

    @Override
    public void getGreenChannel(String key, String saveKey) {
      output.append("Green component extracted from ").append(key).append(" saved as ").append(saveKey).append("\n");
    }

    @Override
    public void getBlueChannel(String key, String saveKey) {
      output.append("Blue component extracted from ").append(key).append(" saved as ").append(saveKey).append("\n");
    }

    @Override
    public void blur(String key, String saveKey) {
      output.append("Blur applied to ").append(key).append(" saved as ").append(saveKey).append("\n");
    }

    @Override
    public void brighten(int brightenFactor, String key, String saveKey) {
      output.append("Brightened ").append(key).append(" by ").append(brightenFactor)
          .append(" saved as ").append(saveKey).append("\n");
    }

    @Override
    public void split(String key, String saveKey1, String saveKey2, String saveKey3) {
      output.append("Split ").append(key).append(" into ")
          .append(saveKey1).append(", ").append(saveKey2).append(" and ").append(saveKey3).append("\n");
    }

    @Override
    public void combine(String key, String key1, String key2, String key3) {
      output.append("Combined ").append(key1).append(", ").append(key2).append(", ").append(key3)
          .append(" saved as ").append(key).append("\n");
    }

    @Override
    public void flip(String key, String saveKey, Direction d) {
      output.append("Flipped ").append(key).append(" ").append(d).append(" saved as ").append(saveKey).append("\n");
    }

  }

  @Test
  public void testLoad() {
    String input = "load test/Test_Image/Landscape.png test1\nexit";
    runControllerWithInput(input);
    assertEquals("Stored pixels with key: test1\n", output.toString());
  }

  @Test
  public void testBlur() {
    String input = "blur test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("Blur applied to test1 saved as test2\n", output.toString());
  }

  @Test
  public void testBrighten() {
    String input = "brighten 10 test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("Brightened test1 by 10 saved as test2\n", output.toString());
  }

  @Test
  public void testSplit() {
    String input = "rgb-split test1 red green blue\nexit";
    runControllerWithInput(input);
    assertEquals("Split test1 into red, green and blue\n", output.toString());
  }

  private void runControllerWithInput(String input) {
    output.setLength(0); // Clear the output
    in = new StringReader(input);
    imageController = new ImageController(mockImage, in, output);
    imageController.run();
  }

  @Test
  public void testGreyScale() {
    String input = "greyscale test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("greyScale with key: test1\n", output.toString());
  }

  @Test
  public void testSepia() {
    String input = "sepia test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("sepia with key: test1\n", output.toString());
  }

  @Test
  public void testSharpen() {
    String input = "sharpen test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("sharpen with key: test1\n", output.toString());
  }

  @Test
  public void testLuma() {
    String input = "luma-component test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("luma with key: test1\n", output.toString());
  }

  @Test
  public void testValue() {
    String input = "value-component test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("Value with key: test1\n", output.toString());
  }

  @Test
  public void testIntensity() {
    String input = "intensity-component test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("Intensity with key: test1\n", output.toString());
  }

  @Test
  public void testColorCorrection() {
    String input = "color-correction test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("Color Corrected with key: test1\n", output.toString());
  }

  @Test
  public void testAdjustLevel() {
    String input = "levels-adjust 0 50 100 test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("Level adjusted with key: test1\n", output.toString());
  }

  @Test
  public void testHistogram() {
    String input = "histogram test1 test2\nexit";
    runControllerWithInput(input);
    assertEquals("Histogram created: test1\n", output.toString());
  }

  @Test
  public void RGBSplit() {
    String input = "rgb-split test1 red green blue \nexit";
    runControllerWithInput(input);
    assertEquals("Split test1 into red, green and blue\n", output.toString());
  }

  @Test
  public void testStorePixels() {
    String input = "load test/Test_Image/Landscape.png i1\nexit";
    runControllerWithInput(input);
    assertEquals("Stored pixels with key: i1\n", output.toString());
  }

  @Test
  public void testCompression() {
    String input = "compress 10 test1 test2 \nexit";
    runControllerWithInput(input);
    assertEquals("Applying compression to test1 with ratio 10.0\n", output.toString());
  }

  @Test
  public void testFlip() {
    String input = "horizontal-flip test1 test2\nexit"; // assuming "horizontal" is a valid direction
    runControllerWithInput(input);
    assertEquals("Flipped test1 HORIZONTAL saved as test2\n", output.toString());
  }



}