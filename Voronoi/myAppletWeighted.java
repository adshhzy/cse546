package Voronoi;
/*======================================================================
Created by Steven Zhao 
Wustl Class of 2015
*///====================================================================
import java.applet .Applet ;
import java.awt.*;
import java.lang.Object;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
public class myAppletWeighted extends Applet
 
{
	int[] farColor = {255,153,0};
	int[] closeColor ={0, 255 , 0};
	double delta = 0.15;
	int pointNumber = 30;
	int radiusOfCircleRange= 40;
	int screenSize = 400;
	public void paint(Graphics g) 
	{
		Point[] pointArray = new Point[pointNumber];
		for (int i = 0; i < pointNumber; i++){
			int x = new java.util.Random().nextInt(screenSize);
			int y = new java.util.Random().nextInt(screenSize);
			int r = new java.util.Random().nextInt(radiusOfCircleRange);
			pointArray[i] = new Point(x,y,r);
			//g.drawLine(pointArray[i].x, pointArray[i].y, pointArray[i].x, pointArray[i].y);
		}	
		double maxDist =pointArray[0].weightedDistance(0, 0);
		//Color c = Color.blue;
		for (int i = 0; i < screenSize; i++ )
		{
			for (int j = 0; j < screenSize; j++)
			{
				 for(int k = 1; k< pointNumber; k++){
					 double tempDistance = pointArray[k].weightedDistance(i, j);
 
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
				 double minDistHere =pointArray[0].weightedDistance(i, j);
				 double secondMinDistHere =pointArray[1].weightedDistance(i, j);
				 if(minDistHere > secondMinDistHere){
					 double temp = minDistHere;
					 minDistHere = secondMinDistHere;
					 secondMinDistHere = temp;
				 }

				 for(int k = 2; k< pointNumber; k++){
					 double distanceTo = pointArray[k].weightedDistance(i, j);

					 if(distanceTo < minDistHere ){
						 secondMinDistHere = minDistHere;
						 minDistHere = distanceTo;
					 }
					 if((minDistHere < distanceTo) && (distanceTo < secondMinDistHere)){
						 secondMinDistHere = distanceTo;
					 } 
				 }
				 if(minDistHere<=0){
					 
					 g.setColor(Color.yellow);
					 g.drawLine(i, j, i, j);
					 continue;
				 }
				 double p = (minDistHere/maxDist);
				 if(secondMinDistHere-minDistHere <= delta){
					 g.setColor(Color.black);
					 g.drawLine(i, j, i, j);
				 }
				 else{
					 for (int l= 0; l<3; l++){
						  color[l] =(int) ((1-p)*(1-p)*(1-p)*(1-p)*(1-p)*closeColor[l]+p*p*p*p*p*farColor[l]);
	 
					  }
					 Color c = new Color(color[0], color[1], color[2]);
					 g.setColor(c);
					 g.drawLine(i, j, i, j);
				 }	   
			}	
		}
		for(int i = 0; i < pointNumber; i++){
			int xCoor = pointArray[i].x;
			int yCoor = pointArray[i].y;
			g.setColor(Color.black);
			g.fillOval(xCoor, yCoor, 5, 5);
		}
    }
}