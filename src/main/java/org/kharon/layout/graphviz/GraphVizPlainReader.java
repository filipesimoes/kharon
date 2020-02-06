package org.kharon.layout.graphviz;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.kharon.Graph;
import org.kharon.Node;
import org.kharon.layout.AbstractHistoryEnabledLayout.LayoutAction;

public class GraphVizPlainReader {

  private double multiplier = 200d;

  public Rectangle read(InputStream in, Graph graph, int left, int middle, LayoutAction action) throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
    String line = null;

    Map<String, Point> oldPositions = new HashMap<>();

    while ((line = reader.readLine()) != null) {
      if (line.startsWith("node")) {

        String[] values = line.split(" ");

        String id = values[1];
        int newX = (int) (Double.parseDouble(values[2]) * multiplier);
        int newY = (int) (Double.parseDouble(values[3]) * multiplier);

        Node node = graph.getNode(id);

        oldPositions.put(id, new Point(node.getX(), node.getY()));

        node.setX(newX);
        node.setY(newY);

      } else if (line.startsWith("stop")) {
        break;
      }
    }

    Rectangle rect = graph.getBoundingBox();
    int top = middle - (int) (rect.getHeight() / 2);

    for (Node node : graph.getNodes()) {
      int newX = left + node.getX();
      int newY = top + node.getY();

      Point oldPos = oldPositions.get(node.getId());

      int oldX = (int) oldPos.getX();
      int oldY = (int) oldPos.getY();
      action.move(node, oldX, oldY, newX, newY);
    }

    return graph.getBoundingBox();
  }

}
