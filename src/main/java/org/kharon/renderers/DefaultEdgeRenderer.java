package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.GeneralPath;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.GraphShape;
import org.kharon.Node;

public class DefaultEdgeRenderer implements EdgeRenderer {

  @Override
  public GraphShape render(Graphics g, Edge edge, RenderContext renderContext) {
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

    GeneralPath shape = new GeneralPath();
    shape.moveTo(x1, y1);
    shape.lineTo(x2, y2);

    GraphShape graphShape = new GraphShape(shape);

    Color color = edge.getColor();
    if (color == null) {
      Graph graph = renderContext.getGraph();
      color = graph.getSettings().getDefaultEdgeColor();
    }
    graphShape.setStrokePaint(color);
    return graphShape;
  }

}
