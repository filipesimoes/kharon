package org.kharon.renderers;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import org.kharon.Node;

public class CircleNodeRenderer extends BaseNodeRenderer implements NodeRenderer {

  @Override
  public Shape renderShape(Node node, RenderContext renderContext) {
    int x = node.getX();
    int y = node.getY();
    int size = node.getSize();

    Shape shape = new Ellipse2D.Double(x, y, size, size);
    return shape;
  }

}
