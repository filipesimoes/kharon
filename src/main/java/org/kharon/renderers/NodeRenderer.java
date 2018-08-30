package org.kharon.renderers;

import java.awt.Graphics2D;

import org.kharon.GraphShape;
import org.kharon.Node;

public interface NodeRenderer {

  GraphShape render(Graphics2D g, Node node, RenderContext renderContext);

}
