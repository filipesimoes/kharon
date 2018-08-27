package org.kharon;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class NodeBoundingBox {

  private List<Shape> boxes = new ArrayList<>(2);

  public void addBox(Shape box) {
    this.boxes.add(box);
  }

  public boolean contains(double x, double y) {
    for (Shape box : boxes) {
      if (box.contains(x, y)) {
        return true;
      }
    }
    return false;
  }

  public boolean intersects(Rectangle2D rect) {
    for (Shape box : boxes) {
      if (box.intersects(rect)) {
        return true;
      }
    }
    return false;
  }

  public List<Shape> getBoxes() {
    return boxes;
  }

}
