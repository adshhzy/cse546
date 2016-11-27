package Voronoi;
/*======================================================================
Created by Steven Zhao 
Wustl Class of 2015
*///====================================================================
import java.lang.Math;
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
