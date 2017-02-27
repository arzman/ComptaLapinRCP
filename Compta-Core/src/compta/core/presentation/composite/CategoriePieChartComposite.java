package compta.core.presentation.composite;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.util.Rotation;

import compta.core.presentation.controllers.DepenseStatDialogController;

public class CategoriePieChartComposite extends Composite {

	private DepenseStatDialogController _controler;

	private JFreeChart chart;

	public CategoriePieChartComposite(Composite parent, DepenseStatDialogController controler) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout());
		// This will create the dataset
		_controler = controler;
		PieDataset dataset = createDataset();
		// based on the dataset we create the chart
		chart = createChart(dataset);
		// we put the chart into a panel
		new ChartComposite(this, SWT.NONE, chart, true);

	}

	/** * Creates a sample dataset */

	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();

		if (_controler != null) {
			for (Object cat : _controler.getCategorieData()) {

				result.setValue((String) cat, _controler.getTotal((String) cat));

			}
		}
		return result;

	}

	/** * Creates a chart */

	private JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart3D("", dataset, false, true, true);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setForegroundAlpha(0.9f);
		plot.setDepthFactor(0.3f);
		plot.setBaseSectionOutlinePaint(Color.BLACK);
		
		return chart;

	}

	public void reDoChart() {

		getChildren()[0].dispose();

		chart = createChart(createDataset());
		new ChartComposite(this, SWT.NONE, chart, true);
		layout();

	}

}
