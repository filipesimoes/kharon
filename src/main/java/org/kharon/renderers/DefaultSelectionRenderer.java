package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import org.kharon.Graph;
import org.kharon.GraphSettings;
import org.kharon.GraphShape;
import org.kharon.NodeBoundingBox;
import org.kharon.ShapeUtils;

public class DefaultSelectionRenderer implements SelectionRenderer {

  private static final double SCALE = 1.0;

  @Override
  public GraphShape render(Graphics g, NodeBoundingBox boundingBox, RenderContext renderContext) {
    Graph graph = renderContext.getGraph();
    GraphSettings settings = graph.getSettings();
    Color color = settings.getDefaultSelectionColor();

    Rectangle2D shape = ShapeUtils.getUnionBox(boundingBox.getBoxes(), SCALE);
    if (shape != null) {
      GraphShape graphShape = new GraphShape(shape);
      graphShape.setStrokePaint(color);
      return graphShape;
    }
    return null;
  }

}
