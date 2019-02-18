package org.kharon.layout.graphviz;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collection;

import org.kharon.Edge;
import org.kharon.Graph;

public class GraphVizWriter {

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
    writer.write("digraph {");
    writerEdges(graph);
    writer.write("}\r\n");
  }

  private void writerEdges(Graph graph) throws IOException {
    Collection<Edge> edges = graph.getEdges();
    for (Edge edge : edges) {
      writeEdge(edge);
    }
  }

  private void writeEdge(Edge edge) throws IOException {
    writer.write(edge.getSource());
    writer.write("->");
    writer.write(edge.getTarget());
    writer.write(";");
  }

}
