package br.gov.pf.labld.kharon.renderers;

import java.awt.Graphics;

import br.gov.pf.labld.kharon.Edge;

public interface EdgeRenderer {

  void render(Graphics g, Edge edge, RenderContext renderContext);

}
