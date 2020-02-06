package org.kharon.layout.graphviz;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.Node;

public class GraphVizLayoutTest {

  private GraphVizLayout layout = new GraphVizLayout(GraphVizAlgorithm.DOT);

  @Test
  public void testFindConnectedGraphsOneConnectedGraphOnly() {

    Graph graph = new Graph();

    Node node0 = new Node("0", 0, 0);
    Node node1 = new Node("1", 0, 1);

    graph.addNode(node0);
    graph.addNode(node1);

    Edge edge0 = new Edge("0", node0, node1);

    graph.addEdge(edge0);

    List<Graph> graphs = layout.getConnectedSubGraphs(graph);

    assertEquals(1, graphs.size());
    assertEquals(2, graphs.get(0).getNodes().size());
    assertEquals(1, graphs.get(0).getEdges().size());
  }

  @Test
  public void testFindConnectedGraphsTwoDisconnectedGraphs() {

    Graph graph = new Graph();

    Node node0 = new Node("0", 0, 0);
    Node node1 = new Node("1", 0, 1);

    graph.addNode(node0);
    graph.addNode(node1);

    Edge edge0 = new Edge("0", node0, node1);
    graph.addEdge(edge0);

    Node node2 = new Node("2", 1, 0);
    Node node3 = new Node("3", 1, 1);

    graph.addNode(node2);
    graph.addNode(node3);

    Edge edge1 = new Edge("1", node2, node3);
    graph.addEdge(edge1);

    List<Graph> graphs = layout.getConnectedSubGraphs(graph);

    assertEquals(2, graphs.size());
    assertEquals(2, graphs.get(0).getNodes().size());
    assertEquals(1, graphs.get(0).getEdges().size());

    assertEquals(2, graphs.get(1).getNodes().size());
    assertEquals(1, graphs.get(1).getEdges().size());
  }

}
