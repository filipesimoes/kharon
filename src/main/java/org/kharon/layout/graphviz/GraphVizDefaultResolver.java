package org.kharon.layout.graphviz;

public class GraphVizDefaultResolver implements GraphVizResolver {

  @Override
  public String resolveBinaryPath(GraphVizAlgorithm algo) {
    return algo.getCmd();
  }

}
