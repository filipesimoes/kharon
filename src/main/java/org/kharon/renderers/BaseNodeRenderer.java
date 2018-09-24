package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import org.kharon.Graph;
import org.kharon.GraphShape;
import org.kharon.Node;

public abstract class BaseNodeRenderer implements NodeRenderer {

  @Override
  public GraphShape render(Graphics2D g, Node node, RenderContext renderContext) {
    Shape shape = renderShape(node, renderContext);
    GraphShape graphShape = new GraphShape(shape);

    Color color = node.getColor();
    if (color == null) {
      Graph graph = renderContext.getGraph();
      color = graph.getSettings().getDefaultNodeColor();
    }
    graphShape.setFillPaint(color);

    return graphShape;
  }

  public abstract Shape renderShape(Node node, RenderContext renderContext);

}
