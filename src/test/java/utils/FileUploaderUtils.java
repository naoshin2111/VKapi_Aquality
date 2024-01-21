package utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

@Slf4j
@UtilityClass
public class FileUploaderUtils {

    private static final int ROBOT_DELAY = 1000;

    public static void uploadImage(String imagePath) {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(ROBOT_DELAY);

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(new File(imagePath).getAbsolutePath());
            clipboard.setContents(stringSelection, stringSelection);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            log.error("Error From: FileUploaderUtils - Robot Class did not follow command", e);
            throw new RuntimeException();
        }
    }
}
