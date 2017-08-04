package com.lyc.test;

import java.applet.*;
import java.util.*;
import java.awt.*;

public class Test extends Applet  
{  
	private Image bgImage;
	private Graphics bg;

	public void paint(Graphics g)  
	{  
		Date date=new Date();  
		g.drawString(date.toString(),20,50);  
		repaint(1000);  
	}
 
	public void update (Graphics g)
	{
		if (bgImage == null)
		{
			bgImage = createImage (this.getSize().width, this.getSize().height);
			bg = bgImage.getGraphics();
		}
		bg.setColor (getBackground ());
		bg.fillRect (0, 0, this.getSize().width, this.getSize().height);
		bg.setColor (getForeground());
		paint (bg);
		g.drawImage (bgImage, 0, 0, this);
		Graphics2D ga = (Graphics2D) g;
		ga.rotate(Math.toRadians(300), 0, 0);
	}

} 