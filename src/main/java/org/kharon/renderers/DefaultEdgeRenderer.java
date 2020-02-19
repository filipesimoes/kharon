package org.kharon.renderers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.GraphShape;
import org.kharon.Node;
import org.kharon.OverlappedEdges;

public class DefaultEdgeRenderer implements EdgeRenderer {

  private boolean renderLabels = true;

  public DefaultEdgeRenderer() {
    this(true);
  }

  public DefaultEdgeRenderer(boolean renderLabels) {
    super();
    this.renderLabels = renderLabels;
  }

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

    double slope = Math.atan2(y1 - y2, x2 - x1);
    double pSlope = slope + (Math.PI / 2);
    final double tickness = 1d;

    GeneralPath shape = new GeneralPath();
    double ticknessOffsetX = tickness * Math.cos(pSlope) / 2d;
    double ticknessOffsetY = tickness * -1 * Math.sin(pSlope) / 2d;

    shape.moveTo(x1 - ticknessOffsetX, y1 - ticknessOffsetY);
    shape.lineTo(x2 - ticknessOffsetX, y2 - ticknessOffsetY);
    shape.lineTo(x2 + ticknessOffsetX, y2 + ticknessOffsetY);
    shape.lineTo(x1 + ticknessOffsetX, y1 + ticknessOffsetY);
    shape.closePath();

    Font font = g.getFont();
    FontMetrics fontMetrics = g.getFontMetrics(font);

    boolean isOverlapped = edge instanceof OverlappedEdges; 
    String label = !isOverlapped ? edge.getLabel() : "[" + ((OverlappedEdges)edge).getEdgeCount() + "]";
    double distance = Point2D.distance(x1, y1, x2, y2);

    int labelWidth = label != null ? fontMetrics.stringWidth(label) : 0;
    if (renderLabels && label != null && !label.trim().isEmpty() && distance > labelWidth) {

      if (slope < 0d) {
        slope = 2d * Math.PI + slope;
      }

      int labelHeight = fontMetrics.getHeight();

      double offsetHeight = 0.2d * labelHeight;

      double labelSlope;
      double offsetDistance;
      if (slope > (Math.PI / 2d) && slope < (Math.PI * 3d / 2d)) {
        labelSlope = Math.PI - slope;
        offsetHeight = -1d * offsetHeight;
        offsetDistance = (distance / 2d) + (labelWidth / 2);
      } else {
        labelSlope = 2 * Math.PI - slope;
        offsetDistance = (distance / 2d) - (labelWidth / 2);
      }

      double incX = Math.cos(slope) * offsetDistance + offsetHeight * Math.cos(pSlope);
      double incY = -1 * Math.sin(slope) * offsetDistance - offsetHeight * Math.sin(pSlope);
      int labelX = (int) (x1 + incX);
      int labelY = (int) (y1 + incY);

      GlyphVector vector = font.createGlyphVector(fontMetrics.getFontRenderContext(), label);
      for (int index = 0; index < vector.getNumGlyphs(); index++) {
        vector.setGlyphTransform(index, AffineTransform.getRotateInstance(labelSlope));
      }

      Shape labelShape = vector.getOutline(labelX, labelY);

      shape.append(labelShape, false);
    }
    
    if(!isOverlapped || !((OverlappedEdges)edge).isDoubleDirection()) {
        
        int x1Arrow = x2;
        int y1Arrow = y2;
        int size = target.getSize();
        
        if(isOverlapped && ((OverlappedEdges)edge).isReverseDirection()) {
            x1Arrow = x1;
            y1Arrow = y1;
            size = source.getSize();
            slope = Math.atan2(y2 - y1, x1 - x2);
        }

        x1Arrow = (int) (x1Arrow - Math.cos(slope) * size);
        y1Arrow = (int) (y1Arrow + Math.sin(slope) * size);
    
        int x2Arrow = (int) (x1Arrow + Math.cos(slope + 3 * Math.PI / 4) * 10);
        int y2Arrow = (int) (y1Arrow - Math.sin(slope + 3 * Math.PI / 4) * 10);
    
        int x3Arrow = (int) (x1Arrow + Math.cos(slope - 3 * Math.PI / 4) * 10);
        int y3Arrow = (int) (y1Arrow - Math.sin(slope - 3 * Math.PI / 4) * 10);
    
        GeneralPath arrow = new GeneralPath();
        arrow.moveTo(x1Arrow, y1Arrow);
        arrow.lineTo(x2Arrow, y2Arrow);
        arrow.lineTo(x3Arrow, y3Arrow);
        arrow.closePath();
        
        shape.append(arrow, false);

    }
        
    GraphShape graphShape = new GraphShape(shape);

    Color color = edge.getColor();
    if (color == null) {
      Graph graph = renderContext.getGraph();
      color = graph.getSettings().getDefaultEdgeColor();
    }
    graphShape.setStrokePaint(color);
    graphShape.setFillPaint(color);
    return graphShape;
  }

}
