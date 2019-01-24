package org.kharon.sample;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.GraphPane;
import org.kharon.GraphPreviewPane;
import org.kharon.GraphWithPreviewPane;
import org.kharon.Node;
import org.kharon.NodeListener;
import org.kharon.StageListener;
import org.kharon.StageMode;
import org.kharon.layout.HierarquicalLayout;
import org.kharon.layout.Layout;
import org.kharon.renderers.Renderers;

public class Sample {

  public static void main(String[] args) throws Exception {
    show();
  }

  public static void show() throws URISyntaxException {

    Graph graph = new Graph();
    Node node0 = new Node("0");
    node0.setLabel("Node 0");
    node0.setX(50 + (int) (Math.random() * 300));
    node0.setY(50 + (int) (Math.random() * 300));
    node0.setType("bug");
    node0.setSize(30);
    graph.addNode(node0);

    Node node1 = new Node("1");
    node1.setLabel("Node 1");
    node1.setX(50 + (int) (Math.random() * 300));
    node1.setY(50 + (int) (Math.random() * 300));
    node1.setType("bug");
    node1.setSize(30);
    graph.addNode(node1);

    Node node2 = new Node("2");
    node2.setLabel("Node 2");
    node2.setX(50 + (int) (Math.random() * 300));
    node2.setY(50 + (int) (Math.random() * 300));
    node2.setType("bug");
    node2.setSize(30);
    graph.addNode(node2);

    Node node3 = new Node("3");
    node3.setLabel("Node 3");
    node3.setX(50 + (int) (Math.random() * 300));
    node3.setY(50 + (int) (Math.random() * 300));
    node3.setType("bug");
    node3.setSize(30);
    graph.addNode(node3);

    Node node4 = new Node("4");
    node4.setLabel("Node 4");
    node4.setX(50 + (int) (Math.random() * 300));
    node4.setY(50 + (int) (Math.random() * 300));
    node4.setType("bug");
    node4.setSize(50);
    graph.addNode(node4);

    Edge edge0 = new Edge("0", node0, node1);
    edge0.setLabel("Label 0 > 1");
    graph.addEdge(edge0);

    Edge edge1 = new Edge("1", node0, node2);
    edge1.setLabel("Label 0 > 2");
    graph.addEdge(edge1);

    Edge edge2 = new Edge("2", node3, node2);
    edge2.setLabel("Label 3 > 2");
    graph.addEdge(edge2);

    Edge edge3 = new Edge("3", node2, node4);
    edge3.setLabel("Label 2 > 4");
    graph.addEdge(edge3);

    // int totalNodes = 20;
    // Node[] nodes = new Node[totalNodes + 1];
    //
    // for (int i = 0; i < totalNodes; i++) {
    // Node node = new Node("" + i);
    // node.setLabel("Node " + i);
    // node.setX(50 + (int) (Math.random() * 2000));
    // node.setY(50 + (int) (Math.random() * 1000));
    // node.setType("bug");
    // node.setSize(30);
    // graph.addNode(node);
    // nodes[i] = node;
    // }
    //
    // Node node = new Node("special");
    // node.setLabel("Very long and special label for this node");
    // node.setX(50 + (int) (Math.random() * 1000));
    // node.setY(50 + (int) (Math.random() * 600));
    // node.setType("bug");
    // node.setSize(30);
    // graph.addNode(node);
    // nodes[totalNodes] = node;
    //
    // for (int i = 0; i < nodes.length; i++) {
    // for (int j = 0; j < 2; j++) {
    // Node target = nodes[(int) ((nodes.length - 1) * Math.random())];
    // Node source = nodes[i];
    // String id = "" + i + "_" + j;
    // Edge edge = new Edge(id, source, target);
    // edge.setLabel(source.getLabel() + " -> " + target.getLabel());
    // graph.addEdge(edge);
    // }
    // }


    JFrame frame = new JFrame("Kharon, ferryman of Hades.");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setPreferredSize(new Dimension(200, 200));

    GraphWithPreviewPane graphWithPreviewPane = new GraphWithPreviewPane(graph);

    GraphPreviewPane previewPane = graphWithPreviewPane.getPreviewPane();
    previewPane.setMinimumSize(new Dimension(200, 200));
    previewPane.setMaximumSize(new Dimension(300, 300));

    final GraphPane graphPane = graphWithPreviewPane.getGraphPane();
    graphPane.setBackground(Color.WHITE);
    graphPane.setHistoryEnabled(true);
    
    Renderers renderers = graphPane.getRenderers();

    renderers.registerNodeRenderer("bug", new BugNodeRenderer());

    graphPane.addNodeListener(new NodeListener() {
      @Override
      public void nodeClicked(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " clicked.");
        int clickCount = e.getClickCount();

        if (clickCount == 1) {
          nodeSingleClicked(node, e);
        }
      }

      private void nodeSingleClicked(Node node, MouseEvent e) {
        boolean selected = graphPane.isNodeSelected(node);
        boolean keepSelection = e.isControlDown() || e.isShiftDown();
        if (!selected) {
          graphPane.selectNode(node.getId(), keepSelection);
        } else if (keepSelection) {
          graphPane.deselectNode(node.getId());
        } else {
          graphPane.selectNode(node.getId());
        }
      }

      @Override
      public void nodeDragStarted(Collection<Node> nodes, MouseEvent e) {
        for (Node node : nodes) {
          if (graphPane.isNodeUnderMouse(node)) {
            boolean keepSelection = graphPane.isNodeSelected(node);
            graphPane.selectNode(node.getId(), keepSelection);
          }
          System.out.println("Node " + node.getId() + " drag started");
        }
        graphPane.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
      }

      @Override
      public void nodeDragStopped(Collection<Node> nodes, MouseEvent e) {
        for (Node node : nodes) {
          nodeDragStopped(node, e);
        }
        graphPane.setCursor(Cursor.getDefaultCursor());
      }

      public void nodeDragStopped(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " drag stopped.");
      }

      @Override
      public void nodeDragged(Collection<Node> nodes, MouseEvent e) {
        // for (Node node : nodes) {
        // System.out.println("Node " + node.getId() + " dragged.");
        // }
      }

      @Override
      public void nodePressed(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " pressed.");
        // nodeSingleClicked(node, e);
      }

      @Override
      public void nodeReleased(Node node, MouseEvent e) {
        System.out.println("Node " + node.getId() + " released.");
      }

      @Override
      public void nodeHover(Node node, MouseEvent e) {
        graphPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      }

      @Override
      public void nodeOut(MouseEvent e) {
        graphPane.setCursor(Cursor.getDefaultCursor());
      }
    });

    graphPane.addStageListener(new StageListener() {

      @Override
      public void stageZoomChanged(double zoom) {
        System.out.println("Zoom changed " + zoom);
      }

      @Override
      public void stageMoved(double x, double y) {
        System.out.println("Stage dragged.");
      }

      @Override
      public void stageDragStopped(MouseEvent e) {
        System.out.println("Stage drag stopped.");
        graphPane.setCursor(Cursor.getDefaultCursor());
        graphPane.setStageMode(StageMode.PAN);
      }

      @Override
      public void stageDragStarted(MouseEvent e) {
        System.out.println("Stage drag started.");
        if (e.isControlDown() || e.isShiftDown()) {
          graphPane.setStageMode(StageMode.SELECTION);
        } else {
          graphPane.setStageMode(StageMode.PAN);
          graphPane.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
      }

      @Override
      public void stageClicked(MouseEvent e) {
        System.out.println("Stage clicked.");
        graphPane.deselectAll();
      }
    });

    graphPane.getActionMap().put("SelectAll", new AbstractAction() {

      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        graphPane.selectAll();
      }
    });
    KeyStroke controlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK);
    InputMap inputMap = graphPane.getInputMap();
    inputMap.put(controlA, "SelectAll");

    graphPane.getActionMap().put("SaveImage", new AbstractAction() {

      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(graphPane);
        if (option == JFileChooser.APPROVE_OPTION) {
          BufferedImage image = graphPane.toImage();
          File selectedFile = fileChooser.getSelectedFile();
          if (!selectedFile.getName().endsWith(".png")) {
            selectedFile = new File(selectedFile.getParentFile(), selectedFile.getName() + ".png");
            try {
              ImageIO.write(image, "png", selectedFile);
            } catch (IOException e1) {
              throw new RuntimeException(e1);
            }
          }
        }
      }
    });
    KeyStroke controlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
    inputMap.put(controlS, "SaveImage");

    graphPane.getActionMap().put("RemoveSelected", new AbstractAction() {

      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        graphPane.removeSelectedNodes();
      }
    });
    KeyStroke delete = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
    inputMap.put(delete, "RemoveSelected");

    graphPane.getActionMap().put("Undo", new AbstractAction() {

      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        graphPane.getHistory().undo();
        graphPane.repaint();
      }
    });
    KeyStroke controlZ = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK);
    inputMap.put(controlZ, "Undo");

    graphPane.getActionMap().put("Redo", new AbstractAction() {

      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        graphPane.getHistory().redo();
        graphPane.repaint();
      }
    });
    KeyStroke controlY = KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK);
    inputMap.put(controlY, "Redo");

    KeyStroke control0 = KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_MASK);
    inputMap.put(control0, "FitToScreen");

    graphPane.getActionMap().put("FitToScreen", new AbstractAction() {

      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        graphPane.fitToScreen();
        graphWithPreviewPane.repaint();
      }
    });

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(graphWithPreviewPane);

    frame.add(panel, BorderLayout.CENTER);
    JLabel comp = new JLabel("Kharon");
    comp.setHorizontalAlignment(JLabel.CENTER);
    frame.add(comp, BorderLayout.NORTH);

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    // frame.setUndecorated(true);
    frame.setVisible(true);
    // graphPane.fitToScreen();
  }

}
