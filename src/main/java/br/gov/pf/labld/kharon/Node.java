package br.gov.pf.labld.kharon;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

public class Node {

  private String id;
  private String label;

  private String type = "square";
  private String labelType = "default";
  private String selectionType = "default";

  private int x = 0;
  private int y = 0;
  private int size = 20;

  private Color color;
  private Color labelColor;

  private AtomicInteger degree = new AtomicInteger(0);

  public Node(String id) {
    super();
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Color getLabelColor() {
    return labelColor;
  }

  public void setLabelColor(Color labelColor) {
    this.labelColor = labelColor;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getLabelType() {
    return labelType;
  }

  public void setLabelType(String labelType) {
    this.labelType = labelType;
  }

  public String getSelectionType() {
    return selectionType;
  }

  public void setSelectionType(String selectionType) {
    this.selectionType = selectionType;
  }

  public int getDegree() {
    return degree.get();
  }

  void increaseDegree() {
    this.degree.incrementAndGet();
  }

  void decreaseDegree() {
    this.degree.decrementAndGet();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Node other = (Node) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
