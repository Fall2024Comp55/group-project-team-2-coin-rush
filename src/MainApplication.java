import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public class MainApplication extends GraphicsProgram {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    private WelcomePane welcomePane;
    private Level0Pane level0Pane;
    private Level1Pane level1Pane;
    private Level2Pane level2Pane;
    private Level3Pane level3Pane;
    private Level4Pane level4Pane;
    private PausePane pausePane;
    private ScoreboardPane scoreboardPane; // Note: this is now created dynamically
    private GraphicsPane currentPane;
    private GraphicsPane previousPane;
    private int totalScore = 0;
    private FinalScoreboardPane finalScoreboardPane;
    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        welcomePane = new WelcomePane(this);
        level0Pane = new Level0Pane(this);
        level1Pane = new Level1Pane(this);
        level2Pane = new Level2Pane(this);
        level3Pane = new Level3Pane(this);
        level4Pane = new Level4Pane(this);
        pausePane = new PausePane(this);

        switchToWelcomePane();
        addKeyListeners();
        addMouseListeners();
    }

    public void run() {}

    public void switchToWelcomePane() {
        if (currentPane != null) currentPane.hideContent();
        currentPane = welcomePane;
        currentPane.showContent();
    }

    public void switchToLevel0Pane() {
        if (currentPane != null) currentPane.hideContent();
        currentPane = level0Pane;
        currentPane.showContent();
        startTimer();
    }

    public void switchToLevel1Pane() {
        if (currentPane != null) currentPane.hideContent();
        currentPane = level1Pane;
        currentPane.showContent();
        startTimer();
    }

    public void switchToLevel2Pane() {
        if (currentPane != null) currentPane.hideContent();
        currentPane = level2Pane;
        currentPane.showContent();
        startTimer();
    }

    public void switchToLevel3Pane() {
        if (currentPane != null) currentPane.hideContent();
        currentPane = level3Pane;
        currentPane.showContent();
        startTimer();
    }

    public void switchToLevel4Pane() {
        if (currentPane != null) currentPane.hideContent();
        currentPane = level4Pane;
        currentPane.showContent();
        startTimer();
    }

    public void switchToPauseScreen() {
        if (currentPane != null && (currentPane instanceof Level0Pane || currentPane instanceof Level1Pane || currentPane instanceof Level2Pane || currentPane instanceof Level3Pane || currentPane instanceof Level4Pane)) {
            pauseCurrentLevelTimer();
            previousPane = currentPane; // <--- THIS IS THE IMPORTANT PART
        }
        currentPane = pausePane;
        currentPane.showContent();
    }

    public void resumePreviousPane() {
        if (currentPane != null) currentPane.hideContent();
        currentPane = previousPane;
        startTimer();
    }

    public void switchToScoreboard(int timeLeft, int coinsCollected, int currentLevel) {
        if (currentPane != null) {
            currentPane.hideContent();
        }
        scoreboardPane = new ScoreboardPane(this, timeLeft, coinsCollected, currentLevel); // <--- FIX
        currentPane = scoreboardPane;
        currentPane.showContent();
    }


    public void startTimer() {
        if (currentPane instanceof Level0Pane) ((Level0Pane) currentPane).startTimer();
        else if (currentPane instanceof Level1Pane) ((Level1Pane) currentPane).startTimer();
        else if (currentPane instanceof Level2Pane) ((Level2Pane) currentPane).startTimer();
        else if (currentPane instanceof Level3Pane) ((Level3Pane) currentPane).startTimer();
        else if (currentPane instanceof Level4Pane) ((Level4Pane) currentPane).startTimer();
    }


    public void pauseCurrentLevelTimer() {
        if (currentPane instanceof Level0Pane) ((Level0Pane) currentPane).pauseGame();
        else if (currentPane instanceof Level1Pane) ((Level1Pane) currentPane).pauseGame();
        else if (currentPane instanceof Level2Pane) ((Level2Pane) currentPane).pauseGame();
        else if (currentPane instanceof Level3Pane) ((Level3Pane) currentPane).pauseGame();
        else if (currentPane instanceof Level4Pane) ((Level4Pane) currentPane).pauseGame();
    }
	
	public static void main(String[] args) {
		new MainApplication().start();
	}


    public void switchToNextLevel(int currentLevel) {
        if (currentLevel == 0) switchToLevel1Pane();
        else if (currentLevel == 1) switchToLevel2Pane();
        else if (currentLevel == 2) switchToLevel3Pane();
        else if (currentLevel == 3) switchToLevel4Pane();
        else switchToWelcomePane(); // after last level, return to WelcomePane
    }

    public void restartLevel(int currentLevel) {
        if (currentPane != null) {
            currentPane.hideContent();
        }

        if (currentLevel == 0) {
            level0Pane = new Level0Pane(this); // <-- recreate a new fresh Level0Pane
            currentPane = level0Pane;
            currentPane.showContent();
            startTimer();
        } else if (currentLevel == 1) {
            level1Pane = new Level1Pane(this);
            currentPane = level1Pane;
            currentPane.showContent();
            startTimer();
        } else if (currentLevel == 2) {
            level2Pane = new Level2Pane(this);
            currentPane = level2Pane;
            currentPane.showContent();
            startTimer();
        } else if (currentLevel == 3) {
            level3Pane = new Level3Pane(this);
            currentPane = level3Pane;
            currentPane.showContent();
            startTimer();
        } else if (currentLevel == 4) {
            level4Pane = new Level4Pane(this);
            currentPane = level4Pane;
            currentPane.showContent();
            startTimer();
        }
    }
    public void switchToFinalScoreboard(int timeLeft, int coinsCollected) {
        if (currentPane != null) {
            currentPane.hideContent();
        }
        finalScoreboardPane = new FinalScoreboardPane(this, timeLeft, coinsCollected);
        currentPane = finalScoreboardPane;
        currentPane.showContent();
    }



  

    public GraphicsPane getPreviousPane() {
        return previousPane;
    }

   
    public int getTotalScore() {
        return totalScore;
    }

    public void addToTotalScore(int score) {
        totalScore += score;
    }

	

	
	public void switchToWelcomeScreen() {
		switchToScreen(welcomePane);
	}
	
	
	public void switchToLevel0Screen() {
		switchToScreen(level0Pane);
	}
	
	
	
	public void hidePauseScreen() {
		pausePane.hideContent();
		 currentPane = level0Pane;
	}
	
	public void startLevel0Timer() {
		level0Pane.startTimer();
	}
	
	protected void switchToScreen(GraphicsPane newScreen) {
		if( currentPane != null) {
			 currentPane.hideContent();
		}
		newScreen.showContent();
		 currentPane = newScreen;
	}
	
	public GObject getElementAtLocation(double x, double y) {
		return getElementAt(x, y);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if( currentPane != null) {
			 currentPane.mousePressed(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if( currentPane != null) {
			 currentPane.mouseReleased(e);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if( currentPane != null) {
			 currentPane.mouseClicked(e);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if( currentPane != null) {
			 currentPane.mouseDragged(e);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if( currentPane != null) {
			 currentPane.mouseMoved(e);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if( currentPane != null) {
			 currentPane.keyPressed(e);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if( currentPane != null) {
			 currentPane.keyReleased(e);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if( currentPane != null) {
			 currentPane.keyTyped(e);
		}
	}


}
