//COMP 1006A/1406A Assignment 4 Problem 3
//By Alexander Kuhn, ID# 101023154, August 9th, 2016
//Purpose: This class makes CTF players that move randomly at first, then adjust their movement patterns to try and get in range of the flag.
//When they can pick up the flag, they do so and then stop moving.
public class Seeker extends Player {
  private double originalSpeedX = Math.random()*4-2;
  private double originalSpeedY = Math.random()*4-2;
  double distanceX;
  double distanceY;
  //3 of the 5 classes we have to design require a method to find a specified target
  //Originally, I implemented it very simply; if you were moving in the right direction, you kept moving, and if you weren't, you reversed direction
  //However, this meant everything moved in strictly straight lines, which the criteria warned against
  //Each of my Seeker-type classes now uses vectors to find their targets
  //For this algorithm to work, I need their starting speed, because it must reset after each call to play
  //I also need the horizontal and vertical distance between them and their target
  //By default, every class has a randomly generated speed between -2 and 2, simply because that's the speed in DummyPlayer
  //TAKE NOTE: THIS MEANS TESTING SPEED WILL REQUIRE YOU TO ALTER ORIGINALSPEEDX AND ORIGINALSPEEDY RATHER THAN SPEEDX AND SPEEDY
  //For this reason, I've overridden the basic getters and setters for speed from Entity
  
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
  //I had to alter Entity slightly to be able to override these setters; they're supposed to be final
  
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
    //This algorithm won't work properly with negative speeds; it behaves very oddly, and almost always ends up running off the map
    
    if (this.team.intern() == "reds"){
      distanceX = (field.getFlag1Position()[0] - this.x);
      distanceY = (field.getFlag1Position()[1] - this.y);
    }
    
    else{
      distanceX = (field.getFlag2Position()[0] - this.x);
      distanceY = (field.getFlag2Position()[1] - this.y);
    }
   
    double vector = Math.atan2(distanceY, distanceX);
    this.speedX *= Math.cos(vector);
    this.speedY *= Math.sin(vector);
    //vector is, well, the vector between this object and its target
    //Its cosine is the normalized x component of that vector, and its sine is the normalized y component
      
    if (this.pickUpFlag(field) == true) {
      this.originalSpeedX = 0;
      this.originalSpeedY = 0;
    }
    //Seekers that find their target (the flag) must remain where they found the flag, so when they pick it up I ensure that they don't move anymore
    
    //Every play method that requires its object to find and move towards some other object will be an instance of this algorithm
    
  }
  
  @Override
  public void update(Field field){}
  
  public Seeker(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = originalSpeedX;
    this.speedY = originalSpeedY;
  }

}