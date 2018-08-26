package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.kharon.Graph;
import org.kharon.Node;

public abstract class ShapeNodeRenderer implements NodeRenderer {

  @Override
  public Rectangle2D render(Graphics g, Node node, RenderContext renderContext) {
    int x = node.getX();
    int y = node.getY();
    int size = node.getSize();

    Graphics2D g2d = (Graphics2D) g;

    AffineTransform oldTransform = g2d.getTransform();

    g2d.translate(x, y);
    paint(g2d, node, renderContext);

    g2d.setTransform(oldTransform);
    return new Rectangle2D.Double(x, y, size, size);
  }

  public abstract Shape drawShape(Node node, RenderContext renderContext);

  private Shape paint(Graphics2D g, Node node, RenderContext renderContext) {
    Shape shape = drawShape(node, renderContext);

    Rectangle2D bounds = shape.getBounds2D();
    int size = node.getSize();
    double widthScale = size / bounds.getWidth();
    double heigthScale = size / bounds.getHeight();

    AffineTransform tx = new AffineTransform();
    tx.scale(widthScale, heigthScale);
    shape = tx.createTransformedShape(shape);

    Paint oldPaint = g.getPaint();
    Color color = node.getColor();
    if (color == null) {
      Graph graph = renderContext.getGraph();
      color = graph.getSettings().getDefaultNodeColor();
    }
    g.setPaint(color);
    g.fill(shape);
    g.setPaint(oldPaint);

    return renderContext.getGraphPanel().transform(shape);
  }

}
