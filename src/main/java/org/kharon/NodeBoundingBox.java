package org.kharon;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class NodeBoundingBox {

  private List<Shape> boxes = new ArrayList<>(2);

  public void addBox(Shape box) {
    this.boxes.add(box);
  }

  public boolean contains(Point2D point) {
    for (Shape box : boxes) {
      if (box.contains(point)) {
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

  public void clear() {
    this.boxes.clear();
  }
}
