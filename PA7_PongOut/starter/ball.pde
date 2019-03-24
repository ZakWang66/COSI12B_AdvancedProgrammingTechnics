import java.util.Random;
import processing.sound.*;

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
    velocity = new PVector(direction * (2 + r.nextFloat() * 4), 2.5);
    acceleration = new PVector(0, 0.15);
    this.bounce = bounce;
  }
  
  boolean update() {
    // compute where ball is next
    velocity.add(acceleration);
    position.add(velocity);
    return checkEdges();
  }
  
  void draw() {
    fill(#F50C0C);
    ellipse(position.x, position.y, 2*r, 2*r);
  }

  boolean checkEdges() {
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
