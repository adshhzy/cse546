package Voronoi;
/*======================================================================
Created by Steven Zhao 
Wustl Class of 2015
*///====================================================================
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.applet .Applet ;
import java.lang.Object;

import javax.swing.*;


public class AnAppletWithButtons extends Applet implements ActionListener {
	   TextField pointOrSegmentNum           = new TextField(10);
	   TextField Delta = new TextField(10);
	   TextField radiusOfCircleRangeText = new TextField(10);
	   Button button1, button2, button3, button4, button5;
	   
		int[] farColor = {255,153,0};
		int[] closeColor ={0, 255 , 0};
		double delta;
		int numberOfPoint;
		int radiusOfCircleRange;
		int screenSize = 500;
		private void addHorizontalLine(Color c)
		   {  
		      // Add a Canvas 10000 pixels wide but only 1 pixel high, which acts as
		      // a horizontal line to separate one group of components from the next.
		      Canvas line = new Canvas( );
		      line.setSize(10000,1);
		      line.setBackground(c);
		      add(line);
		   }
		
	private void addNewLine( ) 
		   {  
		      // Add a horizontal line in the background color. The line itself is
		      // invisible, but it serves to force the next Component onto a new line.
		      addHorizontalLine(getBackground( ));
		   }
	public void init() {
		button1 = new Button("Regular Voronoi Diagram");
		add(button1);
		button1.addActionListener(this);

		button2 = new Button("Weighted Voronoi Diagram");
		add(button2);
		button2.addActionListener(this);
		
		button3 = new Button("Power Voronoi Diagram");
		add(button3);
		button3.addActionListener(this);
		
		button4 = new Button("Furthest Voronoi Diagram");
		add(button4);
		button4.addActionListener(this);
		
		button5 = new Button("Segment Voronoi Diagram");
		add(button5);
		button5.addActionListener(this);
		
		
	      add(pointOrSegmentNum);
	      add(new Label("Number of Points or Segments"));
	      addNewLine( );
	      add(Delta);
	      add(new Label("Please input Delta Value"));
	      addNewLine( );
	      add(radiusOfCircleRangeText);
	      add(new Label("Range of Radius for Weighted/Power graph"));
	}

	public void actionPerformed(ActionEvent e) {
		 delta = atod(Delta.getText());
		 numberOfPoint = (int) atod(pointOrSegmentNum.getText());
		 radiusOfCircleRange = (int) atod(radiusOfCircleRangeText.getText());
		 
		if (e.getSource() == button1) 
		{
			this.paintVoronoi(getGraphics());
		}
		else if (e.getSource() == button2){
			this.paintWeighted(getGraphics());
		}
		else if (e.getSource() == button3){
			this.paintPower(getGraphics());
		}
		else if(e.getSource() == button4){
			this.paintFurthest(getGraphics());
		}
		else if(e.getSource() == button5){
			this.paintSegment(getGraphics());
		}	
		
	}

	   double atod(String s)
	   {
	      double answer;
	      Double d;
	      
	      try
	      {
	         d = new Double(s);
	         answer = d.doubleValue( );
	      }
	      catch (NumberFormatException e)
	      {
	         answer = Double.NaN;
	      }
	      return answer;
	   }
	
 
	public void paintVoronoi(Graphics g)
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
					 g.drawLine(i, j+200, i, j+200);
				 }
				 else{
					 for (int l= 0; l<3; l++){
						  color[l] =(int) ((1-p)*(1-p)*(1-p)*(1-p)*(1-p)*closeColor[l]+p*p*p*p*p*farColor[l]);
	 
					  }
					 Color c = new Color(color[0], color[1], color[2]);
					 g.setColor(c);
					 g.drawLine(i, j+200, i, j+200);
				 }	   
			}	
		}
		for(int i = 0; i < numberOfPoint; i++){
			int xCoor = pointArray[i].x;
			int yCoor = pointArray[i].y;
			g.setColor(Color.black);
			g.fillOval(xCoor, yCoor+200, 5, 5);
		}
    }
	
	
	public void paintWeighted(Graphics g) 
	{
		Point[] pointArray = new Point[numberOfPoint];
		for (int i = 0; i < numberOfPoint; i++){
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
				 for(int k = 1; k< numberOfPoint; k++){
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

				 for(int k = 2; k< numberOfPoint; k++){
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
					 g.drawLine(i, j+200, i, j+200);
					 continue;
				 }
				 double p = (minDistHere/maxDist);
				 if(secondMinDistHere-minDistHere <= delta){
					 g.setColor(Color.black);
					 g.drawLine(i, j+200, i, j+200);
				 }
				 else{
					 for (int l= 0; l<3; l++){
						  color[l] =(int) ((1-p)*(1-p)*(1-p)*(1-p)*(1-p)*closeColor[l]+p*p*p*p*p*farColor[l]);
	 
					  }
					 Color c = new Color(color[0], color[1], color[2]);
					 g.setColor(c);
					 g.drawLine(i, j+200, i, j+200);
				 }	   
			}	
		}
		for(int i = 0; i < numberOfPoint; i++){
			int xCoor = pointArray[i].x;
			int yCoor = pointArray[i].y;
			g.setColor(Color.black);
			g.fillOval(xCoor, yCoor+200, 5, 5);
		}
    }
	
	public void paintPower(Graphics g) 
	{
		Point[] pointArray = new Point[numberOfPoint];
		for (int i = 0; i < numberOfPoint; i++){
			int x = new java.util.Random().nextInt(screenSize);
			int y = new java.util.Random().nextInt(screenSize);
			int r = new java.util.Random().nextInt(radiusOfCircleRange);
			pointArray[i] = new Point(x,y,r);
			//g.drawLine(pointArray[i].x, pointArray[i].y, pointArray[i].x, pointArray[i].y);
		}	
		double maxDist =pointArray[0].PowerDistance(0, 0);
		//Color c = Color.blue;
		for (int i = 0; i < screenSize; i++ )
		{
			for (int j = 0; j < screenSize; j++)
			{
				 for(int k = 1; k< numberOfPoint; k++){
					 double tempDistance = pointArray[k].PowerDistance(i, j);
 
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
				 double minDistHere =pointArray[0].PowerDistance(i, j);
				 double secondMinDistHere =pointArray[1].PowerDistance(i, j);
				 if(minDistHere > secondMinDistHere){
					 double temp = minDistHere;
					 minDistHere = secondMinDistHere;
					 secondMinDistHere = temp;
				 }

				 for(int k = 2; k< numberOfPoint; k++){
					 double distanceTo = pointArray[k].PowerDistance(i, j);

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
					 g.drawLine(i, j+200, i, j+200);
					 continue;
				 }
				 double p = (minDistHere/maxDist); 
				 if(Math.sqrt(secondMinDistHere)-Math.sqrt(minDistHere) <= delta){
					 g.setColor(Color.black);
					 g.drawLine(i, j+200, i, j+200);
 
				 }
				 else{
					 for (int l= 0; l<3; l++){
						  color[l] =(int) ((1-p)*(1-p)*(1-p)*(1-p)*(1-p)*(1-p)*closeColor[l]+p*p*p*p*p*p*farColor[l]);
	 
					  }
					 Color c = new Color(color[0], color[1], color[2]);
					 g.setColor(c);
					 g.drawLine(i, j+200, i, j+200);
				 }	   
			}	
		}
		for(int i = 0; i < numberOfPoint; i++){
			int xCoor = pointArray[i].x;
			int yCoor = pointArray[i].y;
			g.setColor(Color.black);
			g.fillOval(xCoor, yCoor+200, 5, 5);
		}
    }
	
	
	public void paintFurthest(Graphics g)
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
				 double maxDistHere =pointArray[0].distance(i, j);
				 double secondMaxDistHere =pointArray[1].distance(i, j);
				 if(maxDistHere < secondMaxDistHere){
					 double temp = maxDistHere;
					 maxDistHere = secondMaxDistHere;
					 secondMaxDistHere = temp;
				 }
				 for(int k = 1; k< numberOfPoint; k++){
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
					 p = 1;
				 }
				 if(maxDistHere-secondMaxDistHere <= delta){
					 g.setColor(Color.black);
					 g.drawLine(i, j+200, i, j+200);
				 }
				 else{
					 for (int l= 0; l<3; l++){
						  color[l] =(int) (p*p*p*farColor[l]+ (1-p)*(1-p)*(1-p)*closeColor[l]);
	 
					  }
					 Color c = new Color(color[0], color[1], color[2]);
					 g.setColor(c);
					 g.drawLine(i, j+200, i, j+200);
				 }	   
			}	
		}
		for(int i = 0; i < numberOfPoint; i++){
			int xCoor = pointArray[i].x;
			int yCoor = pointArray[i].y;
			g.setColor(Color.black);
			g.fillOval(xCoor, yCoor+200, 5, 5);
		}
    }
	
	
	public void paintSegment(Graphics g)
	{
		Line2D.Double[] segmentArray = new Line2D.Double[numberOfPoint];
		//Here we generate numOfSegments non-intersected line segments.
		for (int i = 0; i < numberOfPoint; i++){
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
		}
		double maxDist =segmentArray[0].ptSegDist(0, 0);
		//Color c = Color.blue;
		for (int i = 0; i < screenSize; i++ )
		{
			 
			for (int j = 0; j < screenSize; j++)
			{
				
				 for(int k = 1; k< numberOfPoint; k++){
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
				 for(int k = 1; k< numberOfPoint; k++){
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
					 g.drawLine(i, j+200, i, j+200);
				 }
				 else{
					 for (int l= 0; l<3; l++){
						  color[l] =(int) ((1-p)*(1-p)*(1-p)*(1-p)*(1-p)*(1-p)*closeColor[l]+p*p*p*p*p*p*farColor[l]);
	 
					  }
					 Color c = new Color(color[0], color[1], color[2]);
					 g.setColor(c);
					 g.drawLine(i, j+200, i, j+200);
				 }	   
			}	
		}
		
		for(int i = 0; i < numberOfPoint; i++){
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(4));
			g2.drawLine((int)segmentArray[i].getX1(), (int)segmentArray[i].getY1()+200, (int)segmentArray[i].getX2(), (int)segmentArray[i].getY2()+200);
		}
    }
	public class Point {

		int x;
		int y;
		int radius;
		public Point(int xCoor, int yCoor)
		{
			x = xCoor;
			y = yCoor;
		}
		public Point(int xCoor, int yCoor, int r){
			x = xCoor;
			y = yCoor;
			radius = r;
		}
		public double distance(int i, int j){
			return Math.sqrt((i-x)*(i-x)+(j-y)*(j-y));
		}
		public double weightedDistance(int i, int j){
			return Math.sqrt((i-x)*(i-x)+(j-y)*(j-y))-radius;
			//return ((i-x)*(i-x)+(j-y)*(j-y))-radius*radius;
		}
		public double PowerDistance(int i, int j){
			return ((i-x)*(i-x)+(j-y)*(j-y))-radius*radius;
		}
	}
	

}


 
