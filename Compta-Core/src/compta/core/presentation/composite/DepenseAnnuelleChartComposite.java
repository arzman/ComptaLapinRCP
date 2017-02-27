package compta.core.presentation.composite;

import java.awt.Color;
import java.awt.Paint;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import compta.core.common.ApplicationFormatter;
import compta.core.presentation.controllers.DepenseAnnuelleSynthDialogController;

public class DepenseAnnuelleChartComposite extends Composite {

	private DepenseAnnuelleSynthDialogController _controler;

	private JFreeChart chart;

	public DepenseAnnuelleChartComposite(Composite parent, DepenseAnnuelleSynthDialogController controler) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout());

		_controler = controler;
		CategoryDataset dataset = createDataset();
		// based on the dataset we create the chart
		chart = createChart(dataset);
		// we put the chart into a panel
		new ChartComposite(this, SWT.NONE, chart, true);

	}

	@SuppressWarnings("serial")
	private JFreeChart createChart(CategoryDataset dataset) {

		JFreeChart chart = ChartFactory.createBarChart3D("", "", "", dataset, PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot plot =  chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRenderer(new BarRenderer3D(){
			
			@Override
			public Paint getItemPaint(int row, int column) {
				return Color.YELLOW;
			}
			
		});
		
		
		return chart;

	}

	private DefaultCategoryDataset createDataset() {

		DefaultCategoryDataset data = new DefaultCategoryDataset();

		for (Calendar month : _controler.getSortedMonth()) {

			String serie = _controler.getSelectedCategorie();
			String cat = ApplicationFormatter.moiAnneedateFormat.format(month.getTime());

			if (_controler.getSelectedAnnee() != null && cat != null && _controler.getSelectedCategorie()!=null) {
				data.addValue(_controler.getTotal(month), serie, cat);
			}

		}

		return data;
	}

	public void reDoChart() {

		if (getChildren().length > 0) {
			getChildren()[0].dispose();
		}

		chart = createChart(createDataset());
		new ChartComposite(this, SWT.NONE, chart, true);
		layout();

	}

}
