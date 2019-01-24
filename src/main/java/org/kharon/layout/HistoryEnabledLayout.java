package org.kharon.layout;

import java.awt.FontMetrics;
import java.util.List;

import org.kharon.GraphPane;
import org.kharon.history.GraphAction;

public interface HistoryEnabledLayout {

  List<GraphAction> performLayout(GraphPane graphPane, FontMetrics fontMetrics);

}
