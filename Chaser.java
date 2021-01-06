//COMP 1006A/1406A Assignment 4 Problem 5
//By Alexander Kuhn, ID# 101023154, August 9th, 2016
//Purpose: This class makes CTF players that latch onto a player from the opposing team and never stop chasing them.
public class Chaser extends Player {
  private double originalSpeedX = Math.random()*4-2;
  private double originalSpeedY = Math.random()*4-2;
  double distanceX;
  double distanceY;
  
  @Override
  public double getSpeedX(){ return this.originalSpeedX; }
  
  @Override
  public double getSpeedY(){ return this.originalSpeedY; }
  
  @Override
  protected final void setSpeedX(double speedX, int id){ 
    /* check if the caller knows this entity's id */
    if( id != this.id ){
      throw new SecurityException("Unauthorized change of entity x-direction speed");
    }
    this.originalSpeedX = speedX;
  }
  
  @Override
  protected final void setSpeedY(double speedY, int id){ 
    /* check if the caller knows this entity's id */
    if( id != this.id ){
      throw new SecurityException("Unauthorized change of entity y-direction speed");
    }
    this.originalSpeedY = speedY;
  }
 
  @Override
  public void play(Field field){
    if (originalSpeedX > 0) {
      this.speedX = originalSpeedX;
    }
    else {
      this.speedX = originalSpeedX * (-1);
    }
    if (originalSpeedY > 0) {
      this.speedY = originalSpeedY;
    }
    else {
      this.speedY = originalSpeedY * (-1);
    }
    
    if (this.team.intern() == "reds"){
      distanceX = (field.getTeam1().get(0).getX() - this.x);
      distanceY = (field.getTeam1().get(0).getY() - this.y);
    }
    else {
      distanceX = (field.getTeam2().get(0).getX() - this.x);
      distanceY = (field.getTeam2().get(0).getY() - this.y);
    }
    
    double angle = Math.atan2(distanceY, distanceX);
    this.speedX *= Math.cos(angle);
    this.speedY *= Math.sin(angle);
    
  }
  //Just like Seeker's play method, except this class follows a moving target
  //It will always follow the first player generated on the opposite team, since the criteria say it will only ever be tested against a single player
    
  @Override
  public void update(Field field){}
 
  public Chaser(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = originalSpeedX;
    this.speedY = originalSpeedY;
  }
  
}