package org.kharon;

import java.awt.Color;

public class GraphSettings {

  private Color defaultEdgeColor = Color.red;
  private Color defaultNodeColor = Color.blue;
  private Color defaultLabelColor = Color.blue;
  private Color defaultSelectionColor = Color.blue;

  private Color selectionColor = Color.blue;

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

}
