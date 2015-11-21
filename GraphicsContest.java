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
	private static final int DELAY=10;
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
		//playGame();
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
		GImage c6=drawCircle();
		GImage c7=drawCircle();
		GImage c8=drawCircle();
		GImage c9=drawCircle();
		GImage c10=drawCircle();
		//get velocity and move
		while(c1!=null || c2!=null || c3!=null || c4!=null || c5!=null || c6!=null || c7!=null || c8!=null || c9!=null || c10!=null) {
			moveCircle(c1);
			moveCircle(c2);
			moveCircle(c3);
			moveCircle(c4);
			moveCircle(c5);
			moveCircle(c6);
			moveCircle(c7);
			moveCircle(c8);
			moveCircle(c9);
			moveCircle(c10);
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
		if (circle!=null) {
			double vx=rgen.nextDouble();
			double vy=rgen.nextDouble();
			circle.move(vx, vy);
			pause(DELAY);
			if (circle.getX()>getWidth() || circle.getY()>getHeight()) {
				remove(circle);
			}		
		}
	}
	
	
	public void mouseClicked(MouseEvent e) {
		double x=(getWidth()-NEW_GAME_WIDTH)/2.0;
		double y=getHeight()-2*NEW_GAME_ASCENT;
		boolean xContain=e.getX()>x && e.getX()<x+NEW_GAME_WIDTH;
		boolean yContain=e.getY()>y && e.getY()<y+NEW_GAME_ASCENT;
		if(xContain && yContain) {
			removeAll();
		}
	}
}
