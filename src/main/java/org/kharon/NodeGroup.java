package org.kharon;

import java.util.HashSet;
import java.util.Set;

public class NodeGroup extends Node {

  private Set<Node> nodes = new HashSet<>();
  private Set<Edge> edges = new HashSet<>();

  public NodeGroup(String id) {
    super(id);
  }

  public NodeGroup(String id, int x, int y) {
    super(id, x, y);
  }

  public void addNode(Node node) {
    nodes.add(node);
  }

  public Set<Node> getNodes() {
    return nodes;
  }

  public void setNodes(Set<Node> nodes) {
    this.nodes = nodes;
  }

  public void addEdge(Edge edge) {
    this.edges.add(edge);
  }

  public Set<Edge> getEdges() {
    return edges;
  }

  public void setEdges(Set<Edge> edges) {
    this.edges = edges;
  }

}
