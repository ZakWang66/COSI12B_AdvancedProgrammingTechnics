class Paddle {
  float x;
  float y;
  float w;
  float h;
  int col;

  Paddle(float initX, int col) {
    x = initX;
    y = height * .9;
    w = 75;
    h = 15;
    this.col = col;
  }
  
  void draw() {
    fill(col);
    rect(x, y, w, h, 0.1, 0.1, 0.1, 0.1);
  }
  
  void update() {
    if (keyPressed) {
      if (keyCode == 39) {
        x = x >= width*0.87 ? width*0.87 : x + 3;
      }
      if (keyCode == 37) {
        x = x <= width*0.13 ? width*0.13 : x - 3;
      }
      delay(10);
    }
  }
}
