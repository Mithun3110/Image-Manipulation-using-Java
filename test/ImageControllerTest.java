import Model.Image;
import Controller.ImageController;
import Model.RGBPixel;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class ImageControllerTest {

  private Image image;
  private ImageController controller;

  @Before
  public void setUp() {
    image = new Image();
    controller = new ImageController(image);
  }

  @Test
  public void testLoadImage() {

      controller.loadIMage("test/Test_Image/Landscape.png", "testKey");
      assertNotNull("Image should be loaded and present in the map", image.h1.get("testKey"));
      RGBPixel[][] loadedPixels = image.getPixels("testKey","test/Test_Image/Landscape.png");
      assertNotNull("Loaded pixels should not be null", loadedPixels);
      assertArrayEquals("Pixel data should match the expected pixels", image.h1.get("testKey"), loadedPixels);
    }

  @Test
  public void testBlur() {
    // Load and blur the image
    controller.loadIMage("test/Test_Image/Landscape.png","testKey" );
    controller.applyOperations("blur", "testKey", "blurredKey");

    // Verify the blurred image with the expected output
    RGBPixel[][] blurredPixels = image.h1.get("blurredKey");
    RGBPixel[][] expectedPixels = image.getPixels("expectedBlurredKey", "test/Test_Image/png_op/landscape-blur.png");

    assertArrayEquals(expectedPixels, blurredPixels);
  }

  @Test
  public void testHorizontalFlip() {
    // Load and apply horizontal flip
    controller.loadIMage("resources/images/testImage.png", "testKey");
    controller.applyOperations("horizontal-flip", "testKey", "flippedKey");
    controller.saveImage("horizontal-flip", "testKey", "flippedKey");

    // Verify the flipped image with the expected output
    RGBPixel[][] flippedPixels = image.h1.get("flippedKey");
    RGBPixel[][] expectedPixels = image.getPixels("expectedFlippedKey", "resources/images/expectedFlippedImage.png");

    assertArrayEquals(expectedPixels, flippedPixels);
  }

  @Test
  public void testBrighten() {
    // Load and brighten the image
    controller.loadIMage("resources/images/testImage.png", "testKey");
    controller.brighten(20, "testKey", "brightenedKey");

    // Verify the brightened image with the expected output
    RGBPixel[][] brightenedPixels = image.h1.get("brightenedKey");
    RGBPixel[][] expectedPixels = image.getPixels("expectedBrightenedKey", "resources/images/expectedBrightenedImage.png");

    assertArrayEquals(expectedPixels, brightenedPixels);
  }

  @Test
  public void testGreyscale() {
    // Load and apply greyscale
    controller.loadIMage("resources/images/testImage.png", "testKey");
    controller.applyOperations("greyscale", "testKey", "greyscaleKey");

    // Verify the greyscale image with the expected output
    RGBPixel[][] greyscalePixels = image.h1.get("greyscaleKey");
    RGBPixel[][] expectedPixels = image.getPixels("expectedGreyscaleKey", "resources/images/expectedGreyscaleImage.png");

    assertArrayEquals(expectedPixels, greyscalePixels);
  }
}
