package org.kharon.renderers;

import java.awt.Graphics;

import org.kharon.GraphShape;
import org.kharon.Node;

public interface LabelRenderer {

  GraphShape render(Graphics g, Node node, RenderContext renderContext);
}
