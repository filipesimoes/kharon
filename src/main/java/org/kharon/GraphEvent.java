package org.kharon;

import java.util.Collection;

public class GraphEvent {

  private Object originator;
  private Collection<Node> nodes;
  private Collection<Edge> edges;

  GraphEvent(Object originator, Collection<Node> nodes, Collection<Edge> edges) {
    super();
    this.originator = originator;
    this.nodes = nodes;
    this.edges = edges;
  }

  public Collection<Node> getNodes() {
    return nodes;
  }

  public Collection<Edge> getEdges() {
    return edges;
  }

  public Object getOriginator() {
    return originator;
  }

}
