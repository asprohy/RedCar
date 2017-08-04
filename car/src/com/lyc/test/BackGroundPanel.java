package com.lyc.test;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackGroundPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Image image = null;
	
	public BackGroundPanel(Image image) {
		this.image = image;
	}
	
	protected void paintCompoment(Graphics g) {
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}

}
