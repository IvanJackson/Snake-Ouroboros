package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage title;
    public static BufferedImage GameOver;
    public static BufferedImage Pause;
    public static BufferedImage[] Resume;
    public static BufferedImage[] BTitle;
    public static BufferedImage[] Options;
    public static ImageIcon icon;
    

    public Images() {

        butstart = new BufferedImage[3];
        Resume = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        

        try {
        	
            title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Artboard.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Sheets/Pause.png"));
            GameOver = ImageIO.read(getClass().getResourceAsStream("/Sheets/Game Over.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeU.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeC.png"));
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitle.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitleP.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/buttonStart.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/buttonStart hover.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/buttonStartClicked.png"));//clickbut

            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));


        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
