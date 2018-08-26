package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.Node;

public class DefaultEdgeRenderer implements EdgeRenderer {

  @Override
  public void render(Graphics g, Edge edge, RenderContext renderContext) {
    Color oldColor = g.getColor();

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

    Color color = edge.getColor();
    if (color == null) {
      Graph graph = renderContext.getGraph();
      color = graph.getSettings().getDefaultEdgeColor();
    }
    g.setColor(color);
    g.drawLine(x1, y1, x2, y2);

    g.setColor(oldColor);
  }

}
