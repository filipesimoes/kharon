package br.gov.pf.labld.kharon.renderers;

import java.awt.Graphics;

import br.gov.pf.labld.kharon.NodeBoundingBox;

public interface SelectionRenderer {

  void render(Graphics g, NodeBoundingBox box, RenderContext renderContext);

}
