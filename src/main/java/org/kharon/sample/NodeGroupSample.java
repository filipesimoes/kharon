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
import org.kharon.NodeAdapter;
import org.kharon.NodeGroup;

public class NodeGroupSample {

  public static void main(String[] args) throws Exception {
    show();
  }

  public static void show() throws URISyntaxException {

    Graph graph = new Graph();

    NodeGroup group0 = new NodeGroup("g0");
    group0.setLabel("Group 0");
    group0.setX(50 + (int) (Math.random() * 500));
    group0.setY(50 + (int) (Math.random() * 300));
    group0.setSize(30);
    graph.addNode(group0);

    Node node0 = new Node("0");
    node0.setLabel("Node 0");
    group0.addNode(node0);

    Node node1 = new Node("1");
    node1.setLabel("Node 1");
    group0.addNode(node1);

    group0.addEdge(new Edge("0", node0, node1));

    NodeGroup group1 = new NodeGroup("g1");
    group1.setLabel("Group 1");
    group1.setX(50 + (int) (Math.random() * 500));
    group1.setY(50 + (int) (Math.random() * 300));
    group1.setSize(30);
    graph.addNode(group1);

    Node node2 = new Node("2");
    node2.setLabel("Node 2");
    group1.addNode(node2);

    Node node3 = new Node("3");
    node3.setLabel("Node 3");
    group1.addNode(node3);

    group0.addEdge(new Edge("1", node2, node3));

    graph.addEdge(new Edge("g0_g1", group0, group1));

    final GraphPane graphPane = new GraphPane(graph);
    graphPane.setBackground(Color.WHITE);
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(graphPane);

    graphPane.addNodeListener(new NodeAdapter() {

      @Override
      public void nodeClicked(Node node, MouseEvent e) {
        if (node instanceof NodeGroup && e.getClickCount() == 2) {
          graph.ungroup((NodeGroup) node);
        }
      }

    });

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

}
