package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import org.kharon.Graph;
import org.kharon.GraphShape;
import org.kharon.Node;

public class CircleNodeRenderer implements NodeRenderer {

  @Override
  public GraphShape render(Graphics2D g, Node node, RenderContext renderContext) {
    int x = node.getX();
    int y = node.getY();
    int size = node.getSize();

    Shape shape = new Ellipse2D.Double(x, y, size, size);

    GraphShape graphShape = new GraphShape(shape);
    Color color = node.getColor();
    if (color == null) {
      Graph graph = renderContext.getGraph();
      color = graph.getSettings().getDefaultNodeColor();
    }
    graphShape.setFillPaint(color);
    return graphShape;
  }

}
