package br.gov.pf.labld.kharon.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.List;

import br.gov.pf.labld.kharon.Graph;
import br.gov.pf.labld.kharon.GraphSettings;
import br.gov.pf.labld.kharon.NodeBoundingBox;

public class DefaultSelectionRenderer implements SelectionRenderer {

  private static final double GAP = 0.2;

  @Override
  public void render(Graphics g, NodeBoundingBox box, RenderContext renderContext) {
    Color oldColor = g.getColor();
    Graph graph = renderContext.getGraph();
    GraphSettings settings = graph.getSettings();
    Color color = settings.getDefaultSelectionColor();
    g.setColor(color);

    Graphics2D g2d = (Graphics2D) g;
    g2d.draw(getUnionBox(box));

    g.setColor(oldColor);
  }

  protected Rectangle2D getUnionBox(NodeBoundingBox box) {
    List<Shape> boxes = box.getBoxes();
    double minX = Double.POSITIVE_INFINITY;
    double minY = Double.POSITIVE_INFINITY;

    double maxX = Double.NEGATIVE_INFINITY;
    double maxY = Double.NEGATIVE_INFINITY;

    for (Shape boundingBox : boxes) {
      Rectangle2D bounds = boundingBox.getBounds2D();
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
