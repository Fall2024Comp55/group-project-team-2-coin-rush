import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class PausePane extends GraphicsPane {
    private MainApplication mainScreen;
    private GCompound continueButtonCompound;
    private GCompound quitButtonCompound;
    private GRect overlay;

    public PausePane(MainApplication mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void showContent() {
        overlay = new GRect(0, 0, MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
        overlay.setFilled(true);
        overlay.setFillColor(new Color(0, 0, 0, 50));
        overlay.setColor(new Color(0, 0, 0, 0));
        mainScreen.add(overlay);
        contents.add(overlay);

        createContinueButton();
        createQuitButton();
    }

    @Override
    public void hideContent() {
        for (GObject item : contents) {
            mainScreen.remove(item);
        }
        contents.clear();
    }

    private void createContinueButton() {
        GImage continueButtonImage = new GImage("CGB02-yellow_M_btn.png");
        continueButtonImage.scale(0.3, 0.3);

        GLabel continueLabel = new GLabel("CONTINUE");
        continueLabel.setFont("SansSerif-bold-18");
        continueLabel.setColor(Color.WHITE);

        continueButtonCompound = new GCompound();
        continueButtonCompound.add(continueButtonImage, 0, 0);

        double labelX = (continueButtonImage.getWidth() - continueLabel.getWidth()) / 2;
        double labelY = (continueButtonImage.getHeight() + continueLabel.getAscent()) / 2;
        continueButtonCompound.add(continueLabel, labelX, labelY);

        double x = (MainApplication.WINDOW_WIDTH / 2) - 200;
        double y = 150;

        continueButtonCompound.setLocation(x, y);
        contents.add(continueButtonCompound);
        mainScreen.add(continueButtonCompound);
    }

    private void createQuitButton() {
        GImage quitButtonImage = new GImage("CGB02-yellow_M_btn.png");
        quitButtonImage.scale(0.3, 0.3);

        GLabel quitLabel = new GLabel("QUIT");
        quitLabel.setFont("SansSerif-bold-18");
        quitLabel.setColor(Color.WHITE);

        quitButtonCompound = new GCompound();
        quitButtonCompound.add(quitButtonImage, 0, 0);

        double labelX = (quitButtonImage.getWidth() - quitLabel.getWidth()) / 2;
        double labelY = (quitButtonImage.getHeight() + quitLabel.getAscent()) / 2;
        quitButtonCompound.add(quitLabel, labelX, labelY);

        double x = (MainApplication.WINDOW_WIDTH / 2) + 50;
        double y = 150;

        quitButtonCompound.setLocation(x, y);
        contents.add(quitButtonCompound);
        mainScreen.add(quitButtonCompound);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GObject clicked = mainScreen.getElementAt(e.getX(), e.getY());

        if (clicked != null) {
            if (continueButtonCompound.getElementAt(e.getX() - continueButtonCompound.getX(), e.getY() - continueButtonCompound.getY()) != null) {
                mainScreen.resumePreviousPane();
            } 
            else if (quitButtonCompound.getElementAt(e.getX() - quitButtonCompound.getX(), e.getY() - quitButtonCompound.getY()) != null) {
                GraphicsPane previous = mainScreen.getPreviousPane();
                if (previous != null) {
                    previous.hideContent();
                }
                mainScreen.switchToWelcomePane();
            }
        }
    }


}
