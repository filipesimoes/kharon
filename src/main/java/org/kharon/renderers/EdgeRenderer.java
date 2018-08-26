package org.kharon.renderers;

import java.awt.Graphics;

import org.kharon.Edge;

public interface EdgeRenderer {

  void render(Graphics g, Edge edge, RenderContext renderContext);

}
