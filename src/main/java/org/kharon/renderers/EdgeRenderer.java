package org.kharon.renderers;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import org.kharon.Edge;

public interface EdgeRenderer {

  Rectangle2D determineBounds(Graphics g, Edge edge, RenderContext renderContext);

  void render(Graphics g, Edge edge, RenderContext renderContext, Rectangle2D bounds);

}
