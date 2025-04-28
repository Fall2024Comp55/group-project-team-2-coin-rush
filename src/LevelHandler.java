import java.util.ArrayList;

public class LevelHandler {
    private Player player;
    private Platform platform;
    private ArrayList<Enemy> enemies;
    private testCoin coin;
    private Door door;
    private hitBox playerHitbox;
    private UI_Elements UI;

    private boolean levelComplete = false; // NEW FLAG

    public LevelHandler(Player player, Platform platform, ArrayList<Enemy> enemies, testCoin coin, Door door, hitBox playerHitbox, UI_Elements UI) {
        this.player = player;
        this.platform = platform;
        this.enemies = enemies;
        this.coin = coin;
        this.door = door;
        this.playerHitbox = playerHitbox;
        this.UI = UI;
    }

    public void updateLevel() {
        if (levelComplete) {
            return; // Prevent updates after winning
        }

        platform.updatePlatforms(); // move platforms first
        player.update();
        playerHitbox.updateHitbox(player.getX(), player.getY(), 20, 3);
        coin.update(playerHitbox);
        platform.handlePlatformInteraction(player, playerHitbox);

        for (Enemy enemy : enemies) {
            enemy.update(playerHitbox, player);
        }

        door.checkIfplayerCanExit(coin.getCoinsCollected());
        UI.init(door, coin, player);

        // 🎯 New: check if player touches door after it is open
        if (door.isOpen() && door.playerTouchingDoor(playerHitbox)) {
            System.out.println("Player touched open door!");
            levelComplete = true; // stop updates
            showScoreboard(); // go to scoreboard
        }
    }

    public void resetLevel() {
        player.setHP(3);
        coin = new testCoin(8); // Optional: re-create coins
        for (Enemy enemy : enemies) {
            enemy = new Enemy(); // Optional: reset enemies
        }
        door = new Door(3, 550, 500); // Optional: re-create door
        levelComplete = false;
    }

    // 🎯 NEW: show scoreboard
    private void showScoreboard() {
        MainApplication mainApp = (MainApplication) player.getProgram();
        mainApp.switchToScoreboard(0, 0, 0);
    }

}
