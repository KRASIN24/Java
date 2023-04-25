import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Canvas extends JPanel {
	boolean framed = false;
	private Point lastPoint;
	int width = 1024, height = 1024;
	private BufferedImage image;
	Graphics g;
	int minX=-1, minY, maxX, maxY;
	
	public Canvas() {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    	
    	addMouseListener(new MouseAdapter() {
    		public void mousePresed(MouseEvent e) {
    			
    			lastPoint = e.getPoint();
    		}
		});
    	
    	addMouseMotionListener(new MouseAdapter() {

            public void mouseDragged(MouseEvent e) {
            	g = image.getGraphics();
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
             
             
            }
        });
    }
	
	public void framing() {
    	if (lastPoint != null) {
          
    		if(framed == false) {
    		int pad = 10;
    		maxX+=pad;
    		maxY+=pad;
    		minX-=pad;
    		minY-=pad;
    		framed = true;
    		}
    		//Graphics g = getGraphics();
    		g.setColor(Color.RED);
            g.drawLine(minX, minY, maxX, minY);
            g.drawLine(maxX, minY, maxX, maxY);
            g.drawLine(maxX, maxY, minX, maxY);
            g.drawLine(minX, maxY, minX, minY);
    }
    }
	
	public boolean[] getBooleans (int col, int row) {
		boolean[] matrixLetter = new boolean[col*row];
		
		return matrixLetter;
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        
    }
	
}