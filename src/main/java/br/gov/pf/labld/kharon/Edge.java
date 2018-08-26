package br.gov.pf.labld.kharon;

import java.awt.Color;

public class Edge {

  private String id;
  private String source;
  private String target;

  private String type = "default";
  private Color color;

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

}
