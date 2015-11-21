/*
 * File: GraphicsContest.java
 * --------------------------
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.event.*;

public class GraphicsContest extends GraphicsProgram {

	/**
	 * constants
	 */
	private static final int DELAY=100;
	private static final double NEW_GAME_WIDTH=0;
	private static final double NEW_GAME_ASCENT=0;
	
	/**
	 * instant variables
	 */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public void run() {
		//the image at the first display
		GImage face=new GImage("FengMian.jpg");
		face.scale(0.43,0.43);
		add(face,-300,0);
		
		//new game
		GLabel newGame= new GLabel("New Game");
		newGame.setFont("Times-20");
		add(newGame,(getWidth()-newGame.getWidth())/2.0,getHeight()-2*newGame.getAscent());
		
		//special effects
		createEffect(newGame);
		
		//start game
		addMouseListeners();
		removeAll();
		playGame();
	}

	/**
	 * make certain image move randomly on the screen
	 */
	private void createEffect(GLabel newGame) {
		//draw circles on the screen
		GImage c1=drawCircle();
		GImage c2=drawCircle();
		GImage c3=drawCircle();
		GImage c4=drawCircle();
		GImage c5=drawCircle();
		//get velocity and move
		while() {
			
		}
	}
	
	/**
	 * draw one circle on the screen
	 * @return circle
	 */
	private GImage drawCircle() {
		GImage circle=new GImage("circle.png");
		double ratio=rgen.nextDouble();
		circle.scale(ratio,ratio);
		int x=rgen.nextInt(getWidth());
		int y=rgen.nextInt(getHeight());
		add(circle,x,y);
		return circle;
	}
	/**
	 * get velocity and move
	 * @param circle
	 */
	private void moveCircle(GImage circle) {
		while(!(circle.getX()>getWidth() && circle.getY()>getHeight())) {
			int vx=rgen.nextInt(2,6);
			int vy=rgen.nextInt(2,6);
			circle.move(vx, vy);
			pause(DELAY);
		}
		remove(circle);
	}
	
	
	private boolean mouseClicked(MouseEvent e) {
		double x=(getWidth()-NEW_GAME_WIDTH)/2.0;
		double y=getHeight()-2*NEW_GAME_ASCENT;
		boolean clicked=false;
		boolean xContain=e.getX()>x && e.getX()<x+NEW_GAME_WIDTH;
		boolean yContain=e.getY()>y && e.getY()<y+NEW_GAME_ASCENT;
		if()) {
			clicked=true;
		}
		return clicked;
	}
}
