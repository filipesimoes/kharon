package org.kharon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.kharon.renderers.EdgeRenderer;
import org.kharon.renderers.GraphRenderer;
import org.kharon.renderers.LabelRenderer;
import org.kharon.renderers.NodeRenderer;
import org.kharon.renderers.RenderContext;
import org.kharon.renderers.Renderers;
import org.kharon.renderers.SelectionRenderer;

public class GraphPanel extends JComponent
    implements MouseListener, MouseWheelListener, MouseMotionListener, GraphListener {

  private static final long serialVersionUID = 3827345534868023684L;

  private Graph graph;
  private StageMode stageMode = StageMode.PAN;
  private RenderContext renderContext;

  private List<NodeListener> nodeListeners = new ArrayList<>();
  private List<StageListener> stageListeners = new ArrayList<>();

  private boolean showBoundingBoxes = false;

  private boolean nodeDragEnabled = true;

  private boolean mouseWheelZoomEnabled = true;
  private double scale = 1d / 5d;
  private static final double MIN_ZOOM = 1d / 4d;
  private static final double MAX_ZOOM = 5d;

  private boolean isDragging = false;
  private Node draggedNode = null;

  private AffineTransform transform = new AffineTransform();
  private AffineTransform inverseTransform = new AffineTransform();

  private int startDragX;
  private int startDragY;

  private int nodeDragOffsetX;
  private int nodeDragOffsetY;

  private Set<String> selectedNodes = new HashSet<>();
  private Map<String, NodeBoundingBox> boxesIndex = new HashMap<>();

  private Double selectionRectangle;

  public GraphPanel(Graph graph) {
    super();
    this.graph = graph;
    this.renderContext = new RenderContext(this, graph);
    this.addMouseWheelListener(this);
    this.addMouseMotionListener(this);
    this.addMouseListener(this);

    this.graph.addListener(this);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHints(rh);

    AffineTransform oldTransform = g2d.getTransform();
    AffineTransform currentTransformation = (AffineTransform) oldTransform.clone();
    currentTransformation.concatenate(this.transform);

    g2d.setTransform(currentTransformation);

    GraphRenderer graphRenderer = Renderers.getGraphRenderer(this.graph.getType());
    graphRenderer.render(g, this.graph);

    Collection<Edge> edges = graph.getEdges();
    for (Edge edge : edges) {
      EdgeRenderer renderer = Renderers.getEdgeRenderer(edge.getType());
      renderer.render(g, edge, renderContext);
    }

    boxesIndex.clear();
    Collection<Node> nodes = graph.getNodes();
    for (Node node : nodes) {
      NodeBoundingBox box = new NodeBoundingBox();

      NodeRenderer renderer = Renderers.getNodeRenderer(node.getType());
      Rectangle2D boudingBox = renderer.render(g, node, renderContext);
      if (boudingBox != null) {
        box.addBox(boudingBox);
      }

      LabelRenderer labelRenderer = Renderers.getLabelRenderer(node.getLabelType());
      Rectangle2D labelBoundingBox = labelRenderer.render(g2d, node, renderContext);
      if (labelBoundingBox != null) {
        box.addBox(labelBoundingBox);
      }

      if (!box.getBoxes().isEmpty()) {
        boxesIndex.put(node.getId(), box);
      }
    }

    if (this.showBoundingBoxes) {
      for (NodeBoundingBox boundingBox : this.boxesIndex.values()) {
        for (Shape box : boundingBox.getBoxes()) {
          g2d.draw(box);
        }
      }
    }

    for (String selectedId : this.selectedNodes) {
      Node node = this.graph.getNode(selectedId);
      NodeBoundingBox nodeBoundingBox = this.boxesIndex.get(selectedId);
      SelectionRenderer renderer = Renderers.getSelectionRenderer(node.getSelectionType());
      renderer.render(g2d, nodeBoundingBox, renderContext);
    }

    if (this.selectionRectangle != null) {
      Color oldColor = g2d.getColor();
      g2d.setColor(this.graph.getSettings().getSelectionColor());
      g2d.draw(this.selectionRectangle);
      g2d.setColor(oldColor);
    }

    g2d.setTransform(oldTransform);
  }

  public Graph getGraph() {
    return graph;
  }

  public void addNodeListener(NodeListener nodeListener) {
    this.nodeListeners.add(nodeListener);
  }

  public void removeNodeListener(NodeListener nodeListener) {
    this.nodeListeners.remove(nodeListener);
  }

  public void addStageListener(StageListener stageListener) {
    this.stageListeners.add(stageListener);
  }

  public void removeStageListener(StageListener stageListener) {
    this.stageListeners.remove(stageListener);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (this.isEnabled()) {
      Node nodeClicked = getNodeUnderMouse(e);
      if (nodeClicked != null) {
        notifyNodeClicked(nodeClicked, e);
      } else {
        notifyStageClicked(e);
      }
    }
  }

  private void notifyStageClicked(MouseEvent e) {
    for (StageListener stageListener : this.stageListeners) {
      stageListener.stageClicked(e);
    }
  }

  private void notifyZoomChanged(MouseWheelEvent e) {
    for (StageListener stageListener : this.stageListeners) {
      stageListener.stageZoomChanged(e);
    }
  }

  private void notifyStageDragStarted(MouseEvent e) {
    for (StageListener stageListener : this.stageListeners) {
      stageListener.stageDragStarted(e);
    }
  }

  private void notifyStageDragStopped(MouseEvent e) {
    for (StageListener stageListener : this.stageListeners) {
      stageListener.stageDragStopped(e);
    }
  }

  private void notifyStageDragged(MouseEvent e) {
    for (StageListener stageListener : this.stageListeners) {
      stageListener.stageDragged(e);
    }
  }

  private void notifyNodeClicked(Node node, MouseEvent e) {
    for (NodeListener listener : this.nodeListeners) {
      listener.nodeClicked(node, e);
    }
  }

  private void notifyNodeDragStarted(Node node, MouseEvent e) {
    for (NodeListener listener : this.nodeListeners) {
      listener.nodeDragStarted(node, e);
    }
  }

  private void notifyNodeDragStopped(Node node, MouseEvent e) {
    for (NodeListener listener : this.nodeListeners) {
      listener.nodeDragStopped(node, e);
    }
  }

  private void notifyNodeDragged(Node node, MouseEvent e) {
    for (NodeListener listener : this.nodeListeners) {
      listener.nodeDragged(node, e);
    }
  }

  private Node getNodeUnderMouse(MouseEvent evt) {
    Point2D evtPoint = invert(evt.getPoint());
    int x = (int) evtPoint.getX();
    int y = (int) evtPoint.getY();
    for (Entry<String, NodeBoundingBox> entry : boxesIndex.entrySet()) {
      NodeBoundingBox box = entry.getValue();
      if (box.contains(x, y)) {
        String id = entry.getKey();
        return this.graph.getNode(id);
      }
    }
    return null;
  }

  private void applyCurrentSelection(MouseEvent e) {
    if (!e.isControlDown() && !e.isShiftDown()) {
      this.selectedNodes.clear();
    }
    for (Entry<String, NodeBoundingBox> entry : boxesIndex.entrySet()) {
      NodeBoundingBox box = entry.getValue();
      if (box.intersects(selectionRectangle)) {
        String id = entry.getKey();
        this.selectedNodes.add(id);
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    stopDrag(e);
    repaint();
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {
    stopDrag(e);
    repaint();
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    if (this.isEnabled()) {
      int notches = -1 * e.getWheelRotation();
      double zoom = getZoom();
      double newZoom = zoom + (scale * notches);
      newZoom = Math.min(newZoom, MAX_ZOOM);
      newZoom = Math.max(newZoom, MIN_ZOOM);

      if (zoom != newZoom) {
        setZoom(newZoom);
        notifyZoomChanged(e);
        repaint();
      }
    }
  }

  public boolean isMouseWheelZoomEnabled() {
    return mouseWheelZoomEnabled;
  }

  public void setMouseWheelZoomEnabled(boolean mouseWheelZoomEnabled) {
    this.mouseWheelZoomEnabled = mouseWheelZoomEnabled;
    if (!mouseWheelZoomEnabled) {
      this.removeMouseWheelListener(this);
    }
  }

  public double getZoom() {
    return transform.getScaleX();
  }

  public void setZoom(double zoom) {
    double translateX = this.transform.getTranslateX();
    double translateY = this.transform.getTranslateY();
    this.transform.setToScale(zoom, zoom);
    this.transform.translate(translateX, translateY);
    try {
      this.inverseTransform = this.transform.createInverse();
    } catch (NoninvertibleTransformException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void mouseDragged(MouseEvent evt) {
    if (this.isEnabled() && SwingUtilities.isLeftMouseButton(evt)) {
      if (!isDragging) {
        startDrag(evt);
      }
      if (draggedNode != null && this.nodeDragEnabled) {
        dragNode(evt);
        repaint();
      } else if (this.stageMode == StageMode.PAN) {
        dragStage(evt);
        repaint();
      } else if (this.stageMode == StageMode.SELECTION) {
        updateSelection(evt);
        repaint();
      }
    }
  }

  private void updateSelection(MouseEvent evt) {
    Point2D evtLocation = invert(evt.getPoint());
    double x = Math.min(this.startDragX, evtLocation.getX());
    double y = Math.min(this.startDragY, evtLocation.getY());
    double width = Math.abs(this.startDragX - evtLocation.getX());
    double height = Math.abs(this.startDragY - evtLocation.getY());
    if (selectionRectangle == null) {
      this.selectionRectangle = new Rectangle2D.Double(x, y, width, height);
    } else {
      this.selectionRectangle.x = x;
      this.selectionRectangle.y = y;
      this.selectionRectangle.width = width;
      this.selectionRectangle.height = height;
    }
  }

  private void dragStage(MouseEvent evt) {
    Point2D evtLocation = invert(evt.getPoint());
    double x = evtLocation.getX();
    double y = evtLocation.getY();

    double stageOffsetX = x - this.startDragX;
    double stageOffsetY = y - this.startDragY;

    this.transform.translate(stageOffsetX, stageOffsetY);

    try {
      this.inverseTransform = this.transform.createInverse();
    } catch (NoninvertibleTransformException e) {
      throw new RuntimeException(e);
    }

    notifyStageDragged(evt);
  }

  private void dragNode(MouseEvent evt) {
    Point2D evtLocation = invert(evt.getPoint());
    int x = (int) (evtLocation.getX() - this.nodeDragOffsetX);
    int y = (int) (evtLocation.getY() - this.nodeDragOffsetY);

    draggedNode.setX(x);
    draggedNode.setY(y);

    notifyNodeDragged(draggedNode, evt);
  }

  private void startDrag(MouseEvent e) {
    this.isDragging = true;
    this.draggedNode = getNodeUnderMouse(e);

    Point2D startDrag = invert(e.getPoint());
    this.startDragX = (int) startDrag.getX();
    this.startDragY = (int) startDrag.getY();

    if (draggedNode != null) {
      this.nodeDragOffsetX = this.startDragX - draggedNode.getX();
      this.nodeDragOffsetY = this.startDragY - draggedNode.getY();
      notifyNodeDragStarted(draggedNode, e);
    } else {
      notifyStageDragStarted(e);
    }

  }

  private void stopDrag(MouseEvent e) {
    if (this.isDragging) {
      if (this.selectionRectangle != null && this.stageMode == StageMode.SELECTION) {
        applyCurrentSelection(e);
      }

      if (draggedNode != null) {
        notifyNodeDragStopped(draggedNode, e);
      } else {
        notifyStageDragStopped(e);
      }
      this.isDragging = false;
      this.draggedNode = null;
      this.selectionRectangle = null;
    }
  }

  private Point2D invert(Point original) {
    return this.inverseTransform.transform(original, new Point2D.Double());
  }

  public Shape invert(Shape shape) {
    return this.inverseTransform.createTransformedShape(shape);
  }

  public Shape transform(Shape shape) {
    return this.transform.createTransformedShape(shape);
  }

  public double transform(double distance) {
    return this.transform.getScaleX() * distance;
  }

  @Override
  public void mouseMoved(MouseEvent e) {

  }

  public boolean isNodeDragEnabled() {
    return nodeDragEnabled;
  }

  public void setNodeDragEnabled(boolean nodeDragEnabled) {
    this.nodeDragEnabled = nodeDragEnabled;
  }

  public boolean isShowBoundingBoxes() {
    return showBoundingBoxes;
  }

  public void setShowBoundingBoxes(boolean showBoundingBoxes) {
    this.showBoundingBoxes = showBoundingBoxes;
  }

  public Set<String> getSelected() {
    return selectedNodes;
  }

  public boolean isNodeSelected(Node node) {
    return this.selectedNodes.contains(node.getId());
  }

  public Set<Node> getSelectedNodes() {
    return this.graph.getNodes(this.selectedNodes);
  }

  public void removeNode(String id) {
    Node node = this.graph.getNode(id);
    this.graph.removeNode(node);
    repaint();
  }

  public void removeNodes(Set<String> ids) {
    Set<Node> nodes = this.graph.getNodes(ids);
    for (Node node : nodes) {
      this.graph.removeNode(node);
    }
    repaint();
  }

  public void removeSelectedNodes() {
    removeNodes(selectedNodes);
  }

  public void selectNode(String id) {
    this.selectNode(id, false);
  }

  public void selectNode(String id, boolean keepSelection) {
    if (!keepSelection) {
      this.selectedNodes.clear();
    }
    this.selectedNodes.add(id);
    repaint();
  }

  public void deselectAll() {
    this.selectedNodes.clear();
    repaint();
  }

  public void selectAll() {
    this.selectedNodes.addAll(this.graph.getIds());
    repaint();
  }

  @Override
  public void nodeAdded(Node node) {

  }

  @Override
  public void nodeRemoved(Node node) {
    this.selectedNodes.remove(node.getId());
    this.boxesIndex.remove(node.getId());
  }

  @Override
  public void edgeAdded(Edge edge) {

  }

  @Override
  public void edgeRemoved(Edge edge) {

  }

  public StageMode getStageMode() {
    return stageMode;
  }

  public void setStageMode(StageMode stageMode) {
    this.stageMode = stageMode;
  }

}
