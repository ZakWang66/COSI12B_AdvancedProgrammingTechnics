import processing.sound.*;

class TwoPlayersGame extends Game {
    Paddle paddle2;

    @Override
    void setup() {
        super.setup();
        paddle2 = new Paddle2(width * 0.75, #FF81C6);
    }

    @Override
    void draw() {
        super.draw();
        paddle2.draw();
    }

    @Override
    void updateBall(Ball ball) {
        super.updateBall(ball);
        paddle2.update();
        collidesPaddle (paddle2, ball);
    }

    TwoPlayersGame(SoundFile bounce, SoundFile lostBall) {
        super(bounce, lostBall);
    }
}