package br.gov.pf.labld.kharon.renderers;

import java.util.HashMap;
import java.util.Map;

public class Renderers {

  private static Map<String, GraphRenderer> graphRenderers;
  private static Map<String, NodeRenderer> nodeRenderers;
  private static Map<String, EdgeRenderer> edgeRenderers;
  private static Map<String, LabelRenderer> labelRenderers;
  private static Map<String, SelectionRenderer> selectionRenderers;

  static {
    graphRenderers = new HashMap<>();
    graphRenderers.put("default", new DefaultGraphRenderer());

    nodeRenderers = new HashMap<>();
    SquareNodeRenderer square = new SquareNodeRenderer();
    nodeRenderers.put("default", square);
    nodeRenderers.put("square", square);
    nodeRenderers.put("circle", new CircleNodeRenderer());

    edgeRenderers = new HashMap<>();
    edgeRenderers.put("default", new DefaultEdgeRenderer());

    labelRenderers = new HashMap<>();
    labelRenderers.put("default", new DefaultLabelRenderer());

    selectionRenderers = new HashMap<>();
    selectionRenderers.put("default", new DefaultSelectionRenderer());
  }

  public static GraphRenderer getGraphRenderer(String type) {
    GraphRenderer graphRenderer = graphRenderers.get(type);
    if (graphRenderer == null) {
      throw new IllegalArgumentException("Unknown graph type (" + type + ").");
    }
    return graphRenderer;
  }

  public static NodeRenderer getNodeRenderer(String type) {
    NodeRenderer nodeRenderer = nodeRenderers.get(type);
    if (nodeRenderer == null) {
      throw new IllegalArgumentException("Unknown node type (" + type + ").");
    }
    return nodeRenderer;
  }

  public static void registerNodeRenderer(String type, NodeRenderer renderer) {
    nodeRenderers.put(type, renderer);
  }

  public static EdgeRenderer getEdgeRenderer(String type) {
    EdgeRenderer edgeRenderer = edgeRenderers.get(type);
    if (edgeRenderer == null) {
      throw new IllegalArgumentException("Unknown edge type (" + type + ").");
    }
    return edgeRenderer;
  }

  public static LabelRenderer getLabelRenderer(String type) {
    LabelRenderer labelRenderer = labelRenderers.get(type);
    if (labelRenderer == null) {
      throw new IllegalArgumentException("Unknown label type (" + type + ").");
    }
    return labelRenderer;
  }

  public static SelectionRenderer getSelectionRenderer(String type) {
    SelectionRenderer selectionRenderer = selectionRenderers.get(type);
    if (selectionRenderer == null) {
      throw new IllegalArgumentException("Unknown selection type (" + type + ").");
    }
    return selectionRenderer;
  }

}