/*
 * File: GraphicsContest.java
 * --------------------------
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.event.*;
import java.lang.Math;

public class GraphicsContest extends GraphicsProgram {

	/**
	 * constants
	 */
	private static final int DELAY=1;
	private static final int PLAYER_LIFE=100;
	private static final int NPC_LIFE=300;
	private static final int BIAO_DAMAGE=30;
	private static final int AIRFLOW_DAMAGE=20;
	private static final int NPC_ATTACK=25;
	
	/**
	 * instant variables
	 */
	private String playerName;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double newGameWidth;
	private double newGameAscent;
	private boolean firstClicked=false;
	private boolean firstStageME;
	private GImage c1;
	private GImage c2;
	private GImage c3;
	private GImage c4;
	private GImage c5;
	private GImage c6;
	private GImage c7;
	private GImage c8;
	private GImage c9;
	private GImage c10;
	
	private GImage plT1;
	private boolean up1;
	private boolean down1;
	private boolean left1;
	private boolean right1;
	private double plX;
	private double plY;
	private boolean task1=true;
	private boolean task1GoingOn;
	
	private boolean task2=true;
	private GImage npc;
	private GImage plT2;
	private double npcX;
	private double npcY;
	private double plT2X;
	private double plT2Y;
	private GImage biao;
	private GImage airflow;
	private int plLife;
	private int npcLife;
	private boolean task2GoingOn;
	private String playerDirection;
	private boolean left2;
	private boolean right2;
	
	public void run() {
		//the image at the first display
		GImage face=new GImage("FengMian.jpg");
		face.scale(0.43,0.43);
		add(face,-300,0);
		firstStageME=true;
		
		//new game
		GLabel newGame= new GLabel("New Game");
		newGame.setFont("Times-20");
		newGameWidth=newGame.getWidth();
		newGameAscent=newGame.getAscent();
		add(newGame,(getWidth()-newGame.getWidth())/2.0,getHeight()-2*newGame.getAscent());
		
		//special effects
		createEffect(newGame);
		
		//start game
		playGame();
	}

	/**
	 * make certain image move randomly on the screen
	 */
	private void createEffect(GLabel newGame) {
		//draw circles on the screen
		c1=drawCircle();
		c2=drawCircle();
		c3=drawCircle();
		c4=drawCircle();
		c5=drawCircle();
		c6=drawCircle();
		c7=drawCircle();
		c8=drawCircle();
		c9=drawCircle();
		c10=drawCircle();
		//get velocity and move
		addMouseListeners();
		while(!firstClicked) {
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
		double x;
		double y;
		boolean side=rgen.nextBoolean();
		if(side) {
			x=rgen.nextDouble(-circle.getWidth(),getWidth()-circle.getWidth());
			y=-circle.getHeight();
		} else {
			x=-circle.getWidth();
			y=rgen.nextDouble(-circle.getHeight(),getHeight()-circle.getHeight());
		}
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
	
	/**
	 * click to start the game 
	 */
	public void mouseClicked(MouseEvent e) {
		if (firstStageME==true) {
			double x=(getWidth()-newGameWidth)/2.0;
			double y=getHeight()-3*newGameAscent;
			boolean xContain=e.getX()>x && e.getX()<x+newGameWidth;
			boolean yContain=e.getY()>y && e.getY()<y+2*newGameAscent;
			if(xContain && yContain) {
				removeAll();
				firstClicked=true;
				firstStageME=false;
		}
		}
	}
	
	/**
	 * play the game
	 */
	private void playGame() {
		/*goFirstTask();
		if (task1) {
			goSecondTask();
		}
		if (task1 && task2) {
			goThirdScene();
		}
		if(!task1 || !task2) {
			goDie();
		}*/
		winGame();
	}
	
	/**
	 * get and finish the first task
	 */
	private void goFirstTask() {
		task1GoingOn=true;
		getFirstTask();
		arriveAtMansion();
		if (task1) {
			meetMaid();
		}
		task2GoingOn=false;
	}
	
	/**
	 * get the first task on the street
	 */
	private void getFirstTask() {
		meetGirl();
		talkToGirl();
	}
	
	/**
	 * the girl who hand over the first task appear
	 */
	private void meetGirl() {
		//add background
		addBgT1();
		//add NPC
		GImage npc1=new GImage("ChunYangBack.png");
		npc1.scale(0.2,0.2);
		add(npc1,getWidth()/2+50,getHeight()/2);
		waitForClick();
		//add scroll for prompt
		GImage scroll=new GImage("scroll.png");
		scroll.scale(0.3,0.3);
		add(scroll,50,100);
		//add prompt
		double x=scroll.getX()+35;
		double yInit=scroll.getY()+70;
		double lineHeight=30;
		createPrompt("There is a girl on",x,yInit);
		createPrompt("the street. Go and",x,yInit+lineHeight);
		createPrompt("ask her if she needs",x,yInit+2*lineHeight); 
		createPrompt("some help.",x,yInit+3*lineHeight);
		createPrompt("Click to continue.",x,yInit+5*lineHeight);
		
		waitForClick();
		removeAll();		
	}
	
	private void talkToGirl() {
		//add bg
		addBgT1();
		//add NPC
		GImage npc1=new GImage("NPCEastFront.png");
		npc1.scale(0.6,0.6);
		add(npc1,getWidth()/2,100);
		//add scroll
		GImage scroll=new GImage("scroll.png");
		scroll.scale(0.47,0.52);
		add(scroll,20,15);
		//add prompt
		double x=scroll.getX()+50;
		double yInit=scroll.getY()+50;
		double lineHeight=30;
		createPrompt("I am the emissary of ChunYang",x,yInit+lineHeight);
		createPrompt("Branch. We're now plotting the",x,yInit+2*lineHeight);
		createPrompt("death of LuShan's military",x,yInit+3*lineHeight);
		createPrompt("minister, Yuan. Can you help",x,yInit+4*lineHeight);
		createPrompt("deliver the poison to Yuan's",x,yInit+5*lineHeight);
		createPrompt("maid? She is one of our spies",x,yInit+6*lineHeight);
		createPrompt("and she will wait for you in",x,yInit+7*lineHeight);
		createPrompt("Yuan's mansion. To arrive at",x,yInit+8*lineHeight);
		createPrompt("Yuan's mantion, be careful not",x,yInit+9*lineHeight);
		createPrompt("to fall off the suspension",x,yInit+10*lineHeight);
		createPrompt("bridge in front of it.",x,yInit+11*lineHeight);
		
		waitForClick();
		removeAll();
	}
	
	/**
	 * add the background for get task 1
	 */
	private void addBgT1() {
		GImage bg=new GImage("RenWu2.jpg");
		bg.scale(0.75,0.75);
		add(bg,0,0);		
	}
	/**
	 * create prompt
	 * @param ppt: the string in the prompt
	 * @param x: x parameter of prompt
	 * @param y: y parameter of prompt
	 */
	
	private void createPrompt(String ppt, double x, double y) {
		GLabel prompt=new GLabel(ppt);
		prompt.setFont("Times-16-Bold");
		add(prompt,x,y);
	}
	
	private void arriveAtMansion() {
		task1SetUp();
		//make sure the player doesn't fall off the bridge
		double footX=plX+plT1.getWidth()/2.0;
		double footY=plY+plT1.getHeight();
		
		//divide the area on the background to examine whether player has fall off the bridge
		GPolygon land=drawLand();
		add(land);
		land.setVisible(false);
		GPolygon upBri=drawUpBridge();
		add(upBri);
		upBri.setVisible(false);
		GPolygon midBri=drawMidBridge();
		add(midBri);
		midBri.setVisible(false);
		GPolygon downBri=drawDownBridge();
		add(downBri);
		downBri.setVisible(false);
		GPolygon arrival=arrive();
		add(arrival);
		arrival.setVisible(false);
		
		//move player
		addKeyListeners();
		while(true){
			pause(DELAY*10);
			if(land.contains(footX,footY)) {
				moveOnLand();
			} else if(upBri.contains(footX,footY)) {
				moveOnUpBri();
			} else if(midBri.contains(footX,footY)) {
				moveOnMidBri();
			} else if(downBri.contains(footX,footY)) {
				moveOnDownBri();
			} else if(arrival.contains(footX,footY)) {
				removeAll();
				break;
			} else {
				task1=false;
				removeAll();
				break;
			}
			footX=plX+plT1.getWidth()/2.0;
			footY=plY+plT1.getHeight();
		}
	}
	
	private void task1SetUp() {
		//add bg
		GImage bg=new GImage("Task1_1.jpg");
		bg.scale(0.5,0.5);
		add(bg,0,0);
		//zoom bg
		for(int i=0;i<80;i++) {
			remove(bg);
			bg=new GImage("Task1_1.jpg");
			double scale=0.5*Math.pow(1.01, i);
			bg.scale(scale,scale);
			add(bg,-10*i,-5.5*i);
			pause(DELAY*20);
		}
		//add player
		plX=600;
		plY=420;
		plT1=new GImage("CharacterLeft.png");
		plT1.scale(0.05,0.05);
		add(plT1,plX,plY);
	}
	
	private void moveOnLand() {
		if(up1 || down1 || left1 || right1) {
			remove(plT1);
			if(up1) {
				plT1=new GImage("CharacterBack.png");
				plT1.scale(0.05,0.05);
				add(plT1,plX,plY);
				plY-=0.5;
			} else if(down1){
				plT1=new GImage("CharacterFront.png");
				plT1.scale(0.05,0.05);
				add(plT1,plX,plY);
				plY+=0.5;
			} else if(left1) {
				plT1=new GImage("CharacterLeft.png");
				plT1.scale(0.05,0.05);
				add(plT1,plX,plY);
				plX-=1;
			} else if(right1) {
				plT1=new GImage("CharacterRight.png");
				plT1.scale(0.05,0.05);
				add(plT1,plX,plY);
				plX+=1;
			}			
		}
	}
	
	private void moveOnUpBri() {
		if(up1 || down1 || left1 || right1) {
			remove(plT1);
			if(up1) {
				plT1=new GImage("CharacterBack.png");
				plT1.scale(0.047,0.047);
				add(plT1,plX,plY);
				plY-=0.3;
				plX+=0.6;
			} else if(down1){
				plT1=new GImage("CharacterFront.png");
				plT1.scale(0.047,0.047);
				add(plT1,plX,plY);
				plY+=0.3;
				plX-=0.6;
			} else if(left1) {
				plT1=new GImage("CharacterLeft.png");
				plT1.scale(0.047,0.047);
				add(plT1,plX,plY);
				plX-=0.8;
				plY-=0.6;
			} else if(right1) {
				plT1=new GImage("CharacterRight.png");
				plT1.scale(0.047,0.047);
				add(plT1,plX,plY);
				plX+=0.8;
				plY+=0.6;
			}			
		}
	}
	
	private void moveOnMidBri() {
		if(up1 || down1 || left1 || right1) {
			remove(plT1);
			if(up1) {
				plT1=new GImage("CharacterBack.png");
				plT1.scale(0.044,0.044);
				add(plT1,plX,plY);
				plY-=0.3;
				plX+=0.8;
			} else if(down1){
				plT1=new GImage("CharacterFront.png");
				plT1.scale(0.044,0.044);
				add(plT1,plX,plY);
				plY+=0.3;
				plX-=0.8;
			} else if(left1) {
				plT1=new GImage("CharacterLeft.png");
				plT1.scale(0.044,0.044);
				add(plT1,plX,plY);
				plX-=0.8;
				plY-=0.2;
			} else if(right1) {
				plT1=new GImage("CharacterRight.png");
				plT1.scale(0.044,0.044);
				add(plT1,plX,plY);
				plX+=0.8;
				plY+=0.2;
			}			
		}
	}
	
	private void moveOnDownBri() {
		if(up1 || down1 || left1 || right1) {
			remove(plT1);
			if(up1) {
				plT1=new GImage("CharacterBack.png");
				plT1.scale(0.041,0.041);
				add(plT1,plX,plY);
				plY-=0.2;
				plX+=0.5;
			} else if(down1){
				plT1=new GImage("CharacterFront.png");
				plT1.scale(0.041,0.041);
				add(plT1,plX,plY);
				plY+=0.2;
				plX-=0.5;
			} else if(left1) {
				plT1=new GImage("CharacterLeft.png");
				plT1.scale(0.041,0.041);
				add(plT1,plX,plY);
				plX-=0.7;
				plY+=0.07;
			} else if(right1) {
				plT1=new GImage("CharacterRight.png");
				plT1.scale(0.041,0.041);
				add(plT1,plX,plY);
				plX+=0.7;
				plY-=0.07;
			}			
		}
	}
	/**
	 * the area of land in task 1
	 * @return land
	 */
	private GPolygon drawLand() {
		GPolygon land=new GPolygon();
		land.addVertex(getWidth(),320);
		land.addVertex(getWidth(), getHeight());
		land.addVertex(435, getHeight());
		land.addVertex(365, getHeight()-60);
		land.addVertex(435, getHeight()-55);
		land.addVertex(480, getHeight()-90);
		land.addVertex(getWidth()-80, 320);
		return land;
	}
	
	/**
	 * the area of up bridge in task 1
	 * @return up bridge
	 */
	private GPolygon drawUpBridge() {
		GPolygon brd=new GPolygon();
		brd.addVertex(435, getHeight()-55);
		brd.addVertex(480, getHeight()-90);
		brd.addVertex(370, getHeight()-155);
		brd.addVertex(320, getHeight()-150);
		brd.addVertex(360, getHeight()-120);
		return brd;
	}
	
	/**
	 * the area of middle bridge in task 1
	 * @return middle bridge
	 */
	private GPolygon drawMidBridge() {
		GPolygon brd=new GPolygon();
		brd.addVertex(370, getHeight()-155);
		brd.addVertex(320, getHeight()-150);
		brd.addVertex(220, getHeight()-165);
		brd.addVertex(260, getHeight()-185);
		brd.addVertex(320, getHeight()-175);
		return brd;
	}
	
	/**
	 * the area of down bridge in task 1
	 * @return down bridge
	 */
	private GPolygon drawDownBridge() {
		GPolygon brd=new GPolygon();
		brd.addVertex(220, getHeight()-165);
		brd.addVertex(260, getHeight()-185);
		brd.addVertex(180, getHeight()-180);
		brd.addVertex(110, getHeight()-160);
		return brd;
	}
	
	/**
	 * examine that the player has arrive at the mansion without falling off the bridge
	 * @return arrival area
	 */
	private GPolygon arrive() {
		GPolygon arrive=new GPolygon();
		arrive.addVertex(180, getHeight()-180);
		arrive.addVertex(110, getHeight()-160);		
		arrive.addVertex(50, getHeight()-160);
		arrive.addVertex(110, getHeight()-220);
		return arrive;
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(task1GoingOn) {
			switch(keyCode) { 
			case KeyEvent.VK_UP:
				up1=true;
				break;
			case KeyEvent.VK_DOWN:
				down1=true;
				break;
			case KeyEvent.VK_LEFT:
				left1=true;
				break;
			case KeyEvent.VK_RIGHT :
				right1=true;
				break;
			}
		} else if(task2GoingOn) {
			switch(keyCode) { 
			case KeyEvent.VK_LEFT:
				playerDirection="Left";
				left2=true;
				movePlayer();
			case KeyEvent.VK_RIGHT :
				playerDirection="Right";
				right2=true;
				movePlayer();
			case 65: //A
				plDaZhao(playerDirection);
			case KeyEvent.VK_S: //S
				plXiaoZhao(playerDirection);
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(task1GoingOn) {
			switch(keyCode) { 
			case KeyEvent.VK_UP:
				up1=false;
				break;
			case KeyEvent.VK_DOWN:
				down1=false;
				break;
			case KeyEvent.VK_LEFT:
				left1=false;
				break;
			case KeyEvent.VK_RIGHT :
				right1=false;
				break;
			}
		} else if(task2GoingOn) {
			switch(keyCode) { 
			case KeyEvent.VK_LEFT:
				left2=false;
			case KeyEvent.VK_RIGHT :
				right2=false;
			}
		}
	}
		
	
	private void meetMaid() {
		//draw bg
		GImage bg=new GImage("Task1_2.jpg");
		bg.scale(0.6,0.6);
		add(bg);
		//draw character
		GImage npc=new GImage("NanZhaoCe.png");
		npc.scale(0.65,0.65);
		add(npc,getWidth()/2-50,getHeight()/4+50);
		GImage pl=new GImage("CharacterLeft.png");
		pl.scale(0.35,0.35);
		add(pl,getWidth()/2+150,getHeight()/4+40);
		waitForClick();
		//add scroll
		GImage scroll=new GImage("Scroll.png");
		scroll.scale(0.4,0.4);
		add(scroll,30,60);
		//add prompt
		double x=scroll.getX()+40;
		double yInit=scroll.getY()+80;
		double lineHeight=30;
		createPrompt("Thank you for delivering",x,yInit);
		createPrompt("the poison, but can you",x,yInit+lineHeight);
		createPrompt("do me another favor? Yuan",x,yInit+2*lineHeight);
		createPrompt("stored most secret ",x,yInit+3*lineHeight);
		createPrompt("documents in the Liuxie",x,yInit+4*lineHeight);
		createPrompt("Pavilion. Can you deliver",x,yInit+5*lineHeight);
		createPrompt("the third scroll on the",x,yInit+6*lineHeight);
		createPrompt("eastern shelf to our head?",x,yInit+7*lineHeight);
		
		waitForClick();
		removeAll();
	}
	
	private void goSecondTask() {
		task2GoingOn=true;
		playerDirection="Left";
		plLife=PLAYER_LIFE;
		npcLife=NPC_LIFE;
		changeBg1();
		changeBg2();
		changeBg3();
		fight();
		task2GoingOn=false;
		if (plLife>0) {
			task2=true;
		} else if(npcLife>0) {
			task2=false;
		}
	}
	
	private void changeBg1() {
		GImage bg1=new GImage("Task2_1.jpg");
		bg1.scale(0.8,0.8);
		add(bg1);
		GImage rain=new GImage("Rain.png");
		rain.scale(0.5,0.5);
		add(rain,1,-920);
		for(int i=0;i<330;i++) {
			bg1.move(-0.8, 0);
			rain.move(0,3);
			pause(DELAY*20);
		}
		removeAll();
		
	}
	
	private void changeBg2() {
		GImage bg2=new GImage("Task2_2Zoom.jpg");
		bg2.scale(0.85,0.85);
		add(bg2);
		GImage rain=new GImage("Rain.png");
		rain.scale(0.45,0.45);
		add(rain,0,-800);
		for(int i=0;i<200;i++) {
			bg2.scale(1.002,1.002);
			bg2.move(-1*0.3,0);
			rain.move(0,4);
			pause(DELAY*30);
		}
		removeAll();
	}
	
	private void changeBg3() {
		addBg2();
		waitForClick();
		drawNPCPlayer2_1();
		drawScroll2_1();
		addPrompt2_1();
		waitForClick();
		removeAll();
		addBg2();
		drawNPCPlayer2_1();
		drawScroll2_2();
		addPrompt2_2();
		waitForClick();
		removeAll();
	}
	
	private void addBg2() {
		GImage bg=new GImage("Task2_3.jpg");
		bg.scale(0.78,0.78);
		add(bg);
	}
	
	private void drawNPCPlayer2_1() {
		GImage npcInit=new GImage("TianCeRight.png");
		npcInit.scale(0.2,0.2);
		add(npcInit,getWidth()/2,getWidth()/3);
		GImage pl2=new GImage("CharacterLeft.png");
		pl2.scale(0.2,0.2);
		add(pl2,getWidth()/2+150,getWidth()/3);
	}
	
	private void drawScroll2_1() {
		GImage scroll=new GImage("Scroll.png");
		scroll.scale(0.4,0.2);
		add(scroll,40,180);
	}
	
	private void addPrompt2_1() {
		double x=90;
		double yInit=230;
		double lineHeight=30;
		createPrompt("You can never get the",x,yInit);
		createPrompt("scroll unless you beat",x,yInit+lineHeight);
		createPrompt("me. Come and fight with",x,yInit+2*lineHeight);
		createPrompt("me!",x,yInit+3*lineHeight);
	}
	
	private void drawScroll2_2() {
		GImage scroll=new GImage("Scroll.png");
		scroll.scale(0.4,0.3);
		add(scroll,40,120);
	}
	
	private void addPrompt2_2() {
		double x=85;
		double yInit=180;
		double lineHeight=30;
		createPrompt("Hint: Try the keys \"Left\",",x,yInit);
		createPrompt("\"Right\", \"Up\" & \"Down\"",x,yInit+lineHeight);
		createPrompt("for moving. Try \"S\" to",x,yInit+2*lineHeight);
		createPrompt("throw the hidden weapon",x,yInit+3*lineHeight);
		createPrompt("at your enemy and \"A\"",x,yInit+4*lineHeight);
		createPrompt("to use airflow against him.",x,yInit+5*lineHeight);
	}
	
	private void fight() {
		addBg2() ;
		drawNPC2_2();
		drawPlayer2_2();
		fightNPC2();
	}
	
	private void drawNPC2_2() {
		npc=new GImage("TianCeRight.png");
		npc.scale(0.2,0.2);
		add(npc,getWidth()/3,getWidth()/3);
		npcX=npc.getX();
		npcY=npc.getY();
	}
	
	private void drawPlayer2_2() {
		plT2=new GImage("CharacterLeft.png");
		plT2.scale(0.2,0.2);
		add(plT2,getWidth()/2+50,getWidth()/3);
		plT2X=plT2.getX();
		plT2Y=plT2.getY();
	}
	
	private void fightNPC2() {
		addKeyListeners();
		while (plLife>0 && npcLife>0) {
			int step=getStep();
			String direction=moveNPC(step);
			boolean attack=rgen.nextBoolean();
			if (attack) {
				fightNPCXiaoZhao(direction);
			} else {
				defendNPC(direction);
			}
			pause(100);
		}
	}
	
	private String getNPCDirection() {
		String direction;
		if(npc.getX()>plT2.getX()) {
			direction="Left";
		} else {
			direction="Right";
		}
		return direction;
	}
	
	private int getStep() {
		boolean left=rgen.nextBoolean();
		int stepAbs=rgen.nextInt(0,80);
		int step;
		if(left) {
			step=-stepAbs;
		} else {
			step=stepAbs;
		}
		return step;
	}
	
	/**
	 * NPC move
	 * @param step
	 */
	private String moveNPC(int step) {
		String direction=getNPCDirection();
		//draw NPC
		remove(npc);
		npc=new GImage("TianCe"+direction+".png");
		npc.scale(0.2,0.2);
		add(npc,npcX,npcY);
		boolean hitWall=false;
		int oneStep=decideDirection(step);
		for(int i=0;i<Math.abs(step);i++) {
			if(!hitWall) {
				npc.move(oneStep,0);
				npcX+=oneStep;
			} else {
				npc.move(-oneStep,0);
				npcX-=oneStep;
			}
			if(!hitWall) {
				hitWall=checkHitWall(npc);
			}
			pause(10);
		}
		return direction;
	}
	
	private int decideDirection(int step) {
		boolean moveToRight=(step>=0);
		int oneStep;
		if(moveToRight) {
			oneStep=1;
		} else {
			oneStep=-1;
		}
		return oneStep;
	}
	
	/**
	 * check if NPC hit wall or is too far away from the player
	 * @param role
	 * @return oppositeDirection
	 */
	private boolean checkHitWall(GImage role) {
		boolean oppositeDirection=false;
		boolean hitWall=role.getX()<=0 || role.getX()+role.getWidth()>=getWidth();
		boolean tooFarFromPlayer=npc.getX()<plT2.getX()-350 || npc.getX()>plT2.getX()+350;
		if(hitWall || tooFarFromPlayer) {
			oppositeDirection=true;
		}
		return oppositeDirection;
	}
	
	/**
	 * NPC attacks the player
	 * @param direction
	 */
	private void fightNPCXiaoZhao(String direction) {
		pause(100);
		fightNPCPrepare(direction);
		fightNPC(direction);
		fightNPCPrepare(direction);
	}
	
	private void fightNPCPrepare(String direction) {
		remove(npc);
		npc=new GImage("TianCeZhaoShi"+direction+".png");
		npc.scale(0.18,0.18);
		if(direction.equals("Left")) {
			add(npc,npcX-55,npcY);
		} else {
			add(npc,npcX,npcY);
		}
		pause(100);
	}
	
	private void fightNPC(String direction) {
		remove(npc);
		npc=new GImage("NPCX"+direction+".png");
		npc.scale(0.18,0.18);
		if(direction.equals("Left")) {
			add(npc,npcX-190,npcY);
		} else {
			add(npc,npcX,npcY);
		}
		pause(1000);
		hitPlayer(direction);
	}
	
	private void hitPlayer(String direction) {
		double weaponFrontX;
		double weaponFrontY=npc.getY()-npc.getHeight()/3.0;
		if (direction.equals("Left")) {
			weaponFrontX=npc.getX();
		} else {
			weaponFrontX=npc.getX()+npc.getWidth();
		}
		GObject status=getElementAt(weaponFrontX,weaponFrontY);
		if(status==plT2) {
			plLife-=NPC_ATTACK;
		}
	}
	/**
	 * NPC defend himself against the attack from player
	 * @param direction
	 */
	private void defendNPC(String direction) {
		pause(100);
		drawDefendNPCFrontCe(direction);
		drawDefendNPCFront(direction);
		drawDefendNPCFrontCe(direction);
	}
	
	private void drawDefendNPCFrontCe(String direction) {
		remove(npc);
		npc=new GImage("NPCCeFront"+direction+".png");
		npc.scale(0.15,0.15);
		add(npc,npcX,npcY);
		pause(100);
	}
	
	private void drawDefendNPCFront(String direction) {
		remove(npc);
		npc=new GImage("NPCDa"+direction+".png");
		npc.scale(0.29,0.29);
		if(direction.equals("Left")) {
			add(npc,npcX-10,npcY);
		} else {
			add(npc,npcX,npcY);
		}
		pause(600);
	}
	
	/**
	 * player use airflow to attack the NPC
	 * @param direction
	 */
	private void plDaZhao(String direction) {
		if(airflow==null) {
			//player
			pause(100);
			addDaZhaoCharacterBack();
			addDaZhaoPrepare(direction);
			addDaZhao(direction);
			addDaZhaoPrepare(direction);
			addDaZhaoCharacterBack();
			addPlayerAfterAttack(direction);
			//effect
			addDaZhaoEffect(direction);
		}

		
	}
	
	private void addDaZhaoCharacterBack() {
		remove(plT2);
		plT2=new GImage("CharacterBack.png");
		plT2.scale(0.2,0.2);
		add(plT2,plT2X,plT2Y);
		pause(100);
	}
	
	private void addDaZhaoPrepare(String direction) {
		remove(plT2);
		plT2=new GImage("DaZhaoPrepare"+direction+".png");
		plT2.scale(0.175,0.175);
		add(plT2,plT2X-35,plT2Y);
		pause(100);
	}
	
	private void addDaZhao(String direction) {
		remove(plT2);
		plT2=new GImage("DaZhao"+direction+".png");
		plT2.scale(0.175,0.175);
		add(plT2,plT2X-80,plT2Y);
		pause(100);
	}
	
	private void addPlayerAfterAttack(String direction) {
		remove(plT2);
		plT2=new GImage("Character"+direction+".png");
		plT2.scale(0.2,0.2);
		add(plT2,plT2X,plT2Y);
	}
	
	private void addDaZhaoEffect(String direction) {
		//add the effect
			airflow=new GImage("Yinbo"+direction+".png");
			airflow.scale(0.1,0.1);
			double airflowX=plT2X-100;
			double airflowY=plT2Y+plT2.getY()/3.0;
			add(airflow,airflowX,airflowY);
			airflowX+=airflow.getWidth();
			airflowY+=airflow.getHeight()/2.0;
		
			//move the airflow
			boolean hitNPC=false;
			for(int i=0;i<500;i++) {
				remove(airflow);
				airflow=new GImage("Yinbo"+direction+".png");
				airflow.scale(0.1*Math.pow(1.02,i),0.1*Math.pow(1.02,i));
				airflowX=getAirflowX(direction, i, airflowX);
				airflowY-=airflow.getHeight()/2.0;
				add(airflow,airflowX,airflowY);
				pause(DELAY*20);
				airflowX=retrieveAirflowX(direction, i, airflowX);
				airflowY+=airflow.getHeight()/2.0;
				//if the airflow hits the NPC
				if(!hitNPC) {
					hitNPC=checkAirflowHit(direction);
				} else {
					break;
				}
			}
			if(hitNPC) {
				remove(airflow);
				airflow=null;
			}
	}

	private double getAirflowX(String direction, int i, double airflowX) {
		if(direction.equals("Left")) {
			airflowX-=airflow.getWidth()+i+0.1*Math.pow(i,2);
		} else {
			airflowX-=airflow.getWidth()-i-0.1*Math.pow(i,2);
		}
		return airflowX;
	}
	
	private double retrieveAirflowX(String direction, int i, double airflowX) {
		if(direction.equals("Left")) {
			airflowX+=airflow.getWidth()+i+0.1*Math.pow(i,2);
		} else {
			airflowX+=airflow.getWidth()-i-0.1*Math.pow(i,2);
		}
		return airflowX;
	}
	
	private boolean checkAirflowHit(String direction) {
		double airflowFrontX;
		double airflowFrontY=airflow.getY()-airflow.getHeight()/2.0;;
		if (direction.equals("Left")) {
			airflowFrontX=airflow.getX();
		} else {
			airflowFrontX=airflow.getX()+airflow.getWidth();
		}
		GObject status=getElementAt(airflowFrontX,airflowFrontY);
		GImage defendPrepare1= new GImage("NPCCeFrontLeft.png");
		GImage defendPrepare2= new GImage("NPCCeFrontRight.png");
		GImage defend1=new GImage("NPCDaLeft.png");
		GImage defend2=new GImage("NPCDaRight.png");
		boolean npcStatus=!(status.equals(defendPrepare1)) || !(status.equals(defendPrepare2)) || !(status.equals(defend1)) || !(status.equals(defend2));
		if(npcStatus && status!=null) {
			npcLife-=AIRFLOW_DAMAGE;
		} else {
			remove(airflow);
			airflow=null;
		}
		return npcStatus;
	}
	/**
	 * player throw hidden weapon at his enemy
	 * @param direction
	 */
	private void plXiaoZhao(String direction) {
		if(biao==null) {
			pause(100);
			addPlayerXiaoZhaoGesture(direction);
			addXiaoZhaoEffect(direction);
		}
	}
	
	private void addPlayerXiaoZhaoGesture(String direction) {
		remove(plT2);
		plT2=new GImage("ZhaoShi"+direction+".png");
		plT2.scale(0.17,0.17);
		add(plT2,plT2X-50,plT2Y);
		pause(200);
	}
	
	private void addXiaoZhaoEffect(String direction) {
		//add effect
		GImage effect=new GImage("Biao"+direction+".png");
		effect.scale(0.15,0.15);
		add(effect,plT2X-120,plT2Y+plT2.getY()/6.0);
		//move effect
		boolean hitNPC=false;
		for (int i=0;i<400;i++) {
			if (direction.equals("Left")) {
				effect.move(-2-0.001*Math.pow(i, 2),0);
			} else {
				effect.move(2+0.001*Math.pow(i, 2),0);
			}
			pause(7);
			if(!hitNPC) {
				hitNPC=checkBiaoHit(direction);
			} else {
				break;
			}
		}
		if(hitNPC) {
			remove(biao);
			biao=null;
		}
	}
	
	private boolean checkBiaoHit(String direction) {
		double biaoFrontX;
		double biaoFrontY=biao.getY()-biao.getHeight()/2.0;;
		if (direction.equals("Left")) {
			biaoFrontX=biao.getX();
		} else {
			biaoFrontX=biao.getX()+biao.getWidth();
		}
		GObject status=getElementAt(biaoFrontX,biaoFrontY);
		GImage defendPrepare1= new GImage("NPCCeFrontLeft.png");
		GImage defendPrepare2= new GImage("NPCCeFrontRight.png");
		GImage defend1=new GImage("NPCDaLeft.png");
		GImage defend2=new GImage("NPCDaRight.png");
		boolean npcStatus=!(status.equals(defendPrepare1)) || !(status.equals(defendPrepare2)) || !(status.equals(defend1)) || !(status.equals(defend2));
		if(npcStatus && status!=null) {
			npcLife-=BIAO_DAMAGE;
		} else {
			remove(biao);
			biao=null;
		}
		return npcStatus;
	}
	
	private void movePlayer() {
		remove(plT2);
		if(left2) {
			addPlayerAfterAttack("Left");
			while(left2) {
				plT2.move(-1, 0);
				pause(10);
			}
		} else if(right2) {
			addPlayerAfterAttack("Right");
			while(right2) {
				plT2.move(1, 0);
				pause(10);
			}
		}
	}
	
	/**
	 * after player finish the first two task, they go to the third scene
	 */
	private void goThirdScene() {
		goToTheHead();
		meetHead();
		winGame();
	}
	
	private void goToTheHead() {
		//add bg
		GImage bg=new GImage("BianHua6.jpg");
		add(bg,0,0);
		//add rain
		GImage rain=new GImage("Rain.png");
		rain.scale(0.45,0.45);
		add(rain,0,-700);
		//add cloud
		GImage wind=new GImage("Wind.png");
		wind.scale(0.4,0.4);
		add(wind,-wind.getWidth()+getWidth(),0);
		//move bg
		for(int i=0;i<400;i++) {
			bg.move(-1,0);
			rain.move(0, 2);
			wind.move(1, 0);
			pause(20);
		}
		removeAll();
	}
	
	private void meetHead() {
		//add bg
		GImage bg=new GImage("BianHua4.jpg");
		bg.scale(0.27,0.27);
		add(bg);
		//add npc
		GImage npcT3=new GImage("NPCWestFront.png");
		npcT3.scale(0.5,0.5);
		add(npcT3,getWidth()/2,getHeight()/3);
		//add scroll
		GImage scroll=new GImage("Scroll.png");
		scroll.scale(0.4,0.34);
		add(scroll,80,100);
		//add prompt
		double x=scroll.getX()+50;
		double y=scroll.getY()+80;
		double lineHeight=30;
		createPrompt("Thank you for delivering",x,y);
		createPrompt("the scroll. You are now",x,y+lineHeight);
		createPrompt("qualified to join us.",x,y+2*lineHeight);
		createPrompt("Welcome to ChunYang.",x,y+3*lineHeight);
		createPrompt("Let's defeat LuShan",x,y+4*lineHeight);
		createPrompt("together in the future.",x,y+5*lineHeight);
	}
	
	private void goDie() {
		GImage bg=new GImage("BianHua8.jpg");
		bg.scale(0.8,0.8);
		add(bg);
		GImage gameOver=new GImage("GameOver.png");
		gameOver.scale(0.6,0.6);
		add(gameOver,(getWidth()-gameOver.getWidth())/2.0,(getHeight()-gameOver.getHeight())/2.0);
	}
	
	private void winGame() {
		waitForClick();
		removeAll();
		
		GImage bg=new GImage("BianHua3.jpg");
		bg.scale(0.46,0.46);
		add(bg);
	}
}


