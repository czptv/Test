/*
 * File: GraphicsContest.java
 * --------------------------
 */

import acm.program.*;
import acm.graphics.*;

public class GraphicsContest extends GraphicsProgram {

	public void run() {
		GImage face=new GImage("FengMian.jpg");
		face.scale(0.5,0.5);
		add(face,-300,0);
	}

}
