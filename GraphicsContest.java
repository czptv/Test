/*
 * File: GraphicsContest.java
 * --------------------------
 */

import acm.program.*;
import acm.graphics.*;

public class GraphicsContest extends GraphicsProgram {

	public void run() {
		GImage face=new GImage("FengMian.jpg");
		face.scale(0.4,0.4);
		add(face,-300,0);
	}

}
