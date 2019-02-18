package org.kharon.layout.graphviz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.kharon.Graph;
import org.kharon.Node;
import org.kharon.layout.AbstractHistoryEnabledLayout.LayoutAction;

public class GraphVizPlainReader {

  private double multiplier = 100d;

  public void read(InputStream in, Graph graph, LayoutAction action) throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
    String line = null;

    while ((line = reader.readLine()) != null) {
      if (line.startsWith("node")) {
        readNode(line, graph, action);
      } else if (line.startsWith("stop")) {
        return;
      }
    }

  }

  private void readNode(String line, Graph graph, LayoutAction action) {
    String[] values = line.split(" ");
    String id = values[1];
    double xd = Double.parseDouble(values[2]) * multiplier;
    double yd = Double.parseDouble(values[3]) * multiplier;

    Node node = graph.getNode(id);
    action.move(node, (int) xd, (int) yd);
  }

}
