import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class PausePane extends GraphicsPane {
	public PausePane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	} 
	
	
	public void showContent() {
		createButton();
		
	}
	
	public void hideContent()  {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	private void createButton() {
		GImage continueButton = new GImage("CGB02-yellow_M_btn.png",200,500);
		continueButton.scale(0.3, 0.3);
		
		GLabel continueLabel =  new GLabel("CONTINUE");
		continueLabel.setFont("SansSerif-bold-18");
		continueLabel.setColor(Color.WHITE);
		
		GCompound buttonCompound = new GCompound();
		buttonCompound.add(continueButton, 0, 0);
		
		double labelX = (continueButton.getWidth() - continueLabel.getWidth()) / 2;
		double labelY = (continueButton.getHeight() - continueLabel.getAscent()) / 2;
		buttonCompound.add(continueLabel, labelX, labelY);
		
		double x = (mainScreen.getWidth() - buttonCompound.getWidth()) / 2;
		double y = (mainScreen.getHeight() -buttonCompound.getHeight()) / 2;
		
		buttonCompound.setLocation(x, y);
		
		contents.add(buttonCompound);
		mainScreen.add(buttonCompound);
	}

}
