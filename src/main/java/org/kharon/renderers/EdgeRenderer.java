package org.kharon.renderers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.kharon.Edge;

public interface EdgeRenderer {

  Rectangle2D determineBounds(Graphics2D g2d, Edge edge, RenderContext renderContext);

  void render(Graphics g, Edge edge, RenderContext renderContext, Rectangle2D bounds);

}
