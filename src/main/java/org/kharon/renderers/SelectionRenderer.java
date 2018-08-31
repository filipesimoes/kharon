package org.kharon.renderers;

import java.awt.Graphics;

import org.kharon.GraphShape;
import org.kharon.NodeBoundingBox;

public interface SelectionRenderer {

  GraphShape render(Graphics g, NodeBoundingBox boundingBox, RenderContext renderContext);

}
