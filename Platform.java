import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.program.GraphicsProgram;
import java.awt.Color;
import java.util.ArrayList;

public class Platform extends MainApplication {
    private ArrayList<GRect> Platforms = new ArrayList<>();
    private ArrayList<PlatformTypes> PlatformTypesList = new ArrayList<>();
    private ArrayList<Double> PlatformSpeeds = new ArrayList<>();
    private ArrayList<Integer> PlatformDistances = new ArrayList<>();
    private ArrayList<Double> PlatformStartX = new ArrayList<>();
    private ArrayList<Double> PlatformX = new ArrayList<>();
    private ArrayList<Double> PlatformStartY = new ArrayList<>();
    private ArrayList<Double> PlatformY = new ArrayList<>();
    private ArrayList<Boolean> PlatformVerticality = new ArrayList<>();
    private GraphicsProgram program;

    public enum PlatformTypes {
        STATIC, MOVING;
    }

    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }

    public ArrayList<GRect> getPlatforms() {
        return Platforms;
    }

    public void addPlatformsToScreen() {
        for (GRect rect : Platforms) {
            program.add(rect);
        }
    }

    public void addPlatform(int locX, int locY, int width, int height, PlatformTypes type, double speed, int maxDistance, boolean isVert) {
        GRect rect = new GRect(locX, locY, width, height);
        rect.setColor(Color.red);
        rect.setFilled(true);
        Platforms.add(rect);
        PlatformTypesList.add(type);
        PlatformSpeeds.add(speed);
        PlatformDistances.add(maxDistance);
        PlatformVerticality.add(isVert);
        PlatformX.add((double) locX);
        PlatformStartX.add((double) locX);
        PlatformY.add((double) locY);
        PlatformStartY.add((double) locY);
    }

    public void updatePlatforms() {
        for (int i = 0; i < Platforms.size(); i++) {
            GRect rect = Platforms.get(i);
            PlatformTypes type = PlatformTypesList.get(i);
            double platformSpeed = PlatformSpeeds.get(i);
            int maxDistance = PlatformDistances.get(i);
            boolean isVert = PlatformVerticality.get(i);
            double currentX = PlatformX.get(i);
            double startX = PlatformStartX.get(i);
            double currentY = PlatformY.get(i);
            double startY = PlatformStartY.get(i);

            if (type == PlatformTypes.MOVING && platformSpeed != 0 && maxDistance != 0) {
                if (isVert) {
                    currentY += platformSpeed;
                    if (maxDistance > 0) {
                        if (currentY > startY + maxDistance) {
                            currentY = startY + maxDistance;
                            PlatformSpeeds.set(i, -Math.abs(platformSpeed));
                        } else if (currentY < startY) {
                            currentY = startY;
                            PlatformSpeeds.set(i, Math.abs(platformSpeed));
                        }
                    } else {
                        if (currentY < startY + maxDistance) {
                            currentY = startY + maxDistance;
                            PlatformSpeeds.set(i, Math.abs(platformSpeed));
                        } else if (currentY > startY) {
                            currentY = startY;
                            PlatformSpeeds.set(i, -Math.abs(platformSpeed));
                        }
                    }
                } else {
                    currentX += platformSpeed;
                    if (maxDistance > 0) {
                        if (currentX > startX + maxDistance) {
                            currentX = startX + maxDistance;
                            PlatformSpeeds.set(i, -Math.abs(platformSpeed));
                        } else if (currentX < startX) {
                            currentX = startX;
                            PlatformSpeeds.set(i, Math.abs(platformSpeed));
                        }
                    } else {
                        if (currentX < startX + maxDistance) {
                            currentX = startX + maxDistance;
                            PlatformSpeeds.set(i, Math.abs(platformSpeed));
                        } else if (currentX > startX) {
                            currentX = startX;
                            PlatformSpeeds.set(i, -Math.abs(platformSpeed));
                        }
                    }
                }
                PlatformX.set(i, currentX);
                PlatformY.set(i, currentY);
                rect.setLocation(currentX, currentY);
            }
        }
    }

    public GRect detectPlatformCollision(hitBox playerHitbox) {
        for (GRect platform : Platforms) {
            if (playerHitbox.getBounds().intersects(platform.getBounds())) {
                return platform;
            }
        }
        return null;
    }

    public void handlePlatformInteraction(Player player, hitBox playerHitbox) {
        GRect touched = detectPlatformCollision(playerHitbox);
        player.setGrounded(false);
        if (touched != null) {
            double playerBottom = player.getY() + playerHitbox.getHeight();
            double playerTop = player.getY();
            double platformTop = touched.getY();
            double platformBottom = touched.getY() + touched.getHeight();

            if (playerBottom >= platformTop && playerBottom <= platformTop + 15 && player.getyVelocity() >= 0) {
                player.setGrounded(true);
                player.setyVelocity(0);
                player.setY(platformTop - playerHitbox.getHeight());
            } else if (playerTop <= platformBottom && playerTop >= platformBottom - 15 && player.getyVelocity() < 0) {
                player.setyVelocity(0);
            } else if (playerBottom > platformTop && playerBottom <= platformBottom && player.getyVelocity() > 0) {
                player.setyVelocity(0);
                player.setY(platformTop - playerHitbox.getHeight());
                player.setGrounded(true);
            }
        }
    }
}
