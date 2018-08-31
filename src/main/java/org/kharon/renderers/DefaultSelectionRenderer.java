package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;

import org.kharon.Graph;
import org.kharon.GraphSettings;
import org.kharon.GraphShape;
import org.kharon.NodeBoundingBox;

public class DefaultSelectionRenderer implements SelectionRenderer {

  private static final double GAP = 0.2;

  @Override
  public GraphShape render(Graphics g, NodeBoundingBox boundingBox, RenderContext renderContext) {
    Graph graph = renderContext.getGraph();
    GraphSettings settings = graph.getSettings();
    Color color = settings.getDefaultSelectionColor();

    Rectangle2D shape = getUnionBox(boundingBox.getBoxes());
    if (shape != null) {
      GraphShape graphShape = new GraphShape(shape);
      graphShape.setStrokePaint(color);
      return graphShape;
    }
    return null;
  }

  protected Rectangle2D getUnionBox(Collection<Shape> shapes) {
    // double minX = Double.POSITIVE_INFINITY;
    // double minY = Double.POSITIVE_INFINITY;
    //
    // double maxX = Double.NEGATIVE_INFINITY;
    // double maxY = Double.NEGATIVE_INFINITY;

    if (!shapes.isEmpty()) {
      Iterator<Shape> iterator = shapes.iterator();
      Rectangle2D union = iterator.next().getBounds2D();
      while (iterator.hasNext()) {
        Rectangle2D.union(iterator.next().getBounds2D(), union, union);
      }

      double x = union.getX();
      double y = union.getY();
      double width = union.getWidth();
      double height = union.getHeight();
      return new Rectangle2D.Double(x - (width * GAP) / 2, y - (height * GAP) / 2, width * (1 + GAP),
          height * (1 + GAP));
    }

    return null;

    // for (Shape shape : shapes) {
    // Rectangle2D bounds = shape.getBounds2D();
    // double boundsX = bounds.getX();
    // double boundsY = bounds.getY();
    //
    // minX = Math.min(minX, boundsX);
    // minY = Math.min(minY, boundsY);
    //
    // maxX = Math.max(maxX, (boundsX + bounds.getWidth()));
    // maxY = Math.max(maxY, (boundsY + bounds.getHeight()));
    // }
    //
    // double width = Math.abs(maxX - minX);
    // double height = Math.abs(maxY - minY);
    //
    // return new Rectangle2D.Double(minX - (width * GAP) / 2, minY - (height * GAP)
    // / 2, width * (1 + GAP),
    // height * (1 + GAP));
  }

}
