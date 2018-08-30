package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.kharon.Graph;
import org.kharon.GraphShape;
import org.kharon.Node;

public abstract class ShapeNodeRenderer implements NodeRenderer {

  public abstract Shape drawShape(Node node, RenderContext renderContext);

  @Override
  public GraphShape render(Graphics2D g, Node node, RenderContext renderContext) {
    Shape shape = drawShape(node, renderContext);

    Rectangle2D bounds = shape.getBounds2D();
    int size = node.getSize();
    double widthScale = size / bounds.getWidth();
    double heigthScale = size / bounds.getHeight();
    int x = node.getX();
    int y = node.getY();

    shape = AffineTransform.getScaleInstance(widthScale, heigthScale).createTransformedShape(shape);
    shape = AffineTransform.getTranslateInstance(x, y).createTransformedShape(shape);

    Color color = node.getColor();
    if (color == null) {
      Graph graph = renderContext.getGraph();
      color = graph.getSettings().getDefaultNodeColor();
    }
    GraphShape graphShape = new GraphShape(shape);
    graphShape.setFillPaint(color);
    return graphShape;
  }

}
