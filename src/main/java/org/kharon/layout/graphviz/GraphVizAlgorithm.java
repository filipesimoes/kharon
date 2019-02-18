package org.kharon.layout.graphviz;

public enum GraphVizAlgorithm {

  DOT, NEATO, FDP, SFDP, TWOPI, CIRCO;

  public String getCmd() {
    return name().toLowerCase();
  }

}
