package org.kharon;

import java.awt.Color;

public class Edge implements Cloneable {

  private String id;
  private String source;
  private String target;
  
  private String label;

  private String type = "default";
  private Color color;

  private Edge() {
    super();
  }

  public Edge(String id, Node source, Node target) {
    this.id = id;
    this.source = source.getId();
    this.target = target.getId();
  }

  public Edge(String id, String source, String target) {
    super();
    this.id = id;
    this.source = source;
    this.target = target;
  }

  public String getId() {
    return id;
  }

  public String getSource() {
    return source;
  }

  public String getTarget() {
    return target;
  }

  public String getType() {
    return type;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
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
    Edge other = (Edge) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    Edge clone = new Edge();

    clone.id = id;
    clone.source = source;
    clone.target = target;

    clone.type = type;
    clone.color = color;

    return clone;
  }

}
