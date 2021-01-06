//COMP 1006A/1406A Assignment 4 Problem 2
//By Alexander Kuhn, ID# 101023154, August 9th, 2016
//Purpose: This class makes CTF players that move randomly at first, then stop moving the instant they cross over a boundary.
public class Stopping extends Player{
 
  @Override
  public void play(Field field){
    if ((this.x + (Field.SCALE * this.speedX) / Entity.TIME_SCALE) >= 793){
      this.x = 793;
      this.speedX = 0;
      this.speedY = 0;
    }
    else if ((this.x + (Field.SCALE * this.speedX) / Entity.TIME_SCALE) <= 10){
      this.x = 10;
      this.speedX = 0;
      this.speedY = 0;
    }
    if ((this.y + (Field.SCALE * this.speedY) / Entity.TIME_SCALE) >= 593){ 
      this.y = 593;
      this.speedX = 0;
      this.speedY = 0;
    }
    else if ((this.y + (Field.SCALE * this.speedY) / Entity.TIME_SCALE) <= 10){
      this.y = 10;
      this.speedX = 0;
      this.speedY = 0;
    }
  }
  //With this, any player that would go over any of the four borders will instead stop at the border's edge
  //After some testing, these x- and y-values seem to get the closest to the borders without ever going over
  //I need to pull static variables from Field and Entity to get accurate information on where my Stopping object is really
  //going to be, because the updatePosition method Entity uses is dependent on those as well as my object's position and speed
  
  @Override
  public void update(Field field){}
  
  public Stopping(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*4-2;
    this.speedY = Math.random()*4-2;
  }

}