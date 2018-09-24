package org.kharon.renderers;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.kharon.Node;

public abstract class ShapeNodeRenderer extends BaseNodeRenderer implements NodeRenderer {

  public abstract Shape drawShape(Node node, RenderContext renderContext);

  public Shape renderShape(Node node, RenderContext renderContext) {
    Shape shape = drawShape(node, renderContext);

    Rectangle2D bounds = shape.getBounds2D();
    int size = node.getSize();
    double widthScale = size / bounds.getWidth();
    double heigthScale = size / bounds.getHeight();
    int x = node.getX();
    int y = node.getY();

    shape = AffineTransform.getScaleInstance(widthScale, heigthScale).createTransformedShape(shape);
    shape = AffineTransform.getTranslateInstance(x, y).createTransformedShape(shape);

    return shape;
  }

}
