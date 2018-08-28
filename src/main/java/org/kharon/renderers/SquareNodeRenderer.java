package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.kharon.Graph;
import org.kharon.Node;

public class SquareNodeRenderer implements NodeRenderer {

  @Override
  public void render(Graphics g, Node node, RenderContext renderContext, Rectangle2D bounds) {

    Color oldColor = g.getColor();

    Graphics2D g2 = (Graphics2D) g;
    int x = node.getX();
    int y = node.getY();
    int size = node.getSize();

    Color color = node.getColor();
    if (color == null) {
      Graph graph = renderContext.getGraph();
      color = graph.getSettings().getDefaultNodeColor();
    }
    g.setColor(color);
    g2.fillRect(x, y, size, size);

    g.setColor(oldColor);
  }

  @Override
  public Rectangle2D determineBounds(Graphics g, Node node, RenderContext renderContext) {
    int x = node.getX();
    int y = node.getY();
    int size = node.getSize();
    return new Rectangle2D.Double(x, y, size, size);
  }

}
