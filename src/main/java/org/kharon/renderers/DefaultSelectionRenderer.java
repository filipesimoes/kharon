package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

import org.kharon.Graph;
import org.kharon.GraphSettings;
import org.kharon.GraphShape;

public class DefaultSelectionRenderer implements SelectionRenderer {

  private static final double GAP = 0.2;

  @Override
  public GraphShape render(Graphics g, Collection<Shape> shapes, RenderContext renderContext) {
    Graph graph = renderContext.getGraph();
    GraphSettings settings = graph.getSettings();
    Color color = settings.getDefaultSelectionColor();

    Rectangle2D shape = getUnionBox(shapes);
    GraphShape graphShape = new GraphShape(shape);
    graphShape.setStrokePaint(color);
    return graphShape;
  }

  protected Rectangle2D getUnionBox(Collection<Shape> shapes) {
    double minX = Double.POSITIVE_INFINITY;
    double minY = Double.POSITIVE_INFINITY;

    double maxX = Double.NEGATIVE_INFINITY;
    double maxY = Double.NEGATIVE_INFINITY;

    for (Shape shape : shapes) {
      Rectangle2D bounds = shape.getBounds2D();
      double boundsX = bounds.getX();
      double boundsY = bounds.getY();

      minX = Math.min(minX, boundsX);
      minY = Math.min(minY, boundsY);

      maxX = Math.max(maxX, (boundsX + bounds.getWidth()));
      maxY = Math.max(maxY, (boundsY + bounds.getHeight()));
    }

    double width = Math.abs(maxX - minX);
    double height = Math.abs(maxY - minY);

    return new Rectangle2D.Double(minX - (width * GAP) / 2, minY - (height * GAP) / 2, width * (1 + GAP),
        height * (1 + GAP));
  }

}
