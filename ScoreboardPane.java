import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;

public class ScoreboardPane extends GraphicsPane {
    private MainApplication program;
    private int timeLeft;
    private int coinsCollected;
    private int finalScore;
    private int currentLevel;

    private GLabel scoreLabel;
    private GLabel timeBonusLabel;
    private GLabel coinBonusLabel;
    private GCompound nextButtonCompound;
    private GCompound restartButtonCompound;
    private GCompound menuButtonCompound;
    private GRect overlay;

    public ScoreboardPane(MainApplication app, int timeLeft, int coinsCollected, int currentLevel) {
        this.program = app;
        this.timeLeft = timeLeft;
        this.coinsCollected = coinsCollected;
        this.currentLevel = currentLevel;

        finalScore = (timeLeft * 500) + (coinsCollected * 250);
    }

    @Override
    public void showContent() {
        // Transparent background
        overlay = new GRect(0, 0, MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
        overlay.setFilled(true);
        overlay.setFillColor(new Color(0, 0, 0, 100)); // Black with 100 alpha transparency
        overlay.setColor(new Color(0, 0, 0, 0)); // No border
        program.add(overlay);
        contents.add(overlay);

        // Time bonus label
        timeBonusLabel = new GLabel("Time Bonus: " + timeLeft + " × 500 = " + (timeLeft * 500));
        timeBonusLabel.setFont("SansSerif-bold-24");
        timeBonusLabel.setColor(Color.WHITE);
        double timeBonusX = (MainApplication.WINDOW_WIDTH - timeBonusLabel.getWidth()) / 2;
        timeBonusLabel.setLocation(timeBonusX, 120);
        program.add(timeBonusLabel);
        contents.add(timeBonusLabel);

        // Coin bonus label
        coinBonusLabel = new GLabel("Coins Bonus: " + coinsCollected + " × 250 = " + (coinsCollected * 250));
        coinBonusLabel.setFont("SansSerif-bold-24");
        coinBonusLabel.setColor(Color.WHITE);
        double coinBonusX = (MainApplication.WINDOW_WIDTH - coinBonusLabel.getWidth()) / 2;
        coinBonusLabel.setLocation(coinBonusX, 170);
        program.add(coinBonusLabel);
        contents.add(coinBonusLabel);

        // Final Score label
        scoreLabel = new GLabel("Final Score: " + finalScore);
        scoreLabel.setFont("SansSerif-bold-36");
        scoreLabel.setColor(Color.WHITE);
        double scoreX = (MainApplication.WINDOW_WIDTH - scoreLabel.getWidth()) / 2;
        scoreLabel.setLocation(scoreX, 230);
        program.add(scoreLabel);
        contents.add(scoreLabel);

        createNextButton();
        createRestartButton();
        createMenuButton();
    }

    private void createNextButton() {
        GImage buttonImage = new GImage("CGB02-yellow_M_btn.png");
        buttonImage.scale(0.4, 0.4);

        GLabel label = new GLabel("NEXT LEVEL");
        label.setFont("SansSerif-bold-18");
        label.setColor(Color.WHITE);

        nextButtonCompound = new GCompound();
        nextButtonCompound.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        nextButtonCompound.add(label, labelX, labelY);

        nextButtonCompound.setLocation((MainApplication.WINDOW_WIDTH - buttonImage.getWidth()) / 2, 330);
        program.add(nextButtonCompound);
        contents.add(nextButtonCompound);
    }

    private void createRestartButton() {
        GImage buttonImage = new GImage("CGB02-yellow_M_btn.png");
        buttonImage.scale(0.4, 0.4);

        GLabel label = new GLabel("RESTART LEVEL");
        label.setFont("SansSerif-bold-18");
        label.setColor(Color.WHITE);

        restartButtonCompound = new GCompound();
        restartButtonCompound.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        restartButtonCompound.add(label, labelX, labelY);

        restartButtonCompound.setLocation((MainApplication.WINDOW_WIDTH - buttonImage.getWidth()) / 2, 400);
        program.add(restartButtonCompound);
        contents.add(restartButtonCompound);
    }

    private void createMenuButton() {
        GImage buttonImage = new GImage("CGB02-yellow_M_btn.png");
        buttonImage.scale(0.4, 0.4);

        GLabel label = new GLabel("MAIN MENU");
        label.setFont("SansSerif-bold-18");
        label.setColor(Color.WHITE);

        menuButtonCompound = new GCompound();
        menuButtonCompound.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        menuButtonCompound.add(label, labelX, labelY);

        menuButtonCompound.setLocation((MainApplication.WINDOW_WIDTH - buttonImage.getWidth()) / 2, 470);
        program.add(menuButtonCompound);
        contents.add(menuButtonCompound);
    }

    @Override
    public void hideContent() {
        for (GObject obj : contents) {
            program.remove(obj);
        }
        contents.clear();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GObject clicked = program.getElementAt(e.getX(), e.getY());

        if (clicked != null) {
            if (nextButtonCompound.getElementAt(e.getX() - nextButtonCompound.getX(), e.getY() - nextButtonCompound.getY()) != null) {
                program.switchToNextLevel(currentLevel);
            } else if (restartButtonCompound.getElementAt(e.getX() - restartButtonCompound.getX(), e.getY() - restartButtonCompound.getY()) != null) {
                program.restartLevel(currentLevel);
            } else if (menuButtonCompound.getElementAt(e.getX() - menuButtonCompound.getX(), e.getY() - menuButtonCompound.getY()) != null) {
                program.switchToWelcomeScreen();
            }
        }
    }
}
