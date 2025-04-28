import java.awt.event.MouseEvent;
import acm.graphics.*;

public class FinalScoreboardPane extends GraphicsPane {
    private MainApplication program;
    private int timeLeft;
    private int coinsCollected;
    private int finalLevelScore;
    private int totalScore;

    private GCompound restartButtonCompound;
    private GCompound menuButtonCompound;
    private GLabel titleLabel;
    private GLabel levelScoreLabel;
    private GLabel totalScoreLabel;
    private GImage background;

    public FinalScoreboardPane(MainApplication app, int timeLeft, int coinsCollected) {
        this.program = app;
        this.timeLeft = timeLeft;
        this.coinsCollected = coinsCollected;
        this.finalLevelScore = (timeLeft * 500) + (coinsCollected * 250);
        this.totalScore = program.getTotalScore(); // Assuming MainApplication has a totalScore tracker
    }

    @Override
    public void showContent() {
        // Optional background
        background = new GImage("Media/welcomepane.jpg");
        background.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
        program.add(background);
        contents.add(background);

        titleLabel = new GLabel("The End!", 500, 150);
        titleLabel.setFont("SansSerif-bold-36");
        titleLabel.setColor(java.awt.Color.WHITE);
        program.add(titleLabel);
        contents.add(titleLabel);

        levelScoreLabel = new GLabel("Level 4 Score: " + finalLevelScore, 480, 250);
        levelScoreLabel.setFont("SansSerif-bold-24");
        levelScoreLabel.setColor(java.awt.Color.YELLOW);
        program.add(levelScoreLabel);
        contents.add(levelScoreLabel);

        totalScoreLabel = new GLabel("Total Score: " + totalScore, 500, 300);
        totalScoreLabel.setFont("SansSerif-bold-28");
        totalScoreLabel.setColor(java.awt.Color.CYAN);
        program.add(totalScoreLabel);
        contents.add(totalScoreLabel);

        createRestartButton();
        createMenuButton();
    }

    private void createRestartButton() {
        GImage buttonImage = new GImage("CGB02-yellow_M_btn.png");
        buttonImage.scale(0.3, 0.3);

        GLabel label = new GLabel("RESTART");
        label.setFont("SansSerif-bold-18");
        label.setColor(java.awt.Color.BLACK);

        restartButtonCompound = new GCompound();
        restartButtonCompound.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        restartButtonCompound.add(label, labelX, labelY);

        restartButtonCompound.setLocation(480, 400);
        program.add(restartButtonCompound);
        contents.add(restartButtonCompound);
    }

    private void createMenuButton() {
        GImage buttonImage = new GImage("CGB02-yellow_M_btn.png");
        buttonImage.scale(0.3, 0.3);

        GLabel label = new GLabel("MAIN MENU");
        label.setFont("SansSerif-bold-18");
        label.setColor(java.awt.Color.BLACK);

        menuButtonCompound = new GCompound();
        menuButtonCompound.add(buttonImage, 0, 0);

        double labelX = (buttonImage.getWidth() - label.getWidth()) / 2;
        double labelY = (buttonImage.getHeight() + label.getAscent()) / 2;
        menuButtonCompound.add(label, labelX, labelY);

        menuButtonCompound.setLocation(480, 480);
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
            if (restartButtonCompound.getElementAt(e.getX() - restartButtonCompound.getX(), e.getY() - restartButtonCompound.getY()) != null) {
                program.switchToLevel0Pane(); // Restart the game from Level 0
            } else if (menuButtonCompound.getElementAt(e.getX() - menuButtonCompound.getX(), e.getY() - menuButtonCompound.getY()) != null) {
                program.switchToWelcomePane(); // Go back to main menu
            }
        }
    }
}
