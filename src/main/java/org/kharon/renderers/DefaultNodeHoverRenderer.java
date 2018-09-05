package org.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.kharon.GraphSettings;
import org.kharon.GraphShape;
import org.kharon.NodeBoundingBox;

public class DefaultNodeHoverRenderer implements NodeHoverRenderer {

//  private static final Color TRANSP = new Color(255, 255, 255, 0);

  private static final double SCALE = 1.0d;

  @Override
  public GraphShape render(Graphics2D g2d, NodeBoundingBox box, RenderContext renderContext) {
    Rectangle2D bounds = box.getUnionBox(SCALE);

    GraphShape graphShape = new GraphShape(bounds);
    GraphSettings settings = renderContext.getGraph().getSettings();
    Color nodeHoverColor = settings.getNodeHoverColor();

    graphShape.setStrokePaint(nodeHoverColor);
    return graphShape;
  }

}
