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

public class myAppletFurthest extends Applet

{
	int[] farColor = {255,153,0};
	int[] closeColor ={0, 255 , 0};
	double delta = 0.15;
	int numberOfPoints = 35;
	int screenSize = 500;
	
	public void paint(Graphics g)
	{
		Point[] pointArray = new Point[numberOfPoints];
		for (int i = 0; i < numberOfPoints; i++){
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
				
				 for(int k = 1; k< numberOfPoints; k++){
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
				 double maxDistHere =pointArray[0].distance(i, j);
				 double secondMaxDistHere =pointArray[1].distance(i, j);
				 if(maxDistHere < secondMaxDistHere){
					 double temp = maxDistHere;
					 maxDistHere = secondMaxDistHere;
					 secondMaxDistHere = temp;
				 }
				 for(int k = 1; k< numberOfPoints; k++){
					 double distanceTo = pointArray[k].distance(i, j);
					 
					 if(distanceTo > maxDistHere ){
						 secondMaxDistHere = maxDistHere;
						 maxDistHere = distanceTo;
					 }
					 if((maxDistHere > distanceTo) && (distanceTo > secondMaxDistHere)){
						 secondMaxDistHere = distanceTo;
					 } 
				 }
				 double p = maxDistHere/maxDist;
				 if(p>=1){
					 System.out.println("p ="+p);
					 p = 1;
				 }
				 if(maxDistHere-secondMaxDistHere <= delta){
					 g.setColor(Color.black);
					 g.drawLine(i, j, i, j);
				 }
				 else{
					 for (int l= 0; l<3; l++){
						  color[l] =(int) (p*p*p*p*closeColor[l]+ (1-p)*(1-p)*(1-p)*(1-p)*farColor[l]);
	 
					  }
					 Color c = new Color(color[0], color[1], color[2]);
					 g.setColor(c);
					 g.drawLine(i, j, i, j);
				 }	   
			}	
		}
		for(int i = 0; i < numberOfPoints; i++){
			int xCoor = pointArray[i].x;
			int yCoor = pointArray[i].y;
			g.setColor(Color.black);
			g.fillOval(xCoor, yCoor, 5, 5);
		}
    }
}