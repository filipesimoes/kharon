package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.Node;

public class DefaultEdgeRenderer implements EdgeRenderer {

  @Override
  public void render(Graphics g, Edge edge, RenderContext renderContext, Rectangle2D bounds) {
    String sourceId = edge.getSource();
    String targetId = edge.getTarget();
    Node source = renderContext.getGraph().getNode(sourceId);
    Node target = renderContext.getGraph().getNode(targetId);

    int offset1 = (int) (source.getSize() / 2);

    int x1 = source.getX() + offset1;
    int y1 = source.getY() + offset1;

    int offset2 = (int) (target.getSize() / 2);

    int x2 = target.getX() + offset2;
    int y2 = target.getY() + offset2;

    Color oldColor = g.getColor();

    Color color = edge.getColor();
    if (color == null) {
      Graph graph = renderContext.getGraph();
      color = graph.getSettings().getDefaultEdgeColor();
    }
    g.setColor(color);
    g.drawLine(x1, y1, x2, y2);

    g.setColor(oldColor);
  }

  @Override
  public Rectangle2D determineBounds(Graphics2D g2d, Edge edge, RenderContext renderContext) {
    // TODO Create a EdgeHolder to hold a direct reference to the node.
    String sourceId = edge.getSource();
    String targetId = edge.getTarget();
    Node source = renderContext.getGraph().getNode(sourceId);
    Node target = renderContext.getGraph().getNode(targetId);

    int offset1 = (int) (source.getSize() / 2);

    int x1 = source.getX() + offset1;
    int y1 = source.getY() + offset1;

    int offset2 = (int) (target.getSize() / 2);

    int x2 = target.getX() + offset2;
    int y2 = target.getY() + offset2;

    int w = Math.abs(x1 - x2);
    int h = Math.abs(y1 - y2);

    int x = Math.min(x1, x2);
    int y = Math.min(y1, y2);

    return new Rectangle2D.Double(x, y, w, h);
  }

}
