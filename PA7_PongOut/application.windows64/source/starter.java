import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.*; 
import processing.sound.*; 
import java.util.Random; 
import processing.sound.*; 
import processing.sound.*; 
import processing.sound.*; 
import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class starter extends PApplet {

// Pong Game



MainMenu mainMenu;
Game game;
boolean begin;
int start = 0;


SoundFile lostBall;
SoundFile lostGame;
SoundFile bounce;

public void setup() {
  
  begin = false;
  bounce = new SoundFile(this, "pong.mp3");
  lostBall = new SoundFile(this, "lostball.mp3");
  lostGame = new SoundFile(this, "lostgame.mp3");
  mainMenu = new MainMenu();
}

public void draw() {
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



class Ball {
  PVector position;
  PVector velocity;
  PVector acceleration;
  SoundFile bounce;
  int r = 8;
  
  Ball(SoundFile bounce) {
    Random r = new Random();
    int direction = r.nextBoolean() ? 1 : -1;
    position = new PVector(width/2, 10);
    velocity = new PVector(direction * (2 + r.nextFloat() * 4), 2.5f);
    acceleration = new PVector(0, 0.15f);
    this.bounce = bounce;
  }
  
  public boolean update() {
    // compute where ball is next
    velocity.add(acceleration);
    position.add(velocity);
    return checkEdges();
  }
  
  public void draw() {
    fill(0xffF50C0C);
    ellipse(position.x, position.y, 2*r, 2*r);
  }

  public boolean checkEdges() {
    if (position.x > width - r) {
      position.x = width - r;
      velocity.x *= -1;
      bounce.play();
    } else if (position.x < r) {
      velocity.x *= -1;
      position.x = r;
      bounce.play();
    }
    if (position.y > height - r) {
      return false;
    } else if (position.y < r) {
      velocity.y *= -1;
      position.y = r;
      bounce.play();
    }
    return true;
  }
}


class Game {
  Ball ball;
  Paddle paddle;
  Headsup hup;
  SoundFile bounce;
  SoundFile lostBall;
  
  Game(SoundFile bounce, SoundFile lostBall) {
    this.bounce = bounce;
    this.lostBall = lostBall;
  }

  public void setup() {
    hup = new Headsup();
    paddle = new Paddle(width * 0.25f, 0xff00E8FF);
    ball = new Ball(bounce);
  }

  public void draw() {
    background(0);
    ball.draw();
    hup.draw();
    paddle.draw();
  }

  public void update() {
    updateBall(ball);
  }

  public void updateBall(Ball ball) {
    if (!ball.update()) {
      lostBall.play();
      hup.round--;
      this.ball = new Ball(bounce);
    }
    paddle.update();
    collidesPaddle (paddle, ball);
  }

  public void collidesPaddle (Paddle rect, Ball circle) {
    PVector half = new PVector(rect.w/2, rect.h/2);
    PVector center = new PVector(
      circle.position.x - (rect.x),
      circle.position.y - (rect.y));
    PVector side = new PVector(
        Math.abs (center.x) - half.x - circle.r, 
        Math.abs (center.y) - half.y - circle.r);
    if (side.x <=  0 && side.y <=  0) {
      if (side.y < 0) {
        if (center.y < 0) 
          circle.position.y = rect.y - half.y - circle.r;
        else if (center.y > 0) 
          circle.position.y = rect.y + half.y + circle.r; 
        bounce.play();
        circle.velocity.y *= -1;
      }
    }
  }
}
class Headsup {
  int round;

  Headsup() {
    round = 3;
  }

  public void draw() {
    // draw the game status as a "headsup" display on the gameboard
    textSize(16);
    fill(255);
    text("Zekai Wang", 10, 30); 
    text(round + " balls left", 10, 50); 

  }
}
class MainMenu {
  int selected;
  final float[] boxPlace = new float[]{5,2,1.25f};
  final String[]modes = new String[]{"Normal Mode", "Multiple Balls", "Two Players"};

  MainMenu () {
    selected = 0;
  }

  public int update() {
    return waitKey();
  }

  public void draw() {
    background(80);
    rectMode(CENTER);
    for (int i = 0; i < boxPlace.length; i++) {
      if (i == selected) {
        fill(255);
      }
      else {
        fill(150);
      }
      rect(width/2, height/boxPlace[i], height/3, height/6);
      fill(0, 102, 153);
      textSize(20);
      text(modes[i], width/3.5f, height/boxPlace[i]);
    }
    
  }

  public int waitKey() {
    if (keyPressed) {
      if (key == '\n') {
        return selected;
      }
      if (keyCode == 40) {
        selected = selected >= 2 ? 2 : selected + 1;
      }
      if (keyCode == 38) {
        selected = selected <= 0 ? 0 : selected - 1;
      }
      delay(100);
    }
    return -1;
  }
}
class Paddle {
  float x;
  float y;
  float w;
  float h;
  int col;

  Paddle(float initX, int col) {
    x = initX;
    y = height * .9f;
    w = 75;
    h = 15;
    this.col = col;
  }
  
  public void draw() {
    fill(col);
    rect(x, y, w, h, 0.1f, 0.1f, 0.1f, 0.1f);
  }
  
  public void update() {
    if (keyPressed) {
      if (keyCode == 39) {
        x = x >= width*0.87f ? width*0.87f : x + 3;
      }
      if (keyCode == 37) {
        x = x <= width*0.13f ? width*0.13f : x - 3;
      }
      delay(10);
    }
  }
}
class Paddle2 extends Paddle {

    Paddle2(float initX, int col) {
        super(initX, col);
    }

    public @Override
    void update() {
    if (keyPressed) {
      if (key == 'd' || key == 'D') {
        x = x >= width*0.87f ? width*0.87f : x + 3;
      }
      if (key == 'a' || key == 'A') {
        x = x <= width*0.13f ? width*0.13f : x - 3;
      }
      delay(10);
    }
  }
}


class TwoBallsGame extends Game {
  Ball ball2;
  int start;
  boolean begin;

  TwoBallsGame(SoundFile bounce, SoundFile lostBall) {
    super(bounce, lostBall);
  }

  public @Override
  void setup() {
    super.setup();
    start = millis();
    begin = false;
  }

  public @Override
  void draw() {
    background(0);
    ball.draw();
    if (begin)
      ball2.draw();
    hup.draw();
    paddle.draw();
  }

  public @Override
  void update() {
    if (millis() - start > 1000) {
      if (!begin) {
        ball2 = new Ball(bounce);
        begin = true;
      }
      updateBall(ball2);
    }
    updateBall(ball);
  }

  public @Override
  void updateBall(Ball ball) {
    if (!ball.update()) {
      lostBall.play();
      hup.round--;
      if (ball == this.ball) {
        this.ball = new Ball(bounce);
      }
      else if (ball == this.ball2){
        this.ball2 = new Ball(bounce);
      }
    }
    paddle.update();
    collidesPaddle (paddle, ball);
  }

}


class TwoPlayersGame extends Game {
    Paddle paddle2;

    public @Override
    void setup() {
        super.setup();
        paddle2 = new Paddle2(width * 0.75f, 0xffFF81C6);
    }

    public @Override
    void draw() {
        super.draw();
        paddle2.draw();
    }

    public @Override
    void updateBall(Ball ball) {
        super.updateBall(ball);
        paddle2.update();
        collidesPaddle (paddle2, ball);
    }

    TwoPlayersGame(SoundFile bounce, SoundFile lostBall) {
        super(bounce, lostBall);
    }
}
  public void settings() {  size(300, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "starter" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
