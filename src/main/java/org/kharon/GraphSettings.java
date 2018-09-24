package org.kharon;

import java.awt.Color;

public class GraphSettings implements Cloneable {

  private Color defaultEdgeColor = Color.red;
  private Color defaultNodeColor = Color.blue;
  private Color defaultLabelColor = Color.blue;
  private Color defaultSelectionColor = Color.blue;

  private Color selectionColor = Color.blue;
  private Color nodeHoverColor = Color.CYAN;

  public Color getDefaultEdgeColor() {
    return defaultEdgeColor;
  }

  public void setDefaultEdgeColor(Color defaultEdgeColor) {
    this.defaultEdgeColor = defaultEdgeColor;
  }

  public Color getDefaultNodeColor() {
    return defaultNodeColor;
  }

  public void setDefaultNodeColor(Color defaultNodeColor) {
    this.defaultNodeColor = defaultNodeColor;
  }

  public Color getDefaultLabelColor() {
    return defaultLabelColor;
  }

  public void setDefaultLabelColor(Color defaultLabelColor) {
    this.defaultLabelColor = defaultLabelColor;
  }

  public Color getDefaultSelectionColor() {
    return defaultSelectionColor;
  }

  public void setDefaultSelectionColor(Color defaultSelectionColor) {
    this.defaultSelectionColor = defaultSelectionColor;
  }

  public Color getSelectionColor() {
    return selectionColor;
  }

  public void setSelectionColor(Color selectionColor) {
    this.selectionColor = selectionColor;
  }

  public Color getNodeHoverColor() {
    return nodeHoverColor;
  }

  public void setNodeHoverColor(Color nodeHoverColor) {
    this.nodeHoverColor = nodeHoverColor;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    GraphSettings settings = new GraphSettings();

    settings.defaultEdgeColor = defaultEdgeColor;
    settings.defaultNodeColor = defaultNodeColor;
    settings.defaultLabelColor = defaultLabelColor;
    settings.defaultSelectionColor = defaultSelectionColor;

    settings.selectionColor = selectionColor;
    settings.nodeHoverColor = nodeHoverColor;

    return settings;
  }

}
