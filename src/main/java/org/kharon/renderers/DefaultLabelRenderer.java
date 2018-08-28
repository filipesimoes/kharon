package org.kharon.renderers;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import org.kharon.Graph;
import org.kharon.Node;

public class DefaultLabelRenderer implements LabelRenderer {

  @Override
  public void render(Graphics g, Node node, RenderContext renderContext, Rectangle2D bounds) {
    String label = node.getLabel();
    if (label != null) {
      Color oldColor = g.getColor();

      Color color = node.getColor();
      if (color == null) {
        Graph graph = renderContext.getGraph();
        color = graph.getSettings().getDefaultLabelColor();
      }
      g.setColor(color);

      g.drawString(label, (int) bounds.getX(), (int) bounds.getY());

      g.setColor(oldColor);
    }
  }

  @Override
  public Rectangle2D determineBounds(Graphics g, Node node, RenderContext renderContext) {
    String label = node.getLabel();
    if (label != null) {
      FontMetrics fontMetrics = g.getFontMetrics();

      int nodeX = node.getX();
      int nodeY = node.getY();
      int size = node.getSize();
      int labelWidth = fontMetrics.stringWidth(label);
      int labelHeight = fontMetrics.getHeight();

      int labelX = nodeX + (size / 2) - (labelWidth / 2);
      int labelY = nodeY + (size / 2) + labelHeight + (size / 2);

      return new Rectangle2D.Double(labelX, labelY - labelHeight, labelWidth, labelHeight);
    } else {
      return null;
    }
  }

}
