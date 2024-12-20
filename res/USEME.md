# IMAGE PROCESSING APPLICATION - USER GUIDE

This document outlines all supported commands and their usage in our image processing application.

## RUNNING OF JAR FILE
- `GUI`
  - java -jar Assignment_4_v2.jar
- `Text Based Interactive Mode`
  - java -jar Assignment_4_v2.jar -text
- `Running Script`
  - java -jar Assignment_4_v2.jar -file PNGScriptJar.txt

## TABLE OF CONTENTS
- [GUI Screenshots](#gui-screenshots)
- [Using the GUI](#using-the-gui)
- [Basic commands for interactive text mode](#basic-commands-for-interactive-text-mode)
- [Important Notes](#important-notes)
- [Example Workflow](#example-workflow)

## GUI Screenshots

1. On clicking load button on the GUI an image will be loaded along with its histogram.
   ![Landing Page](GUI_Landing_Page.png)

2. By checking the checkbox user can preview the operation that will be performed on the image. 
   ![Split Preview](GUI_Split_Preview.png)

##   USING THE GUI

**1. Loading an Image:**
   -  Click the Load Image button on the left panel.
   - A file chooser dialog will open. Navigate to the image file you want to load (supported formats include PNG, JPG, and PPM).
   - Select the file and click Open. The image will be displayed in the main viewing area along with its histogram.

**2. Editing the Image:**
   - The GUI provides various editing options. Here are some of the key operations:
     - `**Brighten:**` Adjust the brightness of the image by clicking the Brighten button and entering a brightness factor when prompted.
     - `**Flip:**` Flip the image horizontally or vertically by clicking the Horizontal Flip or Vertical Flip buttons.
     - `**Color Adjustments:**` Apply color adjustments by selecting the respective buttons (e.g., Greyscale, Sepia, Red Component, etc.).
     - `**Blur/Sharpen:**` Use the Blur or Sharpen buttons to apply those effects. You can preview the effect before applying it by checking the Preview checkbox.
   - `Levels Adjustment:`
     - To adjust the tonal range of the image, click the Levels Adjust button. Enter the black, mid, and white level values when prompted.
     - You can also opt for a split preview by selecting the Preview checkbox to see the adjustments before applying.
   - `Undoing Changes:`
     - If you want to revert the last operation, click the Undo button. This will restore the previous state of the image.
   - `Reverting to Original Image:`
     - To revert to the original image loaded, click the Revert to Original Image button.
   - `Exiting the Application:`
     - To close the application, click the Exit button. You may be prompted to save any unsaved changes before exiting.
   - `Previewing Changes:`
     - Many operations allow you to preview changes before applying them. Check the Preview checkbox for operations like Blur, Sharpen, Sepia, and Greyscale to see how they will affect the image.
   - `Viewing the Histogram:`
     - The histogram of the currently displayed image can be viewed on the right side of the GUI. It updates automatically as you apply different operations.
     
**3. Saving the Image:**
   - After making changes, click the Save Image button.
   - A file chooser dialog will open. Select a location and specify a filename for the output image.
    - Choose the desired file format (PNG, JPG, PPM) from the provided options and click Save.



## BASIC COMMANDS FOR INTERACTIVE TEXT MODE

1. `load <image-path> <image-name>`
   - Loads an image from the specified path and assigns it the given name
   - Example: `load images/beach.jpg beach1`

2. `save <image-path> <image-name>`
   - Saves the image with the given name to the specified path
   - Example: `save processed/beach-bright.png beach1-bright`

## IMAGE OPERATIONS

1. `brighten <increment> <image-name> <dest-image-name>`
   - Brightens/darkens image by the specified increment
   - Example: `brighten 50 beach1 beach1-bright`
   - Note: Negative values will darken the image

2. `horizontal-flip <image-name> <dest-image-name>`
   - Flips image horizontally
   - Example: `horizontal-flip beach1 beach1-horizontal`

3. `vertical-flip <image-name> <dest-image-name>`
   - Flips image vertically
   - Example: `vertical-flip beach1 beach1-vertical`

## COLOR COMPONENTS

1. `red-component <image-name> <dest-image-name>`
   - Extracts red component
   - Example: `red-component beach1 beach1-red`

2. `green-component <image-name> <dest-image-name>`
   - Extracts green component
   - Example: `green-component beach1 beach1-green`

3. `blue-component <image-name> <dest-image-name>`
   - Extracts blue component
   - Example: `blue-component beach1 beach1-blue`

4. `value-component <image-name> <dest-image-name>`
   - Creates grayscale using maximum RGB value
   - Example: `value-component beach1 beach1-value`

5. `luma-component <image-name> <dest-image-name>`
   - Creates grayscale using weighted RGB values
   - Example: `luma-component beach1 beach1-luma`

6. `intensity-component <image-name> <dest-image-name>`
   - Creates grayscale using average RGB values
   - Example: `intensity-component beach1 beach1-intensity`

## ADVANCED OPERATIONS

1. `blur <image-name> <dest-image-name>`
   - Applies Gaussian blur
   - Example: `blur beach1 beach1-blur`

2. `sharpen <image-name> <dest-image-name>`
   - Sharpens the image
   - Example: `sharpen beach1 beach1-sharp`

3. `greyscale <image-name> <dest-image-name>`
   - Converts to grayscale using luma
   - Example: `greyscale beach1 beach1-grey`

4. `sepia <image-name> <dest-image-name>`
   - Applies sepia tone
   - Example: `sepia beach1 beach1-sepia`

## RGB OPERATIONS

1. `rgb-split <image-name> <red-name> <green-name> <blue-name>`
   - Splits image into RGB components
   - Example: `rgb-split beach1 beach1-r beach1-g beach1-b`

2. `rgb-combine <dest-image-name> <red-name> <green-name> <blue-name>`
   - Combines RGB components into one image
   - Example: `rgb-combine beach1-combined beach1-r beach1-g beach1-b`

## NEW FEATURES

1. `compress <percentage> <image-name> <dest-image-name>`
   - Compresses image by specified percentage (0-100)
   - Example: `compress 50 beach1 beach1-compressed`

2. `histogram <image-name> <dest-image-name>`
   - Generates histogram visualization
   - Example: `histogram beach1 beach1-histogram`

3. `color-correction <image-name> <dest-image-name>`
   - Performs color correction
   - Example: `color-correction beach1 beach1-corrected`

4. `levels-adjust <black> <mid> <white> <image-name> <dest-image-name>`
   - Adjusts color levels (values between 0-255)
   - Example: `levels-adjust 20 128 255 beach1 beach1-adjusted`

## SPLIT PREVIEW

Any operation can be split-previewed using the split keyword:
`<operation> <image-name> <dest-image-name> split <percentage>`
Example: `blur beach1 beach1-split-blur split 50`

## SCRIPTING

1. `run-script <script-path>`
   - Runs commands from a script file
   - Example: `run-script scripts/process-beach.txt`

2. `exit`
   - Exits the application

## SUPPORTED FILE FORMATS

- PNG (.png)
- JPG/JPEG (.jpg, .jpeg)
- PPM (P3 format) (.ppm)

## IMPORTANT NOTES

1. Image must be loaded before applying any operations
2. All operations create new images; original remains unchanged
3. File paths can be relative or absolute
4. Invalid commands or parameters will generate error messages
5. For levels-adjust: black < mid < white, all values between 0-255
6. Compression percentage must be between 0 and 100
7. Split preview percentage must be between 0 and 100

## EXAMPLE WORKFLOW

```
load images/beach.jpg beach1
brighten 30 beach1 beach1-bright
blur beach1-bright beach1-blur
sepia beach1-blur beach1-final
save processed/beach-final.jpg beach1-final
```