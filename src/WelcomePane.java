import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class WelcomePane extends GraphicsPane {
    private MainApplication mainScreen;

    public WelcomePane(MainApplication mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void showContent() {
        addPicture();
        addButtonStart();
        addButtonExit();
    }

    @Override
    public void hideContent() {
        for (GObject item : contents) {
            mainScreen.remove(item);
        }
        contents.clear();
    }

    private void addPicture() {
        GImage startImage = new GImage("Media/welcomepane.jpg", 200, 100);
        startImage.setSize(mainScreen.getWidth(), mainScreen.getHeight());
        startImage.setLocation((mainScreen.getWidth() - startImage.getWidth()) / 2, 0);

        contents.add(startImage);
        mainScreen.add(startImage);
    }

    private void addButtonStart() {
        GImage startButton = new GImage("CGB02-yellow_L_btn.png", 200, 400);
        startButton.scale(0.3, 0.3);

        GLabel buttonLabel = new GLabel("START LEVEL 0");
        buttonLabel.setFont("SansSerif-bold-18");
        buttonLabel.setColor(Color.WHITE);

        GCompound buttonCompound = new GCompound();
        buttonCompound.add(startButton, 0, 0);

        double labelX = (startButton.getWidth() - buttonLabel.getWidth()) / 2;
        double labelY = (startButton.getHeight() + buttonLabel.getAscent()) / 2;
        buttonCompound.add(buttonLabel, labelX, labelY);

        double x = (mainScreen.getWidth() - buttonCompound.getWidth()) / 2;
        double y = 400;
        buttonCompound.setLocation(x, y);

        contents.add(buttonCompound);
        mainScreen.add(buttonCompound);
    }

    private void addButtonExit() {
        GImage exitButton = new GImage("CGB02-yellow_L_btn.png", 200, 450);
        exitButton.scale(0.3, 0.3);

        GLabel buttonLabel = new GLabel("EXIT");
        buttonLabel.setFont("SansSerif-bold-18");
        buttonLabel.setColor(Color.WHITE);

        GCompound buttonCompound = new GCompound();
        buttonCompound.add(exitButton, 0, 0);

        double labelX = (exitButton.getWidth() - buttonLabel.getWidth()) / 2;
        double labelY = (exitButton.getHeight() + buttonLabel.getAscent()) / 2;
        buttonCompound.add(buttonLabel, labelX, labelY);

        double x = (mainScreen.getWidth() - buttonCompound.getWidth()) / 2;
        double y = 450;
        buttonCompound.setLocation(x, y);

        contents.add(buttonCompound);
        mainScreen.add(buttonCompound);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GObject clicked = mainScreen.getElementAt(e.getX(), e.getY());

        if (clicked != null && contents.size() > 1) {
            if (contents.get(1).contains(e.getX(), e.getY())) {
                mainScreen.switchToLevel0Pane();
            } else if (contents.size() > 2 && contents.get(2).contains(e.getX(), e.getY())) {
                System.exit(0);
            }
        }
    }
}
