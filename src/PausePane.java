import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class PausePane extends GraphicsPane {
	
	private GCompound continueButtonCompound;
	private GCompound returnButtonCompound;
	
	
	public PausePane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	} 
	
	
	public void showContent() {
		GRect overlay = new GRect(0, 0, MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		overlay.setFilled(true);
		overlay.setFillColor(new Color(0, 0, 0,150));
		mainScreen.add(overlay);
		contents.add(overlay);
		createButton();
		createButton1();
		
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
		continueLabel.setFont("SansSerif-bold-13");
		continueLabel.setColor(Color.WHITE);
		
		continueButtonCompound = new GCompound();
		continueButtonCompound.add(continueButton, 0, 0);
		
		double labelX = (continueButton.getWidth() - continueLabel.getWidth()) / 2;
		double labelY = (continueButton.getHeight() - continueLabel.getAscent()) / 2;
		continueButtonCompound.add(continueLabel, labelX, labelY);
		
		double x = (mainScreen.getWidth() - continueButtonCompound.getWidth()) / 2;
		double y = (mainScreen.getHeight() -continueButtonCompound.getHeight()) / 2;
		
		continueButtonCompound.setLocation(x, y - 200);
		
		contents.add(continueButtonCompound);
		mainScreen.add(continueButtonCompound);
	}
	
	private void createButton1() {
		GImage returnButton = new GImage("CGB02-yellow_M_btn.png",200,500);
		returnButton.scale(0.3, 0.3);
		
		GLabel returnLabel =  new GLabel("QUIT");
		returnLabel.setFont("SansSerif-bold-13");
		returnLabel.setColor(Color.WHITE);
		
		returnButtonCompound = new GCompound();
		returnButtonCompound.add(returnButton, 0, 0);
		
		double labelX = (returnButton.getWidth() - returnLabel.getWidth()) / 2;
		double labelY = (returnButton.getHeight() - returnLabel.getAscent()) / 2;
		returnButtonCompound.add(returnLabel, labelX, labelY);
		
		double x = (mainScreen.getWidth() - returnButtonCompound.getWidth()) / 2;
		double y = (mainScreen.getHeight() -returnButtonCompound.getHeight()) / 2 + 50;
		
		returnButtonCompound.setLocation(x, y - 200);
		
		contents.add(returnButtonCompound);
		mainScreen.add(returnButtonCompound);
	}
	
	public void mouseClicked(MouseEvent e) {
		GObject clicked = mainScreen.getElementAtLocation(e.getX(), e.getY());
		if (clicked == continueButtonCompound) {
			mainScreen.hidePauseScreen();
			mainScreen.startLevel0Timer();
        }
		else if (clicked == returnButtonCompound) {
			mainScreen.hidePauseScreen();
			mainScreen.switchToWelcomeScreen();
		}
	}
	

}













