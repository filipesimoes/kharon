package org.kharon.renderers;

import java.util.HashMap;
import java.util.Map;

public class Renderers {

  public static final String DEFAULT = "default";

  private boolean renderUnknownAsDefault = true;

  private Map<String, GraphRenderer> graphRenderers;
  private Map<String, NodeRenderer> nodeRenderers;
  private Map<String, EdgeRenderer> edgeRenderers;
  private Map<String, LabelRenderer> labelRenderers;
  private Map<String, SelectionRenderer> selectionRenderers;
  private Map<String, NodeHoverRenderer> hoverRenderers;

  public Renderers() {
    super();
    initDefaults();
  }

  private void initDefaults() {
    graphRenderers = new HashMap<>();
    graphRenderers.put(DEFAULT, new DefaultGraphRenderer());

    nodeRenderers = new HashMap<>();
    CircleNodeRenderer circle = new CircleNodeRenderer();
    nodeRenderers.put(DEFAULT, circle);
    nodeRenderers.put("circle", circle);
    nodeRenderers.put("square", new SquareNodeRenderer());

    edgeRenderers = new HashMap<>();
    edgeRenderers.put(DEFAULT, new DefaultEdgeRenderer());

    labelRenderers = new HashMap<>();
    labelRenderers.put(DEFAULT, new DefaultLabelRenderer());

    selectionRenderers = new HashMap<>();
    selectionRenderers.put(DEFAULT, new DefaultSelectionRenderer());

    hoverRenderers = new HashMap<>();
    hoverRenderers.put(DEFAULT, new DefaultNodeHoverRenderer());
  }

  public GraphRenderer getGraphRenderer(String type) {
    GraphRenderer graphRenderer = graphRenderers.get(type);
    if (graphRenderer == null && !renderUnknownAsDefault) {
      throw new IllegalArgumentException("Unknown graph type (" + type + ").");
    } else if (graphRenderer == null && renderUnknownAsDefault) {
      graphRenderer = graphRenderers.get(DEFAULT);
    }
    return graphRenderer;
  }

  public NodeRenderer getNodeRenderer(String type) {
    NodeRenderer nodeRenderer = nodeRenderers.get(type);
    if (nodeRenderer == null && !renderUnknownAsDefault) {
      throw new IllegalArgumentException("Unknown node type (" + type + ").");
    } else if (nodeRenderer == null && renderUnknownAsDefault) {
      nodeRenderer = nodeRenderers.get(DEFAULT);
    }
    return nodeRenderer;
  }

  public void registerNodeRenderer(String type, NodeRenderer renderer) {
    nodeRenderers.put(type, renderer);
  }

  public void clearNodeRenderers() {
    nodeRenderers.clear();
  }

  public EdgeRenderer getEdgeRenderer(String type) {
    EdgeRenderer edgeRenderer = edgeRenderers.get(type);
    if (edgeRenderer == null && !renderUnknownAsDefault) {
      throw new IllegalArgumentException("Unknown edge type (" + type + ").");
    } else if (edgeRenderer == null && renderUnknownAsDefault) {
      edgeRenderer = edgeRenderers.get(DEFAULT);
    }
    return edgeRenderer;
  }

  public void registerEdgeRenderer(String type, EdgeRenderer renderer) {
    edgeRenderers.put(type, renderer);
  }

  public void clearEdgeRenderers() {
    edgeRenderers.clear();
  }

  public LabelRenderer getLabelRenderer(String type) {
    LabelRenderer labelRenderer = labelRenderers.get(type);
    if (labelRenderer == null && !renderUnknownAsDefault) {
      throw new IllegalArgumentException("Unknown label type (" + type + ").");
    } else if (labelRenderer == null && renderUnknownAsDefault) {
      labelRenderer = labelRenderers.get(DEFAULT);
    }
    return labelRenderer;
  }

  public void clearLabelRenderers() {
    labelRenderers.clear();
  }

  public void registerLabelRenderer(String type, LabelRenderer renderer) {
    labelRenderers.put(type, renderer);
  }

  public SelectionRenderer getSelectionRenderer(String type) {
    SelectionRenderer selectionRenderer = selectionRenderers.get(type);
    if (selectionRenderer == null && !renderUnknownAsDefault) {
      throw new IllegalArgumentException("Unknown selection type (" + type + ").");
    } else if (selectionRenderer == null && renderUnknownAsDefault) {
      selectionRenderer = selectionRenderers.get(DEFAULT);
    }
    return selectionRenderer;
  }

  public NodeHoverRenderer getNodeHoverRenderer(String type) {
    NodeHoverRenderer hoverRenderer = hoverRenderers.get(type);
    if (hoverRenderer == null && !renderUnknownAsDefault) {
      throw new IllegalArgumentException("Unknown hover type (" + type + ").");
    } else if (hoverRenderer == null && renderUnknownAsDefault) {
      hoverRenderer = hoverRenderers.get(DEFAULT);
    }
    return hoverRenderer;
  }

  public boolean isRenderUnknownAsDefault() {
    return renderUnknownAsDefault;
  }

  public void setRenderUnknownAsDefault(boolean renderUnknownAsDefault) {
    this.renderUnknownAsDefault = renderUnknownAsDefault;
  }

}