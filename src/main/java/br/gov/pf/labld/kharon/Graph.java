package br.gov.pf.labld.kharon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {

  private String type = "default";

  private GraphSettings settings = new GraphSettings();

  private Map<String, NodeHolder> nodeIndex = new HashMap<>();
  private Map<String, Edge> edgeIndex = new HashMap<>();

  private List<GraphListener> listeners = new ArrayList<>();

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Set<Node> getNodes() {
    return nodeIndex.values().stream().map(s -> s.getNode()).collect(Collectors.toSet());
  }

  public Set<Node> getNodes(Collection<String> ids) {
    Set<Node> nodes = new HashSet<>(ids.size());
    for (String id : ids) {
      Node node = this.nodeIndex.get(id).getNode();
      if (node != null) {
        nodes.add(node);
      } else {
        throw new IllegalArgumentException("Node " + id + " not found.");
      }
    }
    return nodes;
  }

  public boolean containsNode(String id) {
    return this.nodeIndex.containsKey(id);
  }

  public Collection<Edge> getEdges() {
    return edgeIndex.values();
  }

  public void addNode(Node node) {
    this.nodeIndex.put(node.getId(), new NodeHolder(node));
    notifyNodeAdded(node);
  }

  public void removeNode(Node node) {
    String id = node.getId();
    if (this.nodeIndex.containsKey(id)) {
      NodeHolder nodeHolder = getNodeHolder(id);

      for (Edge edge : nodeHolder.getEdges()) {
        removeEdge(edge);
      }
      this.nodeIndex.remove(id);
      notifyNodeRemoved(node);
    }
  }

  public void addEdge(Edge edge) {
    this.edgeIndex.put(edge.getId(), edge);

    String source = edge.getSource();
    NodeHolder sourceHolder = getNodeHolder(source);
    sourceHolder.addEdge(edge);
    sourceHolder.getNode().increaseDegree();

    String target = edge.getTarget();
    NodeHolder targetHolder = getNodeHolder(target);
    targetHolder.getNode().increaseDegree();
    targetHolder.addEdge(edge);

    notifyEdgeAdded(edge);
  }

  public void removeEdge(Edge edge) {
    Edge removed = this.edgeIndex.remove(edge.getId());
    if (removed != null) {
      String source = edge.getSource();
      NodeHolder sourceHolder = getNodeHolder(source);
      sourceHolder.getNode().decreaseDegree();

      String target = edge.getTarget();
      NodeHolder targetHolder = getNodeHolder(target);
      targetHolder.getNode().decreaseDegree();

      notifyEdgeRemoved(edge);
    }
  }

  public boolean containsEdge(String id) {
    return this.edgeIndex.containsKey(id);
  }

  public GraphSettings getSettings() {
    return settings;
  }

  public Node getNode(String id) {
    return nodeIndex.get(id).getNode();
  }

  private NodeHolder getNodeHolder(String id) {
    return nodeIndex.get(id);
  }

  public Set<String> getIds() {
    return this.nodeIndex.keySet();
  }

  public void addListener(GraphListener listener) {
    this.listeners.add(listener);
  }

  public void removeListener(GraphListener listener) {
    this.listeners.remove(listener);
  }

  private void notifyNodeAdded(Node node) {
    for (GraphListener listener : this.listeners) {
      listener.nodeAdded(node);
    }
  }

  private void notifyNodeRemoved(Node node) {
    for (GraphListener listener : this.listeners) {
      listener.nodeRemoved(node);
    }
  }

  private void notifyEdgeAdded(Edge edge) {
    for (GraphListener listener : this.listeners) {
      listener.edgeAdded(edge);
    }
  }

  private void notifyEdgeRemoved(Edge edge) {
    for (GraphListener listener : this.listeners) {
      listener.edgeRemoved(edge);
    }
  }

  private static class NodeHolder {

    private Node node;

    private Map<String, Edge> incoming = new HashMap<>();
    private Map<String, Edge> outcoming = new HashMap<>();

    public NodeHolder(Node node) {
      super();
      this.node = node;
    }

    public void addEdge(Edge edge) {
      String source = edge.getSource();
      String target = edge.getTarget();
      String id = node.getId();
      if (id.equals(source)) {
        outcoming.put(edge.getId(), edge);
      } else if (id.equals(target)) {
        incoming.put(edge.getId(), edge);
      }
    }

    public Set<Edge> getEdges() {
      Set<Edge> edges = new HashSet<>(incoming.values());
      edges.addAll(outcoming.values());
      return edges;
    }

    public Node getNode() {
      return node;
    }

  }

}
