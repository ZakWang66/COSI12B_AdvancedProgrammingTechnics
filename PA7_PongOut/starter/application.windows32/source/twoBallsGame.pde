import processing.sound.*;

class TwoBallsGame extends Game {
  Ball ball2;
  int start;
  boolean begin;

  TwoBallsGame(SoundFile bounce, SoundFile lostBall) {
    super(bounce, lostBall);
  }

  @Override
  void setup() {
    super.setup();
    start = millis();
    begin = false;
  }

  @Override
  void draw() {
    background(0);
    ball.draw();
    if (begin)
      ball2.draw();
    hup.draw();
    paddle.draw();
  }

  @Override
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

  @Override
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
