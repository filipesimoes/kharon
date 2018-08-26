package org.kharon.renderers;

import java.awt.Graphics;

import org.kharon.NodeBoundingBox;

public interface SelectionRenderer {

  void render(Graphics g, NodeBoundingBox box, RenderContext renderContext);

}
