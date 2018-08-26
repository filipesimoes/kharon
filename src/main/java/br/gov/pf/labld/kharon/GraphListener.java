package br.gov.pf.labld.kharon;

public interface GraphListener {

  void nodeAdded(Node node);

  void nodeRemoved(Node node);

  void edgeAdded(Edge edge);

  void edgeRemoved(Edge edge);

}
