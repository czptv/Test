/*
 * File: GraphicsContest.java
 * --------------------------
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

public class GraphicsContest extends GraphicsProgram {

	/**
	 * constants
	 */
	private static final int DELAY=100;
	
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
		newGame.setFont("Times-15");
		add(newGame,getWidth()-newGame.getWidth(),getHeight()-newGame.getAscent());
		
		//special effects
		createEffect();
		/*
		//start game
		addMouseListeners();
		removeAll();
		playGame();*/
	}

	/**
	 * make certain image move randomly on the screen
	 */
	private void createEffect() {
		//draw circle
		GImage circle=new GImage("circle.png");
		double ratio=rgen.nextDouble();
		circle.scale(ratio,ratio);
		int x=rgen.nextInt(getWidth());
		int y=rgen.nextInt(getHeight());
		add(circle,x,y);
		//get velocity and move
		while(circle.getX()>getWidth() || circle.getY()>getHeight()) {
			int vx=rgen.nextInt(2,6);
			int vy=rgen.nextInt(2,6);
			circle.move(vx, vy);
			pause(DELAY);
		}
		remove(circle);
	}
	
}
