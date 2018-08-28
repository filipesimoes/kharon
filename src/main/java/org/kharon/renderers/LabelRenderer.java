package org.kharon.renderers;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import org.kharon.Node;

public interface LabelRenderer {

  Rectangle2D determineBounds(Graphics g, Node node, RenderContext renderContext);

  void render(Graphics g, Node node, RenderContext renderContext, Rectangle2D bounds);
}
