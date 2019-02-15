package org.kharon.layout.graphviz;

import java.awt.FontMetrics;
import java.io.InputStream;

import org.kharon.Graph;
import org.kharon.layout.AbstractHistoryEnabledLayout;

public abstract class GraphVizLayout extends AbstractHistoryEnabledLayout  {

  private GraphVizAlgorithm algorithm;

  public GraphVizLayout(GraphVizAlgorithm algo) {
    super();
    this.algorithm = algo;
  }

  @Override
  protected void performLayout(Graph graph, LayoutAction action, FontMetrics fontMetrics) {
    // TODO Auto-generated method stub
    
  }

  private class CommandRunner implements Runnable {
    
    

    
    @Override
    public void run() {
      // TODO Auto-generated method stub

    }

  }

}
