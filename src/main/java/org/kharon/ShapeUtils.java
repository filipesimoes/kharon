package org.kharon;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Collection;

public class ShapeUtils {

  public static boolean intersects(Rectangle rect, Collection<Shape> shapes) {
    for (Shape shape : shapes) {
      if (shape.intersects(rect)) {
        return true;
      }
    }
    return false;
  }

}
