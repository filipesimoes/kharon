package br.gov.pf.labld.kharon.renderers;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import br.gov.pf.labld.kharon.Node;

public interface LabelRenderer {

  Rectangle2D render(Graphics g, Node node, RenderContext renderContext);
}
