package org.kharon.renderers;

import org.kharon.Graph;
import org.kharon.GraphPane;

public class RenderContext {

  private GraphPane graphPanel;
  private Graph graph;

  public RenderContext(GraphPane graphPanel, Graph graph) {
    super();
    this.graphPanel = graphPanel;
    this.graph = graph;
  }

  public Graph getGraph() {
    return graph;
  }

  public GraphPane getGraphPanel() {
    return graphPanel;
  }

}
