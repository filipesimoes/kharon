package org.kharon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

import org.kharon.history.GraphAction;
import org.kharon.history.GraphHistory;
import org.kharon.history.GraphHistoryListener;
import org.kharon.renderers.CircleNodeRenderer;
import org.kharon.renderers.DefaultEdgeRenderer;
import org.kharon.renderers.LabelRenderer;
import org.kharon.renderers.RenderContext;
import org.kharon.renderers.Renderers;

public class GraphPreviewPane extends GraphPane {

  private static final long serialVersionUID = -2623035107257555282L;

  private GraphPane graphPane;

  private Rectangle2D stageViewingWindow;

  private Color borderColor = Color.BLACK;
  private Color viewingWindowColor = Color.RED;

  public GraphPreviewPane(GraphPane graphPanel) {
    super(graphPanel.getGraph());
    this.graphPane = graphPanel;

    initPreviewPane();

    this.graphPane.addStageListener(new PreviewStageListener());
    this.graphPane.addNodeListener(new PreviewNodeListener());
    this.graphPane.getHistory().addListener(new PreviewHistoryListener());
    this.graphPane.getGraph().addListener(new PreviewGraphListener());
    PreviewComponentListener previewComponentListener = new PreviewComponentListener();
    this.graphPane.addComponentListener(previewComponentListener);
    this.addComponentListener(previewComponentListener);
  }

  private void initPreviewPane() {
    setStageMode(StageMode.NONE);
    setNodeDragMode(NodeDragMode.NONE);
    setMouseWheelZoomEnabled(false);
    setHistoryEnabled(false);

    initRenderers();
  }

  private void initRenderers() {
    Renderers renderers = getRenderers();

    renderers.clearNodeRenderers();
    renderers.registerNodeRenderer(Renderers.DEFAULT, new CircleNodeRenderer());

    renderers.clearEdgeRenderers();
    renderers.registerEdgeRenderer(Renderers.DEFAULT, new DefaultEdgeRenderer());

    renderers.clearLabelRenderers();
    renderers.registerLabelRenderer(Renderers.DEFAULT, new VoidLabelRenderer());
  }

  private static class VoidLabelRenderer implements LabelRenderer {

    @Override
    public GraphShape render(Graphics g, Node node, RenderContext renderContext) {
      return null;
    }

  }

  private class PreviewHistoryListener implements GraphHistoryListener {

    @Override
    public void historyChanged(GraphHistory history, GraphAction action) {
      setViewingWindow();
    }

  }

  private class PreviewComponentListener extends ComponentAdapter {

    @Override
    public void componentShown(ComponentEvent e) {
      setViewingWindow();
    }

    @Override
    public void componentResized(ComponentEvent e) {
      setViewingWindow();
    }

  }

  private class PreviewGraphListener extends GraphAdapter {

    @Override
    public void elementsAdded(GraphEvent e) {
      setViewingWindow();
    }

    @Override
    public void elementsRemoved(GraphEvent e) {
      setViewingWindow();
    }

  }

  private class PreviewNodeListener extends NodeAdapter {

    @Override
    public void nodeDragStopped(Collection<Node> nodes, MouseEvent e) {
      setViewingWindow();
    }

  }

  private class PreviewStageListener extends StageAdapter {

    @Override
    public void stageDragged(MouseEvent e) {
      setViewingWindow();
    }

    @Override
    public void stageZoomChanged(double zoom) {
      setViewingWindow();
    }

  }

  private void setViewingWindow() {
    Dimension2D graphPaneSize = graphPane.getBounds().getSize();
    double graphZoom = graphPane.getZoom();

    double stageX = -1d * graphPane.getTranslateX() / graphZoom;
    double stageY = -1d * graphPane.getTranslateY() / graphZoom;

    double stageWidth = graphPaneSize.getWidth() / graphZoom;
    double stageHeight = graphPaneSize.getHeight() / graphZoom;

    stageViewingWindow = new Rectangle2D.Double(stageX, stageY, stageWidth, stageHeight);

    Dimension2D size = getSize();
    Rectangle2D minimumBoundingBox = graphPane.getMinimumBoundingBox().createUnion(stageViewingWindow);

    double graphWidth = minimumBoundingBox.getWidth();
    double previewWidth = size.getWidth();

    double zoomFactorWidth = 0.9d * previewWidth / graphWidth;

    double graphHeight = minimumBoundingBox.getHeight();
    double previewHeight = size.getHeight();

    double zoomFactorHeight = 0.9d * previewHeight / graphHeight;

    double zoom = Math.min(zoomFactorWidth, zoomFactorHeight);
    setZoom(zoom);

    double centerX = minimumBoundingBox.getCenterX();
    double centerY = minimumBoundingBox.getCenterY();

    centerStageAt(centerX, centerY);
    stageViewingWindow = transform(stageViewingWindow).getBounds2D();

  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    Rectangle2D bounds = getBounds();

    bounds = new Rectangle2D.Double(0d, 0d, bounds.getWidth() - 1, bounds.getHeight() - 1);
    super.paintComponent(g);

    Color color = g2d.getColor();
    g2d.setPaint(getBorderColor());
    g2d.draw(bounds);

    if (stageViewingWindow != null) {
      g2d.setPaint(getViewingWindowColor());
      g2d.draw(stageViewingWindow);
    }

    g2d.setColor(color);
  }

  public Color getViewingWindowColor() {
    return this.viewingWindowColor;
  }

  public void setViewingWindowColor(Color viewingWindowColor) {
    this.viewingWindowColor = viewingWindowColor;
  }

  public Color getBorderColor() {
    return this.borderColor;
  }

  public void setBorderColor(Color borderColor) {
    this.borderColor = borderColor;
  }

}
