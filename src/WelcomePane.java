import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class WelcomePane extends GraphicsPane{
	public WelcomePane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		addPicture();
		addDescriptionButton();
		addPicture1();
		addPicture2();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	private void addPicture(){
		GImage startImage = new GImage("start.png", 200, 100);
		startImage.scale(0.5, 0.5);
		startImage.setLocation((mainScreen.getWidth() - startImage.getWidth())/ 2, 70);
		
		contents.add(startImage);
		mainScreen.add(startImage);
	}
	
	private void addDescriptionButton() {
		GImage moreButton = new GImage("CGB02-yellow_L_btn.png", 200, 400);
		moreButton.scale(0.3, 0.3);
		moreButton.setLocation((mainScreen.getWidth() - moreButton.getWidth())/ 2, 400);
		
		contents.add(moreButton);
		mainScreen.add(moreButton);

	}
	
	private void addPicture1() {
		GImage moreButton1 = new GImage("CGB02-yellow_L_btn.png", 200, 400);
		moreButton1.scale(0.3, 0.3);
		moreButton1.setLocation((mainScreen.getWidth() - moreButton1.getWidth())/ 2, 450);
		
		contents.add(moreButton1);
		mainScreen.add(moreButton1);
	}
	
	private void addPicture2() {
		GImage moreButton2 = new GImage("CGB02-yellow_L_btn.png", 200, 400);
		moreButton2.scale(0.3, 0.3);
		moreButton2.setLocation((mainScreen.getWidth() - moreButton2.getWidth())/ 2, 500);
		
		contents.add(moreButton2);
		mainScreen.add(moreButton2);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(1)) {
			mainScreen.switchToDescriptionScreen();
		}
	}

}
