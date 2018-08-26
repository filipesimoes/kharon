package org.kharon.renderers;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import org.kharon.Node;

public interface NodeRenderer {

  Rectangle2D render(Graphics g, Node node, RenderContext renderContext);

}
