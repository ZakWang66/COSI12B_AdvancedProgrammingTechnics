// Pong Game
import java.util.*;
import processing.sound.*;

MainMenu mainMenu;
Game game;
boolean begin;
int start = 0;


SoundFile lostBall;
SoundFile lostGame;
SoundFile bounce;

void setup() {
  size(300, 600);
  begin = false;
  bounce = new SoundFile(this, "pong.mp3");
  lostBall = new SoundFile(this, "lostball.mp3");
  lostGame = new SoundFile(this, "lostgame.mp3");
  mainMenu = new MainMenu();
}

void draw() {
  if (!begin) {
    int selected = -1;
    mainMenu.draw();
    selected = mainMenu.update();
    if (selected < 0) {
      return;
    }
    else {
      begin = true;
      switch (selected) {
        case 0: game = new Game(bounce, lostBall); break;
        case 1: game = new TwoBallsGame(bounce, lostBall); break;
        case 2: game = new TwoPlayersGame(bounce, lostBall); break;
      }
      game.setup();
    }
  }
  else {

    if (game.hup.round <= 0) {
      if (start == 0) {
        background(0);
        textSize(width/10);
        fill(255);
        text("Game Over", width/4, height/2); 
        lostGame.play();
        start = millis();
      }
      int time = millis() - start;
      if (time >= 2000) {
        start = 0;
        mainMenu = new MainMenu();
        begin = false;
      }
    }
    else {
      game.update();
      game.draw();
    }
  }
}
