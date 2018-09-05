package org.kharon;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class NodeBoundingBox {

  private List<Shape> boxes = new ArrayList<>(2);

  private double scale = 1.0d;

  public NodeBoundingBox(double scale) {
    super();
    this.scale = scale;
  }

  public NodeBoundingBox() {
    super();
  }

  public Rectangle2D addBox(Shape box) {
    if (scale != 1.0d) {
      Rectangle2D bounds = ShapeUtils.scale(box.getBounds2D(), this.scale);
      this.boxes.add(bounds);
      return bounds;
    } else {
      Rectangle2D bounds = box.getBounds2D();
      this.boxes.add(bounds);
      return bounds;
    }
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

  public Rectangle2D getUnionBox(double scale) {
    return ShapeUtils.getUnionBox(boxes, scale);
  }

  public void clear() {
    this.boxes.clear();
  }
}
