package br.gov.pf.labld.kharon.renderers;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import br.gov.pf.labld.kharon.Graph;
import br.gov.pf.labld.kharon.Node;

public class DefaultLabelRenderer implements LabelRenderer {

  @Override
  public Rectangle2D render(Graphics g, Node node, RenderContext renderContext) {
    String label = node.getLabel();
    if (label != null) {
      Color oldColor = g.getColor();

      FontMetrics fontMetrics = g.getFontMetrics();

      int nodeX = node.getX();
      int nodeY = node.getY();
      int size = node.getSize();
      int labelWidth = fontMetrics.stringWidth(label);
      int labelHeight = fontMetrics.getHeight();

      int labelX = nodeX + (size / 2) - (labelWidth / 2);
      int labelY = nodeY + (size / 2) + labelHeight + (size / 2);

      Color color = node.getColor();
      if (color == null) {
        Graph graph = renderContext.getGraph();
        color = graph.getSettings().getDefaultLabelColor();
      }
      g.setColor(color);

      g.drawString(label, labelX, labelY);

      g.setColor(oldColor);

      return new Rectangle2D.Double(labelX, labelY - labelHeight, labelWidth, labelHeight);
    } else {
      return null;
    }
  }

}
