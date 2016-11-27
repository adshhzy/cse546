/*======================================================================
Created by Steven Zhao 
Wustl Class of 2015
*///====================================================================
package Voronoi;
import java.applet.Applet; 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.*;

public class myAppletSegment extends Applet {
	int[] farColor = {255,153,0};
	int[] closeColor ={0, 255 , 0};
	double delta = 0.25;
	int numOfSegments = 10;
	int screenSize = 500;
	
	
	public void paint(Graphics g)
	{
		Line2D.Double[] segmentArray = new Line2D.Double[numOfSegments];
		//Here we generate numOfSegments non-intersected line segments.
		for (int i = 0; i < numOfSegments; i++){
			double x1 = new java.util.Random().nextInt(screenSize);
			double y1 = new java.util.Random().nextInt(screenSize);
			double x2 = new java.util.Random().nextInt(screenSize);
			double y2 = new java.util.Random().nextInt(screenSize);
			Line2D.Double thisSegment =new Line2D.Double(x1, y1, x2, y2);
			segmentArray[i] = thisSegment;
			while (i >= 1 ){
				int counter = 0;
				for( int j = 0; j < i; j++){
					if(thisSegment.intersectsLine(segmentArray[j])){
						 x1 = new java.util.Random().nextInt(screenSize);
						 y1 = new java.util.Random().nextInt(screenSize);
						 x2 = new java.util.Random().nextInt(screenSize);
						 y2 = new java.util.Random().nextInt(screenSize);
						 thisSegment =new Line2D.Double(x1, y1, x2, y2);
						 segmentArray[i] = thisSegment;
						 break;
					}
					if(j == i-1){
						counter = 1;
					}
				}
				if (counter == 1){
					break;
				}
			}
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			g2.drawLine((int)segmentArray[i].getX1(), (int)segmentArray[i].getY1(), (int)segmentArray[i].getX2(), (int)segmentArray[i].getY2());
		}	
		double maxDist =segmentArray[0].ptSegDist(0, 0);
		//Color c = Color.blue;
		for (int i = 0; i < screenSize; i++ )
		{
			 
			for (int j = 0; j < screenSize; j++)
			{
				
				 for(int k = 1; k< numOfSegments; k++){
					 double tempDistance = segmentArray[k].ptSegDist(i, j);
 
					  if(maxDist < tempDistance){
						  maxDist = tempDistance;
					  }
				 }

			}
			
		}
		
		for (int i = 0; i < screenSize; i++ )
		{
			 
			for (int j = 0; j < screenSize; j++)
			{
				 int [] color = new int[3];
				 double minDistHere =segmentArray[0].ptSegDist(i, j);
				 double secondMinDistHere =segmentArray[1].ptSegDist(i, j);
				 if(minDistHere > secondMinDistHere){
					 double temp = minDistHere;
					 minDistHere = secondMinDistHere;
					 secondMinDistHere = temp;
				 }
				 for(int k = 1; k< numOfSegments; k++){
					 double distanceTo = segmentArray[k].ptSegDist(i, j);
					 
					 if(distanceTo < minDistHere ){
						 secondMinDistHere = minDistHere;
						 minDistHere = distanceTo;
					 }
					 if((minDistHere < distanceTo) && (distanceTo < secondMinDistHere)){
						 secondMinDistHere = distanceTo;
					 } 
				 }
				 double p = minDistHere/maxDist;
				 if(secondMinDistHere-minDistHere <= delta){
					 g.setColor(Color.black);
					 g.drawLine(i, j, i, j);
				 }
				 else{
					 for (int l= 0; l<3; l++){
						  color[l] =(int) ((1-p)*(1-p)*(1-p)*(1-p)*(1-p)*(1-p)*closeColor[l]+p*p*p*p*p*p*farColor[l]);
	 
					  }
					 Color c = new Color(color[0], color[1], color[2]);
					 g.setColor(c);
					 g.drawLine(i, j, i, j);
				 }	   
			}	
		}
		
		for(int i = 0; i < numOfSegments; i++){
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(4));
			g2.drawLine((int)segmentArray[i].getX1(), (int)segmentArray[i].getY1(), (int)segmentArray[i].getX2(), (int)segmentArray[i].getY2());
		}
    }
}
