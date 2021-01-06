//COMP 1006A/1406A Assignment 4 Problem 4
//By Alexander Kuhn, ID# 101023154, August 9th, 2016
//Purpose: This class makes CTF players that move randomly at first, then reverse direction the moment before they would cross over a boundary.
public class RandomWalker extends Player {
 
  @Override
  public void play(Field field){
    if( ((this.x + (Field.SCALE * this.speedX) / Entity.TIME_SCALE) >= 793) || ((this.x + (Field.SCALE * this.speedX) / Entity.TIME_SCALE) <= 12) ){
      this.speedX *= -1;
    }
    if( ((this.y + (Field.SCALE * this.speedY) / Entity.TIME_SCALE) >= 593) || ((this.y + (Field.SCALE * this.speedY) / Entity.TIME_SCALE) <= 12) ){
      this.speedY *= -1;
    }
    
  }
  //This class is almost identical to Stopping, except when they're about to exceed a boundary they reverse instead of stop
    
  
  @Override
  public void update(Field field){}
 
  public RandomWalker(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*4-2;
    this.speedY = Math.random()*4-2;
  }
  
}