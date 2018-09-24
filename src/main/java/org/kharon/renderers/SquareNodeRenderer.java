package org.kharon.renderers;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import org.kharon.Node;

public class SquareNodeRenderer extends BaseNodeRenderer implements NodeRenderer {

  @Override
  public Shape renderShape(Node node, RenderContext renderContext) {
    int x = node.getX();
    int y = node.getY();
    int size = node.getSize();
    Rectangle2D.Double shape = new Rectangle2D.Double(x, y, size, size);
    return shape;
  }

}
