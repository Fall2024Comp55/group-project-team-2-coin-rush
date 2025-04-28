import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;

public class ScoreboardPane extends GraphicsPane {
    private MainApplication program;
    private int timeLeft;
    private int coinsCollected;
    private int finalScore;
    private int currentLevel;

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
        overlay = new GRect(0, 0, MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
        overlay.setFilled(true);
        overlay.setFillColor(new Color(0, 0, 0, 150));
        overlay.setColor(new Color(0, 0, 0, 0));
        program.add(overlay);
        contents.add(overlay);

        GLabel titleLabel = new GLabel("LEVEL COMPLETE!");
        titleLabel.setFont("SansSerif-bold-48");
        titleLabel.setColor(Color.WHITE);
        titleLabel.setLocation((MainApplication.WINDOW_WIDTH - titleLabel.getWidth()) / 2, 100);
        program.add(titleLabel);
        contents.add(titleLabel);

        GLabel timeBonusLabel = new GLabel("Time Bonus: " + timeLeft + " × 500 = " + (timeLeft * 500));
        timeBonusLabel.setFont("SansSerif-bold-24");
        timeBonusLabel.setColor(Color.YELLOW);
        timeBonusLabel.setLocation((MainApplication.WINDOW_WIDTH - timeBonusLabel.getWidth()) / 2, 200);
        program.add(timeBonusLabel);
        contents.add(timeBonusLabel);

        GLabel coinBonusLabel = new GLabel("Coins Bonus: " + coinsCollected + " × 250 = " + (coinsCollected * 250));
        coinBonusLabel.setFont("SansSerif-bold-24");
        coinBonusLabel.setColor(Color.YELLOW);
        coinBonusLabel.setLocation((MainApplication.WINDOW_WIDTH - coinBonusLabel.getWidth()) / 2, 250);
        program.add(coinBonusLabel);
        contents.add(coinBonusLabel);

        GLabel finalScoreLabel = new GLabel("Final Score: " + finalScore);
        finalScoreLabel.setFont("SansSerif-bold-32");
        finalScoreLabel.setColor(Color.CYAN);
        finalScoreLabel.setLocation((MainApplication.WINDOW_WIDTH - finalScoreLabel.getWidth()) / 2, 320);
        program.add(finalScoreLabel);
        contents.add(finalScoreLabel);

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

        nextButtonCompound.setLocation((MainApplication.WINDOW_WIDTH - buttonImage.getWidth()) / 2, 400);
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

        restartButtonCompound.setLocation((MainApplication.WINDOW_WIDTH - buttonImage.getWidth()) / 2, 470);
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

        menuButtonCompound.setLocation((MainApplication.WINDOW_WIDTH - buttonImage.getWidth()) / 2, 540);
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