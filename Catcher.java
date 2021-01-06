//COMP 1006A/1406A Assignment 4 Problem 6
//By Alexander Kuhn, ID# 101023154, August 9th, 2016
//Purpose: This class makes CTF players that track and catch all the players on the opposing team, one-by-one.
//Once they've caught everyone, they start again from the beginning.
public class Catcher extends Player {
  private double originalSpeedX = Math.random()*4-2;
  private double originalSpeedY = Math.random()*4-2;
  double distanceX;
  double distanceY;
  //Same variables as usual
  
  private Player[] bluePlayerList;
  private Player[] redPlayerList;
  //Arrays that store all the players from either team; the ArrayList that Field automatically stores players in casts them all as Entities, which can introduce trouble
  private int[] bluePlayersTargeted;
  private int[] redPlayersTargeted;
  //The catcher iterates through these arrays to figure out which player in the corresponding PlayerList is the one to go after next
  private int startedPlaying = 0;
  //Whenever a catcher starts catching Players - whether it's because the game just started, or because they just finished catching every opposing Player and need to start over -
  //this variable resets to 0 and reconstructs the list
  private Player Target;
  //This is used to store the current target's details
  private int currentBlueTarget = -1;
  private int currentRedTarget = -1;
  //These two are only equal to -1 when the Catcher doesn't have a target, either because they've just been constructed or because they've just caught somebody
  
  //It's not strictly necessary to have different variables for blue and red teams - nobody's both blue and red at the same time, so one of each blue/red variable will always go unused -
  //but it lays the groundwork for being able to make red and blue teams behave differently, or even to introduce non-standard elements, like a neutral catcher who goes after different teams depending on who's winning
  
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
    int targetCrossedOff;
    int targetListLength;
    //As the Catcher catches players, the array decreases in size through the use of these variables
    //targetCrossedOff is the target that was just caught, and targetListLength is the current length of the target list
    //Then, a new target list is created - one element shorter and without targetCrossedOff
    
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
    
    if (startedPlaying == 0) {
      bluePlayersTargeted = new int[field.getTeam1().size()];
      bluePlayerList = new Player[field.getTeam1().size()];
      
      redPlayersTargeted = new int[field.getTeam2().size()];
      redPlayerList = new Player[field.getTeam2().size()];
      
      for (int i = 0; i < bluePlayersTargeted.length; i += 1) {
        bluePlayersTargeted[i] = i;
        bluePlayerList[i] = (Player) field.getTeam1().get(i);
      }
      
      for (int i = 0; i < redPlayersTargeted.length; i += 1) {
        redPlayersTargeted[i] = i;
        redPlayerList[i] = (Player) field.getTeam2().get(i);
      }
      
      startedPlaying = 1;
      //If the relevant PlayerList is empty, this reconstructs it
    }
    
    
    if (this.team.intern() == "reds"){
      if (currentBlueTarget == -1) {
        currentBlueTarget = bluePlayersTargeted[bluePlayersTargeted.length - 1];
        Target = bluePlayerList[currentBlueTarget];
        //If the Catcher has no target, this tells it to target the last enemy player on the list
      }
      distanceX = (Target.getX() - this.x);
      distanceY = (Target.getY() - this.y);
    
      double angle = Math.atan2(distanceY, distanceX);
      this.speedX *= Math.cos(angle);
      this.speedY *= Math.sin(angle);
      
      if (this.catchOpponent(Target, field)) {
        targetCrossedOff = currentBlueTarget;
        targetListLength = bluePlayersTargeted.length;
        bluePlayersTargeted = new int[targetListLength - 1];
        currentBlueTarget = 0;
        
        for (int i = 0; i < bluePlayersTargeted.length; i += 1) {
          if (currentBlueTarget != targetCrossedOff) {
            bluePlayersTargeted[i] = currentBlueTarget;
          }
          else {
            i -= 1;
          }
          currentBlueTarget += 1;
        }
        
        if (bluePlayersTargeted.length == 0) {
          startedPlaying = 0;
        }
        currentBlueTarget = -1;
      }
    }
    
    else {
      if (currentRedTarget == -1) {
        currentRedTarget = redPlayersTargeted[redPlayersTargeted.length - 1];
        Target = redPlayerList[currentRedTarget];
      }
      distanceX = (Target.getX() - this.x);
      distanceY = (Target.getY() - this.y);
    
      double angle = Math.atan2(distanceY, distanceX);
      this.speedX *= Math.cos(angle);
      this.speedY *= Math.sin(angle);
      
      if (this.catchOpponent(Target, field)) {
        targetCrossedOff = currentRedTarget;
        targetListLength = redPlayersTargeted.length;
        redPlayersTargeted = new int[targetListLength - 1];
        currentRedTarget = 0;
        
        for (int i = 0; i < redPlayersTargeted.length; i += 1) {
          if (currentRedTarget != targetCrossedOff) {
            redPlayersTargeted[i] = currentRedTarget;
          }
          else {
            i -= 1;
          }
          currentRedTarget += 1;
        }
        
        if (redPlayersTargeted.length == 0) {
          startedPlaying = 0;
        }
        currentRedTarget = -1;
      }
    }
    
  }
    
  
  @Override
  public void update(Field field){}
 
  public Catcher(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = originalSpeedX;
    this.speedY = originalSpeedY;
  }
  
}