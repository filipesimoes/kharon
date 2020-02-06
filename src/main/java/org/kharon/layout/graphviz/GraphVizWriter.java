package org.kharon.layout.graphviz;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Set;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.Node;

public class GraphVizWriter {

  private double divider = 200d;

  private BufferedWriter writer;

  public GraphVizWriter(OutputStream out) {
    super();
    this.writer = new BufferedWriter(new OutputStreamWriter(out, Charset.forName("UTF-8")));
  }

  public void write(Graph graph) throws IOException {
    writeDigraph(graph);
    writer.flush();
  }

  private void writeDigraph(Graph graph) throws IOException {
    writer.write("digraph { overlap = false; splines = line;");
    writeNodes(graph);
    writeEdges(graph);
    writer.write("}\r\n");
  }

  private void writeEdges(Graph graph) throws IOException {
    Collection<Edge> edges = graph.getEdges();
    for (Edge edge : edges) {
      writeEdge(edge);
    }
  }

  private void writeNodes(Graph graph) throws IOException {
    Set<Node> nodes = graph.getNodes();
    for (Node node : nodes) {
      writeNode(node);
    }
  }

  private void writeNode(Node node) throws IOException {
    String size = Double.toString(node.getSize() / divider);
    
    writer.write(node.getId());
    writer.write(" [shape=box,");
    writer.write("height=");
    writer.write(size);
    writer.write(",width=");
    writer.write(size);
    writer.write("];");
  }

  private void writeEdge(Edge edge) throws IOException {
    writer.write(edge.getSource());
    writer.write("->");
    writer.write(edge.getTarget());
    writer.write(";");
  }

}
