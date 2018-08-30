package org.kharon.renderers;

import java.awt.Graphics;

import org.kharon.Edge;
import org.kharon.GraphShape;

public interface EdgeRenderer {

  GraphShape render(Graphics g, Edge edge, RenderContext renderContext);

}
