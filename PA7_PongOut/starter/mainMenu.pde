class MainMenu {
  int selected;
  final float[] boxPlace = new float[]{5,2,1.25};
  final String[]modes = new String[]{"Normal Mode", "Multiple Balls", "Two Players"};

  MainMenu () {
    selected = 0;
  }

  int update() {
    return waitKey();
  }

  void draw() {
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
      text(modes[i], width/3.5, height/boxPlace[i]);
    }
    
  }

  int waitKey() {
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
