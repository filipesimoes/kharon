package org.kharon.sample;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.GraphPane;
import org.kharon.Node;
import org.kharon.StageAdapter;
import org.kharon.layout.graphviz.GraphVizAlgorithm;
import org.kharon.layout.graphviz.GraphVizLayout;

public class GraphVizSample {

  public static void main(String[] args) throws Exception {
    show();
  }

  public static void show() throws URISyntaxException {

    Graph graph = new Graph();

    graph.addNode(new Node("a", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("b", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("c", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("d", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("e", (int) (Math.random() * 400), (int) (Math.random() * 500)));

    graph.addEdge(new Edge("0", "a", "b"));
    graph.addEdge(new Edge("1", "b", "c"));
    graph.addEdge(new Edge("2", "a", "c"));
    graph.addEdge(new Edge("3", "d", "c"));
    graph.addEdge(new Edge("4", "e", "c"));
    graph.addEdge(new Edge("5", "e", "a"));

    graph.addNode(new Node("f", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("g", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("h", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("i", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("j", (int) (Math.random() * 400), (int) (Math.random() * 500)));

    graph.addEdge(new Edge("6", "f", "g"));
    graph.addEdge(new Edge("7", "g", "h"));
    graph.addEdge(new Edge("8", "f", "h"));
    graph.addEdge(new Edge("9", "i", "h"));
    graph.addEdge(new Edge("10", "j", "h"));
    graph.addEdge(new Edge("11", "j", "f"));

    final GraphPane graphPane = new GraphPane(graph);
    graphPane.setBackground(Color.WHITE);

    graphPane.addStageListener(new LayoutAdapter(graphPane));

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(graphPane);

    JFrame frame = new JFrame("Kharon, ferryman of Hades.");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setPreferredSize(new Dimension(200, 200));

    frame.add(panel, BorderLayout.CENTER);
    JLabel comp = new JLabel("Kharon");
    comp.setHorizontalAlignment(JLabel.CENTER);
    frame.add(comp, BorderLayout.NORTH);

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setVisible(true);
  }

  private static class LayoutAdapter extends StageAdapter {

    private int last = -1;
    private GraphPane graphPane;
    private Object mutex = new Object();

    public LayoutAdapter(GraphPane graphPane) {
      super();
      this.graphPane = graphPane;
    }

    @Override
    public void stageClicked(MouseEvent e) {
      if (e.getClickCount() == 2) {
        last++;
        GraphVizAlgorithm[] algos = GraphVizAlgorithm.values();
        if (last > algos.length - 1) {
          last = 0;
        }
        GraphVizAlgorithm algo = algos[last];
        GraphVizLayout layout = new GraphVizLayout(algo);
        synchronized (mutex) {
          graphPane.applyLayout(layout);
        }
      }
    }

  }

}
