package com.lyc.test;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 */
public class Test7 extends JFrame implements MouseListener {
    /**
     * 
     */
    
    private static final long serialVersionUID = 1L;
    public Test7() {
        this.setSize(1105, 947);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(this);
        NewPanel p = new NewPanel();
        this.getContentPane().add(p); // 将面板添加到JFrame上
        

               

        this.setVisible(true);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        this.setTitle("当前坐标：" + p.x + "," + p.y);
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    public static void main(String[] args) {
        new Test7();
    }
    
    class NewPanel extends JPanel {
	  public NewPanel() {

	  }

	  public void paintComponent(Graphics g) {
	      int x = 0, y = 0;
	      ImageIcon icon = new ImageIcon("new_map.png");		// 003.jpg是测试图片在项目的根目录下
	      g.drawImage(icon.getImage(), x, y, 1105, 947, this);	// 图片会自动缩放
//	      g.drawImage(icon.getImage(), x, y,this);			//图片不会自动缩放
	  }
	 }
}
