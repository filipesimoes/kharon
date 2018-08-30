package org.kharon.renderers;

import java.awt.Graphics;
import java.awt.Shape;
import java.util.Collection;

import org.kharon.GraphShape;

public interface SelectionRenderer {

  GraphShape render(Graphics g, Collection<Shape> shapes, RenderContext renderContext);

}
