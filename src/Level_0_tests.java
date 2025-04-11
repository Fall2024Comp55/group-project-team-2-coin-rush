import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import acm.graphics.GLine;
import acm.program.GraphicsProgram;

public class Level_0_tests extends GraphicsProgram {

    private Player player;
    private Platform platform;
    private Enemy enemy;
    private testCoin coin;

    private boolean gridVisible = true; //used for key presse
    private ArrayList<GLine> gridLines = new ArrayList<>(); //stores the grid lines
    public static final int GRID_SIZE = 40; //size of grid cell

//draws the grid to screen
    private void drawGrid(int cellSize) {
    	/*	Each grid cell is 40 pixels wide and 40 pixels tall (GRID_SIZE)
    	 *	The grid divides the 1280×720 window into a 32×18 grid (Columm'x'Row)
    	 */
        for (int x = 0; x <= MainApplication.WINDOW_WIDTH; x += cellSize) {
            GLine vertical = new GLine(x, 0, x, MainApplication.WINDOW_HEIGHT);
            vertical.setColor(Color.LIGHT_GRAY);
            add(vertical);
            gridLines.add(vertical);
        }
        for (int y = 0; y <= MainApplication.WINDOW_HEIGHT; y += cellSize) {
            GLine horizontal = new GLine(0, y, MainApplication.WINDOW_WIDTH, y);
            horizontal.setColor(Color.LIGHT_GRAY);
            add(horizontal);
            gridLines.add(horizontal);
        }
    }

    private void clearGrid() {
        for (GLine line : gridLines) {
            remove(line);
        }
        gridLines.clear();
    }

    public void run() {
    	if (gridVisible) {
    	    drawGrid(GRID_SIZE);
    	}
        setSize(1280, 720); // Window size
        addKeyListeners();

        //player
        player = new Player();
        player.setProgram(this);
        player.spawn(100, 300);
        
        //test coins
        coin = new testCoin(3, 3, 5);
        coin.setProgram(this);
        coin.init();
        
        platform = new Platform();
        platform.setProgram(this);
        
        while (true) {
            player.update(); //updates the Player animation loop & movement
            coin.update(player.getBounds()); //updates the collision to check if player is touching a coin
           // platform.update(player);
            pause(16.66); // 60 FPS
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        //I have it so that I can turn off the grid with 'g' key
        //If you plan to add an "edit mode", do so here.
        if (e.getKeyCode() == KeyEvent.VK_G) {
            gridVisible = !gridVisible;
            if (gridVisible) {
                drawGrid(GRID_SIZE);
            } else {
                clearGrid();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        player.keyTyped(e);
    }

    public static void main(String[] args) {
        new Level_0_tests().start();
    }
}
