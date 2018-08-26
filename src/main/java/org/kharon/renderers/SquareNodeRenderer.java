package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.kharon.Graph;
import org.kharon.Node;

public class SquareNodeRenderer implements NodeRenderer {

  @Override
  public Rectangle2D render(Graphics g, Node node, RenderContext renderContext) {

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
    return new Rectangle2D.Double(x, y, size, size);
  }

}
