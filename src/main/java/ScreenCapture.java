import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ScreenCapture {

    public void captureScreen() {
        BufferedImage resizedImage;
        BufferedImage sharpenedImage;
        try {
            Robot robot = new Robot();
            String format = "jpg";
            String fileName = getCurrentTime() + "." + format;

            Rectangle captureRect = new Rectangle(0, 70, 350, 255);
            BufferedImage screenFullImage = robot.createScreenCapture(captureRect);

            resizedImage = resizeImage(screenFullImage);
            sharpenedImage = sharpenImage(resizedImage);

            ImageIO.write(sharpenedImage, format, new File("./src/main/java/Screenshots", fileName));

            System.out.println(getCurrentTime()+" saved.");
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }

    private String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        return now.truncatedTo(ChronoUnit.SECONDS).format(dtf);
    }

    private BufferedImage resizeImage(BufferedImage bufferedImage){
        return Scalr.resize(bufferedImage,  Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH,
                700, 510, Scalr.OP_ANTIALIAS);
    }

    private BufferedImage sharpenImage(BufferedImage bufferedImage){
        Kernel kernel = new Kernel(3, 3, new float[]
                {-1, -1, -1,
                -1, 9, -1,
                -1, -1, -1 });
        BufferedImageOp op = new ConvolveOp(kernel);
        bufferedImage = op.filter(bufferedImage, null);
        return bufferedImage;
    }
}
