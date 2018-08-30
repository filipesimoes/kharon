package org.kharon.renderers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.font.GlyphVector;

import org.kharon.Graph;
import org.kharon.GraphShape;
import org.kharon.Node;

public class DefaultLabelRenderer implements LabelRenderer {

  @Override
  public GraphShape render(Graphics g, Node node, RenderContext renderContext) {
    String label = node.getLabel();
    if (label != null) {
      Font font = g.getFont();
      FontMetrics fontMetrics = g.getFontMetrics(font);

      int nodeX = node.getX();
      int nodeY = node.getY();
      int size = node.getSize();
      int labelWidth = fontMetrics.stringWidth(label);
      int labelHeight = fontMetrics.getHeight();

      int labelX = nodeX + (size / 2) - (labelWidth / 2);
      int labelY = nodeY + (size / 2) + labelHeight + (size / 2);

      GlyphVector vector = font.createGlyphVector(fontMetrics.getFontRenderContext(), label);
      Shape shape = vector.getOutline(labelX, labelY);
      GraphShape graphShape = new GraphShape(shape);

      Color color = node.getColor();
      if (color == null) {
        Graph graph = renderContext.getGraph();
        color = graph.getSettings().getDefaultLabelColor();
      }
      graphShape.setFillPaint(color);

      return graphShape;
    } else {
      return null;
    }
  }

}
