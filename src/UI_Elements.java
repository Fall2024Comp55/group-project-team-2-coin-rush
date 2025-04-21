import acm.graphics.*;
import acm.program.*;

public class UI_Elements {
	
	private GraphicsProgram program;

	// Sets the GraphicsProgram context
    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }
	
	private void healthBar(Player player) {
		player.getHP();
	}
	
	private int coinsCollected(testCoin coin) {
		return coin.getCoinsCollected();
	}
	public void doorState(Door door) {
		GImage door1 = new GImage(door.getCloseDoor());
		door1.setLocation(door.PROG_WIDTH   , 0);
		 door1.scale(0.05);

		program.add(door1);
		if(door.isOpen()) {
			door1.setImage(door.getOpenDoor());
		}
//		door.checkIfplayerCanExit(coinsCollected);
//		door1.scale(0.05);
		//program.add(door1);
	}
}
