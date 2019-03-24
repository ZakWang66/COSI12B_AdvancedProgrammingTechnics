import processing.sound.*;

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

  void setup() {
    hup = new Headsup();
    paddle = new Paddle(width * 0.25, #00E8FF);
    ball = new Ball(bounce);
  }

  void draw() {
    background(0);
    ball.draw();
    hup.draw();
    paddle.draw();
  }

  void update() {
    updateBall(ball);
  }

  void updateBall(Ball ball) {
    if (!ball.update()) {
      lostBall.play();
      hup.round--;
      this.ball = new Ball(bounce);
    }
    paddle.update();
    collidesPaddle (paddle, ball);
  }

  void collidesPaddle (Paddle rect, Ball circle) {
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
