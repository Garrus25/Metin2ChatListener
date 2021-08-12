import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCR {
    private String text = "";

    public void performOCR() {
        Tesseract tesseract = new Tesseract();
        try {
            tesseract.setLanguage("pol");
            tesseract.setTessVariable("user_defined_dpi", "300");
            tesseract.setDatapath("C:\\Users\\mati1\\IdeaProjects\\Tess4J\\tessdata");

            text = tesseract.doOCR(new File("src/main/java/Screenshots/jestgit.jpg"));

        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
    public String getText(){
        return text;
    }
}
