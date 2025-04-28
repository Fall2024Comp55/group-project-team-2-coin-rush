import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.*;
import acm.graphics.*;
import acm.program.*;

public class Enemy {
    public static int WINDOW_HEIGHT = 600;
    public static int WINDOW_WIDTH = 600;
    public static int X_VELOCITY = 1;
    public static final int DEFAULT = 0, MOVING = 1;

    private GImage EnemyImage;
    private GraphicsProgram program;
    private boolean isActive;
    private boolean movingRight;
    private GRect platform;
    private ArrayList<GImage> sprites;
    private int aniTick;
    private int aniIndex = 0;
    private int aniTickSpeed = 5;

    private double lastPlatformX;
    private double lastPlatformY;

    public Enemy() {
        loadAnimations();
    }

    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }

    private void loadAnimations() {
        sprites = loadImagesFromFolder("Media/Enemy_Sprite");
    }

    private ArrayList<GImage> loadImagesFromFolder(String folderPath) {
        ArrayList<GImage> images = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    images.add(new GImage(file.getPath()));
                }
            }
        }
        return images;
    }

    public void spawnEnemy(GRect platform) {
        if (platform != null) {
            this.platform = platform;
            EnemyImage = new GImage(sprites.get(0).getImage());
            EnemyImage.setLocation(platform.getX(), platform.getY() - EnemyImage.getHeight());
            program.add(EnemyImage);
            isActive = true;
            movingRight = true;

            lastPlatformX = platform.getX();
            lastPlatformY = platform.getY();
        }
    }

    private void followPlatform() {
        if (platform == null || EnemyImage == null) return;

        double dx = platform.getX() - lastPlatformX;
        double dy = platform.getY() - lastPlatformY;

        EnemyImage.move(dx, dy);

        lastPlatformX = platform.getX();
        lastPlatformY = platform.getY();
    }

    private void updateAnimation() {
        aniTick++;
        if (aniTick >= aniTickSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= sprites.size()) {
                aniIndex = 0;
            }
            EnemyImage.setImage(sprites.get(aniIndex).getImage());
        }
    }

    private void enemyMovement() {
        if (platform == null || EnemyImage == null) return;

        double enemyX = EnemyImage.getX();
        double velocity = X_VELOCITY;

        if (movingRight) {
            enemyX += velocity;
        } else {
            enemyX -= velocity;
        }

        if (enemyX < platform.getX()) {
            enemyX = platform.getX();
            movingRight = true;
        }

        double platformRightEdge = platform.getX() + platform.getWidth();
        double enemyRightEdge = enemyX + EnemyImage.getWidth();
        if (enemyRightEdge > platformRightEdge) {
            enemyX = platformRightEdge - EnemyImage.getWidth();
            movingRight = false;
        }

        EnemyImage.setLocation(enemyX, EnemyImage.getY());
    }

    public boolean collisionCheck(hitBox playerHitbox, Player player) {
        if (!isActive || EnemyImage == null) return false;

        GRectangle enemyBounds = new GRectangle(EnemyImage.getX(), EnemyImage.getY(), EnemyImage.getWidth(), EnemyImage.getHeight());

        if (playerHitbox.getY() + playerHitbox.getHeight() <= enemyBounds.getY() + 5 &&
            playerHitbox.getBounds().intersects(enemyBounds)) {
            removeEnemy();
        } else if (playerHitbox.getBounds().intersects(enemyBounds)) {
            System.out.println("Enemy detected");
            player.takeDamage();
            player.respawn();
        }
        return isActive;
    }

    public void removeEnemy() {
        if (EnemyImage != null) {
            program.remove(EnemyImage);
            isActive = false;
        }
    }

    public void update(hitBox playerHitbox, Player player) {
        if (!isActive) return;
        followPlatform();
        enemyMovement();
        collisionCheck(playerHitbox, player);
        updateAnimation();
    }

    public GImage getEnemyImage() {
        return EnemyImage;
    }
}
