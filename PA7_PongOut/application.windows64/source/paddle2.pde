class Paddle2 extends Paddle {

    Paddle2(float initX, int col) {
        super(initX, col);
    }

    @Override
    void update() {
    if (keyPressed) {
      if (key == 'd' || key == 'D') {
        x = x >= width*0.87 ? width*0.87 : x + 3;
      }
      if (key == 'a' || key == 'A') {
        x = x <= width*0.13 ? width*0.13 : x - 3;
      }
      delay(10);
    }
  }
}
