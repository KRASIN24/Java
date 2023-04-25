import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

public class Board extends JPanel {
	
	
	int framed = 0;
	private Point lastPoint;
	int width = 1024, height = 1024;
	private BufferedImage image;
	boolean[] matrixLetter;
	
	
	int minX=-1, minY, maxX, maxY;

    public Board() {
    	image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                lastPoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {

            public void mouseDragged(MouseEvent e) {
            	Graphics g = image.getGraphics();
                g.setColor(Color.BLACK);
                g.drawLine(lastPoint.x, lastPoint.y, e.getX(), e.getY());
                lastPoint = e.getPoint();
                
                //System.out.println(lastPoint.x + "    " + lastPoint.y);
                
              if(minX==-1) {
            	  minX=lastPoint.x;
            	  minY=lastPoint.y;
            	  maxX=lastPoint.x;
            	  maxY=lastPoint.y;
              }
            	  
                
             if(minX>lastPoint.x)
            	 minX=lastPoint.x;
             if(minY>lastPoint.y)
            	 minY=lastPoint.y;
             
             if(maxX<lastPoint.x)
            	 maxX=lastPoint.x;
             if(maxY<lastPoint.y)
            	 maxY=lastPoint.y;
             
             repaint();
            }
        });
    }
    
    public void framing() {
    	if (lastPoint != null) {
    		Graphics g = image.getGraphics();
    		framed = 1;
    		//Graphics g = getGraphics();
    		g.setColor(Color.RED);
            g.drawLine(minX, minY, maxX, minY);
            g.drawLine(maxX, minY, maxX, maxY);
            g.drawLine(maxX, maxY, minX, maxY);
            g.drawLine(minX, maxY, minX, minY);
            repaint();
    		}
    }
    
    public boolean[] getBooleans (int col, int row) {
    	matrixLetter = new boolean[col*row];
    	if(framed == 0) {
    	int lenghtX=maxX-minX;
    	int lenghtY=maxY-minY;
    	
    	for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				// i = y   j = x
				
				boolean tick=false;


				for (int a =  minY+ lenghtY*i/col; a < minY + lenghtY*(i+1)/col; a++) {
			            for (int b = minX+ lenghtX*j/row; b < minX+ lenghtX*(j+1)/row; b++) {
			            	//a = y  b = x
			            		
			                if (image.getRGB(b, a) != 0) {
			                	tick=true;
			                    break;
			                }
			            }
			            
			            if (tick==true) {
			            	break;
			            }
			        }
				 if (tick==true) {
				 matrixLetter[i*col+j] = true;
				 }
				 System.out.println(i+  " " +j +" :" +matrixLetter[i*col+j]);

			}
			
		}
    	repaint();
    	 System.out.println();
    	}
    	return matrixLetter;
    	
    }
    
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(image, 0, 0, Color.WHITE, this);
        
    }
    
    public void clear() {
    	minX=-1;
    	framed = 0;
    	image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    	repaint();
    }

	public void grid(int col, int row) {
		Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        int pad = 10;
        maxX+=pad;
		maxY+=pad;
		minX-=pad;
		minY-=pad;
		
		int lenghtX=maxX-minX;
    	int lenghtY=maxY-minY;
		
		for (int i = 0; i < col; i++) {
			int tempRow= minY + lenghtY*i/col;
			for (int j = 0; j < row; j++) {
				int tempCol=minX + lenghtX*j/row;
				
				g.drawLine(tempCol, tempRow, tempCol+lenghtX/col, tempRow);
				g.drawLine(tempCol, tempRow, tempCol, tempRow+lenghtY/row);
			}
		}
	}
	

}
