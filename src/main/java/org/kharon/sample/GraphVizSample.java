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
    graph.addNode(new Node("f", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("g", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("h", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("i", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("j", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("k", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("l", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("m", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("n", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("o", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("p", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("q", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("r", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("s", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("t", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("u", (int) (Math.random() * 400), (int) (Math.random() * 500)));
    graph.addNode(new Node("v", (int) (Math.random() * 400), (int) (Math.random() * 500)));

    graph.addEdge(new Edge("0", "a", "b"));
    graph.addEdge(new Edge("1", "a", "c"));
    graph.addEdge(new Edge("2", "a", "d"));

    graph.addEdge(new Edge("3", "b", "e"));
    graph.addEdge(new Edge("4", "b", "f"));
    graph.addEdge(new Edge("5", "b", "g"));
    graph.addEdge(new Edge("6", "b", "h"));
    graph.addEdge(new Edge("7", "b", "i"));
    graph.addEdge(new Edge("8", "b", "j"));

    graph.addEdge(new Edge("9", "c", "k"));
    graph.addEdge(new Edge("10", "c", "l"));
    graph.addEdge(new Edge("11", "c", "m"));
    graph.addEdge(new Edge("12", "c", "n"));
    graph.addEdge(new Edge("13", "c", "o"));
    graph.addEdge(new Edge("14", "c", "p"));

    graph.addEdge(new Edge("15", "d", "q"));
    graph.addEdge(new Edge("16", "d", "r"));
    graph.addEdge(new Edge("17", "d", "s"));
    graph.addEdge(new Edge("18", "d", "t"));
    graph.addEdge(new Edge("19", "d", "u"));
    graph.addEdge(new Edge("20", "d", "v"));

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
