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
	private static final int DELAY=1;
	
	/**
	 * instant variables
	 */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double newGameWidth;
	private double newGameAscent;
	
	public void run() {
		//the image at the first display
		GImage face=new GImage("FengMian.jpg");
		face.scale(0.43,0.43);
		add(face,-300,0);
		
		//new game
		GLabel newGame= new GLabel("New Game");
		newGame.setFont("Times-20");
		newGameWidth=newGame.getWidth();
		newGameAscent=newGame.getAscent();
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
			c1=moveCircle(c1);
			c2=moveCircle(c2);
			c3=moveCircle(c3);
			c4=moveCircle(c4);
			c5=moveCircle(c5);
			c6=moveCircle(c6);
			c7=moveCircle(c7);
			c8=moveCircle(c8);
			c9=moveCircle(c9);
			c10=moveCircle(c10);
			addMouseListeners();
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
	private GImage moveCircle(GImage circle) {
		if (circle!=null) {
			double vx=rgen.nextDouble();
			double vy=rgen.nextDouble();
			circle.move(vx, vy);
			pause(DELAY);
			if (circle.getX()>getWidth() || circle.getY()>getHeight()) {
				remove(circle);
				circle=drawCircle();
			}		
		}
		return circle;
	}
	
	
	public void mouseClicked(MouseEvent e) {
		double x=(getWidth()-newGameWidth)/2.0;
		double y=getHeight()-2*newGameAscent;
		boolean xContain=e.getX()>x && e.getX()<x+newGameWidth;
		boolean yContain=e.getY()>y && e.getY()<y+newGameAscent;
		if(xContain && yContain) {
			removeAll();
		}
	}
}


