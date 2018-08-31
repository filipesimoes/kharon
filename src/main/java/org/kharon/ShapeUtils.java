package org.kharon;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

public class ShapeUtils {

  public static boolean intersects(Rectangle2D rect, Collection<Shape> shapes) {
    for (Shape shape : shapes) {
      if (shape.intersects(rect)) {
        return true;
      }
    }
    return false;
  }

}
