import acm.program.GraphicsProgram;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LevelManager extends GraphicsProgram {
    private Level_1 level1;
    private Level_2 level2;
    private Level_3 level3;
    private Level_4 level4;

    
    private int currentLevel = 3; //Tracks which level you're on

    public void run() {
        setSize(1280, 720);
        addKeyListeners();
        addMouseListeners();
        showContent();
        
        while (true) {
            updateContent();
            pause(16.66); // 60fps
        }
    }

    public void showContent() {
        removeAll(); // Clear the canvas
        System.out.println("Loading Level " + currentLevel);

		switch (currentLevel) {
            case 1:
                level1 = new Level_1(this);
                level1.setUpLevel();
                break;
     
            case 2:
                level2 = new Level_2(this);
                level2.setUpLevel();
                break;
                
            case 3:
                level3 = new Level_3(this);
                level3.setUpLevel();
                break;
                
            case 4:
            	level4 = new Level_4(this);
            	level4.setUpLevel();
            	break;
                
            default:
                System.out.println("No more levels!");
                System.out.println("Game Over - Thanks for playing!");
                break;
        }
    }
    
    private void updateContent() {
        switch (currentLevel) {
            case 1:
                if (level1 != null) level1.updateLevel();
                break;
                
            case 2:
                if (level2 != null) level2.updateLevel();
                break;
                
            case 3:
                if (level3 != null) level3.updateLevel();
                break;
                
            case 4:
            	if (level4 != null) level4.updateLevel();
            	break;
            	
                }
    }


    public void nextLevel() {
        currentLevel++;
        showContent(); //loads next level
    }

    public void restartLevel() {
        showContent(); //reloads current level
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentLevel == 1 && level1 != null) {
            level1.keyPressed(e);
        } else if (currentLevel == 2 && level2 != null) {
            level2.keyPressed(e);
        } else if (currentLevel == 3 && level3 != null) {
            level3.keyPressed(e);
        } else if(currentLevel == 4 && level4 != null) {
        	level4.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (currentLevel == 1 && level1 != null) {
            level1.keyReleased(e);
        } else if (currentLevel == 2 && level2 != null) {
            level2.keyReleased(e);
        } else if (currentLevel == 3 && level3 != null) {
            level3.keyReleased(e);
        } else if(currentLevel == 4 && level4 != null) {
        	level4.keyPressed(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (currentLevel == 1 && level1 != null) {
            level1.mousePressed(e);
        } else if (currentLevel == 2 && level2 != null) {
            level2.mousePressed(e);
        } else if (currentLevel == 3 && level3 != null) {
            level3.mousePressed(e);
        } else if(currentLevel == 4 && level4 != null) {
        	level4.mousePressed(e);
        }
    }
}
