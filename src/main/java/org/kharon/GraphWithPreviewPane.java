package org.kharon;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JLayeredPane;

public class GraphWithPreviewPane extends JLayeredPane implements ComponentListener {

  private static final long serialVersionUID = 300661421488051861L;

  private GraphPane graphPane;
  private GraphPreviewPane previewPane;

  private double previewSizeWeight = .3d;

  public GraphWithPreviewPane(Graph graph) {
    super();
    this.graphPane = new GraphPane(graph);
    init();
  }

  private void init() {
    this.previewPane = new GraphPreviewPane(graphPane);

    add(graphPane, JLayeredPane.DEFAULT_LAYER);
    add(previewPane, JLayeredPane.PALETTE_LAYER);

    addComponentListener(this);
  }

  public GraphPane getGraphPane() {
    return graphPane;
  }

  public GraphPreviewPane getPreviewPane() {
    return previewPane;
  }

  @Override
  public void componentResized(ComponentEvent e) {
    Rectangle bounds = getBounds();
    graphPane.setBounds(bounds);
    Rectangle previewBounds = getPreviewBounds(bounds);
    previewPane.setBounds(previewBounds);
  }

  private Rectangle getPreviewBounds(Rectangle2D graphBounds) {

    int width = (int) (graphBounds.getWidth() * previewSizeWeight);
    int height = (int) (graphBounds.getHeight() * previewSizeWeight);

    Dimension minimumSize = previewPane.getMinimumSize();
    if (minimumSize != null) {
      width = Math.max(width, minimumSize.width);
      height = Math.max(height, minimumSize.height);
    }

    Dimension maximumSize = previewPane.getMaximumSize();
    if (maximumSize != null) {
      width = Math.min(width, maximumSize.width);
      height = Math.min(height, maximumSize.height);
    }

    int x = (int) (graphBounds.getX() + graphBounds.getWidth() - width);
    int y = (int) (graphBounds.getY() + graphBounds.getHeight() - height);

    return new Rectangle(x, y, width, height);
  }

  @Override
  public void componentMoved(ComponentEvent e) {

  }

  @Override
  public void componentShown(ComponentEvent e) {

  }

  @Override
  public void componentHidden(ComponentEvent e) {

  }

  public double getPreviewSizeWeigth() {
    return previewSizeWeight;
  }

  public void setPreviewSizeWeigth(double previewSizeWeigth) {
    this.previewSizeWeight = previewSizeWeigth;
  }

  public double getPreviewSizeWeight() {
    return previewSizeWeight;
  }

  public void setPreviewSizeWeight(double previewSizeWeight) {
    this.previewSizeWeight = previewSizeWeight;
  }

}
