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

public class myApplet extends Applet

{
	int[] farColor = {255,153,0};
	int[] closeColor ={0, 255 , 0};
	double delta = 0.5;
	int numberOfPoint = 50;
	int screenSize = 500;
	public void paint(Graphics g)
	{
		Point[] pointArray = new Point[numberOfPoint];
		for (int i = 0; i < numberOfPoint; i++){
			int x = new java.util.Random().nextInt(screenSize);
			int y = new java.util.Random().nextInt(screenSize);
			pointArray[i] = new Point(x,y);
			//g.drawLine(pointArray[i].x, pointArray[i].y, pointArray[i].x, pointArray[i].y);
		}	
		double maxDist =pointArray[0].distance(0, 0);
		//Color c = Color.blue;  
		for (int i = 0; i < screenSize; i++ )
		{
			 
			for (int j = 0; j < screenSize; j++)
			{
				
				 for(int k = 1; k< numberOfPoint; k++){
					 double tempDistance = pointArray[k].distance(i, j);
 
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
				 double minDistHere =pointArray[0].distance(i, j);
				 double secondMinDistHere =pointArray[1].distance(i, j);
				 if(minDistHere > secondMinDistHere){
					 double temp = minDistHere;
					 minDistHere = secondMinDistHere;
					 secondMinDistHere = temp;
				 }
				 for(int k = 1; k< numberOfPoint; k++){
					 double distanceTo = pointArray[k].distance(i, j);
					 
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
						  color[l] =(int) ((1-p)*(1-p)*(1-p)*(1-p)*(1-p)*closeColor[l]+p*p*p*p*p*farColor[l]);
	 
					  }
					 Color c = new Color(color[0], color[1], color[2]);
					 g.setColor(c);
					 g.drawLine(i, j, i, j);
				 }	   
			}	
		}
		for(int i = 0; i < numberOfPoint; i++){
			int xCoor = pointArray[i].x;
			int yCoor = pointArray[i].y;
			g.setColor(Color.black);
			g.fillOval(xCoor, yCoor, 5, 5);
		}
    }
}