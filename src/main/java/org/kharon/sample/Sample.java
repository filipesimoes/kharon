package org.kharon.sample;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.GraphPanel;
import org.kharon.Node;
import org.kharon.NodeListener;
import org.kharon.StageListener;
import org.kharon.StageMode;
import org.kharon.renderers.Renderers;

public class Sample {

  public static void main(String[] args) throws Exception {
    show();
  }

  public static void show() throws URISyntaxException {

    Renderers.registerNodeRenderer("bug", new BugNodeRenderer());

    Graph graph = new Graph();

    int totalNodes = 5;
    Node[] nodes = new Node[totalNodes];

    for (int i = 0; i < totalNodes; i++) {
      Node node = new Node("" + i);
      node.setLabel("Node " + i);
      node.setX(10 + (int) (Math.random() * 500));
      node.setY(10 + (int) (Math.random() * 500));
      node.setType("bug");
      node.setSize(30);
      graph.addNode(node);
      nodes[i] = node;
    }

    for (int i = 0; i < totalNodes; i++) {
      for (int j = 0; j < 2; j++) {
        Node target = nodes[(int) ((totalNodes - 1) * Math.random())];
        Edge edge = new Edge("" + i + "_" + j, nodes[i], target);
        graph.addEdge(edge);
      }
    }

    JFrame frame = new JFrame("Kharon, ferryman of Hades.");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setPreferredSize(new Dimension(200, 200));

    final GraphPanel graphPanel = new GraphPanel(graph);
    graphPanel.addNodeListener(new NodeListener() {
      @Override
      public void nodeClicked(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " clicked.");
        boolean selected = graphPanel.isNodeSelected(node);
        boolean keepSelection = e.isControlDown() || e.isShiftDown();
        if (!selected) {
          graphPanel.selectNode(node.getId(), keepSelection);
        } else if (keepSelection) {
          graphPanel.deselectNode(node.getId());
        } else {
          graphPanel.selectNode(node.getId());
        }
      }

      @Override
      public void nodeDragStarted(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " drag started.");
        graphPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        boolean keepSelection = graphPanel.isNodeSelected(node);
        graphPanel.selectNode(node.getId(), keepSelection);
      }

      @Override
      public void nodeDragStopped(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " drag stopped.");
        graphPanel.setCursor(Cursor.getDefaultCursor());
      }

      @Override
      public void nodeDragged(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " dragged.");
      }

      @Override
      public void nodePressed(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " pressed.");
      }

      @Override
      public void nodeReleased(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " released.");
      }
    });

    graphPanel.addStageListener(new StageListener() {

      @Override
      public void stageZoomChanged(MouseWheelEvent e) {
        System.out.println("Zoom changed " + graphPanel.getZoom());
      }

      @Override
      public void stageDragged(MouseEvent e) {
        System.out.println("Stage dragged.");
      }

      @Override
      public void stageDragStopped(MouseEvent e) {
        System.out.println("Stage drag stopped.");
        graphPanel.setCursor(Cursor.getDefaultCursor());
        graphPanel.setStageMode(StageMode.PAN);
      }

      @Override
      public void stageDragStarted(MouseEvent e) {
        System.out.println("Stage drag started.");
        if (e.isControlDown() || e.isShiftDown()) {
          graphPanel.setStageMode(StageMode.SELECTION);
        } else {
          graphPanel.setStageMode(StageMode.PAN);
          graphPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
      }

      @Override
      public void stageClicked(MouseEvent e) {
        System.out.println("Stage clicked.");
        graphPanel.deselectAll();
      }
    });

    graphPanel.getActionMap().put("SelectAll", new AbstractAction() {

      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        graphPanel.selectAll();
      }
    });
    KeyStroke controlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK);
    InputMap inputMap = graphPanel.getInputMap();
    inputMap.put(controlA, "SelectAll");

    graphPanel.getActionMap().put("RemoveSelected", new AbstractAction() {

      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        graphPanel.removeSelectedNodes();
      }
    });
    KeyStroke delete = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
    inputMap.put(delete, "RemoveSelected");

    graphPanel.setShowBoundingBoxes(true);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(graphPanel);

    frame.add(panel, BorderLayout.CENTER);
    JLabel comp = new JLabel("Kharon");
    comp.setHorizontalAlignment(JLabel.CENTER);
    frame.add(comp, BorderLayout.NORTH);

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    // frame.setUndecorated(true);
    frame.setVisible(true);
  }

}
