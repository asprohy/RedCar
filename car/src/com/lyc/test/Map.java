package com.lyc.test;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Image image;
	public Map(){
		try {

			image = ImageIO.read(new File("D:/Workspaces/car/src/resource/new_map.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public void  drawMap(Graphics g) {

		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
		
	}
	
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, this);
	    }
}
