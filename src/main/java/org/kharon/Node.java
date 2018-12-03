package org.kharon;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

public class Node implements Cloneable {

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

  private AtomicInteger incomingDegree = new AtomicInteger(0);
  private AtomicInteger outcomingDegree = new AtomicInteger(0);

  private Node() {
    super();
  }

  public Node(String id) {
    super();
    this.id = id;
  }

  public Node(String id, int x, int y) {
    this.id = id;
    this.x = x;
    this.y = y;
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
    return incomingDegree.get() + outcomingDegree.get();
  }

  public int getIncomingDegree() {
    return incomingDegree.get();
  }

  void increaseIncomingDegree() {
    this.incomingDegree.incrementAndGet();
  }

  void decreaseIncomingDegree() {
    this.incomingDegree.decrementAndGet();
  }

  public int getOutcomingDegree() {
    return outcomingDegree.get();
  }

  void increaseOutcomingDegree() {
    this.outcomingDegree.incrementAndGet();
  }

  void decreaseOutcomingDegree() {
    this.outcomingDegree.decrementAndGet();
  }

  public boolean isHigherThan(Node o) {
    return y < o.y;
  }

  public boolean isLeftOf(Node o) {
    return x < o.x;
  }

  public static Node getHigher(Node... nodes) {
    Node higher = null;

    for (Node node : nodes) {
      if (higher == null || node.isHigherThan(higher)) {
        higher = node;
      }
    }

    return higher;
  }

  public static Node getLeftmost(Node... nodes) {
    Node leftmost = null;

    for (Node node : nodes) {
      if (leftmost == null || node.isLeftOf(leftmost)) {
        leftmost = node;
      }
    }

    return leftmost;
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

  @Override
  protected Object clone() throws CloneNotSupportedException {
    Node clone = new Node();

    clone.id = id;
    clone.label = label;

    clone.type = type;
    clone.labelType = labelType;
    clone.selectionType = selectionType;

    clone.x = x;
    clone.y = y;
    clone.size = size;

    clone.color = color;
    clone.labelColor = labelColor;

    clone.incomingDegree = new AtomicInteger(incomingDegree.get());

    return clone;
  }

  public double distance(Node neighbour) {
    double px = neighbour.getX() - this.getX();
    double py = neighbour.getY() - this.getY();
    return Math.sqrt(px * px + py * py);
  }

  @Override
  public String toString() {
    return "Node [id=" + id + "]";
  }

  public Rectangle getBoundingBox() {
    return new Rectangle(x, y, size, size);
  }

}
