package br.gov.pf.labld.kharon.renderers;

import br.gov.pf.labld.kharon.Graph;
import br.gov.pf.labld.kharon.GraphPanel;

public class RenderContext {

  private GraphPanel graphPanel;
  private Graph graph;

  public RenderContext(GraphPanel graphPanel, Graph graph) {
    super();
    this.graphPanel = graphPanel;
    this.graph = graph;
  }

  public Graph getGraph() {
    return graph;
  }

  public GraphPanel getGraphPanel() {
    return graphPanel;
  }

}
