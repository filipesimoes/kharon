package org.kharon;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;

public class ShapeUtils {

  public static boolean intersects(Rectangle2D rect, Collection<Shape> shapes) {
    for (Shape shape : shapes) {
      if (shape.intersects(rect)) {
        return true;
      }
    }
    return false;
  }

  public static Rectangle2D getUnionBox(Collection<Shape> shapes, double scale) {
    if (!shapes.isEmpty()) {
      Iterator<Shape> iterator = shapes.iterator();
      Rectangle2D union = iterator.next().getBounds2D();
      while (iterator.hasNext()) {
        Rectangle2D.union(iterator.next().getBounds2D(), union, union);
      }

      if (scale != 1.0d) {
        return scale(union, scale);
      } else {
        return union;
      }
    }

    return null;
  }

  public static Rectangle2D getUnionBoundingBox(Collection<NodeBoundingBox> boxes, double scale) {
    if (!boxes.isEmpty()) {
      Iterator<NodeBoundingBox> iterator = boxes.iterator();
      NodeBoundingBox next = iterator.next();
      Rectangle2D union = getUnionBox(next.getBoxes(), scale);
      while (iterator.hasNext()) {
        next = iterator.next();
        Rectangle2D.union(getUnionBox(next.getBoxes(), scale), union, union);
      }
      if (scale != 1.0d) {
        return scale(union, scale);
      } else {
        return union;
      }
    }

    return null;
  }

  public static Rectangle2D scale(Rectangle2D box, double scale) {
    double x = box.getX();
    double y = box.getY();

    double width = box.getWidth();
    double height = box.getHeight();

    double newWidth = width * scale;
    double newHeight = height * scale;

    x = x - (newWidth - width) / 2;
    y = y - (newHeight - height) / 2;

    return new Rectangle2D.Double(x, y, newWidth, newHeight);
  }

}
