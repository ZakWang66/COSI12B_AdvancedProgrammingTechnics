class Headsup {
  int round;

  Headsup() {
    round = 3;
  }

  void draw() {
    // draw the game status as a "headsup" display on the gameboard
    textSize(16);
    fill(255);
    text("Zekai Wang", 10, 30); 
    text(round + " balls left", 10, 50); 

  }
}