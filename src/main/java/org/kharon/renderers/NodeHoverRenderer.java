package org.kharon.renderers;

import java.awt.Graphics2D;

import org.kharon.GraphShape;
import org.kharon.NodeBoundingBox;

public interface NodeHoverRenderer {

  GraphShape render(Graphics2D g2d, NodeBoundingBox node, RenderContext renderContext);

}
