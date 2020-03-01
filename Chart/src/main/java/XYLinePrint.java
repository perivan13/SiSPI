import java.awt.Color;
import java.awt.BasicStroke;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Scanner;
import java.util.Vector;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import static java.lang.Math.pow;

public class XYLinePrint extends ApplicationFrame {

    private XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    private int countOfFunction = 0;


    public void saveAsJPEG(String chartTitle) throws IOException {
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "X",
                "Y=F(X)",
                createDatasetFromFile(new File("X.txt")),
                PlotOrientation.VERTICAL,
                true, true, false);
        File file = new File("Charts.jpeg");
        ChartUtilities.saveChartAsJPEG(file, xyLineChart, 1280, 720);
    }

    public XYLinePrint(String applicationTitle, String chartTitle) throws FileNotFoundException {
        super(applicationTitle);
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "X",
                "Y=F(X)",
                createDatasetFromFile(new File("X.txt")),
                //createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1280, 720));
        final XYPlot plot = xyLineChart.getXYPlot();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesPaint(3, Color.BLUE);
        renderer.setSeriesPaint(4, Color.BLACK);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesStroke(3, new BasicStroke(2.0f));
        renderer.setSeriesStroke(4, new BasicStroke(2.0f));


        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    public XYDataset createDataset() {
        final XYSeries function = new XYSeries("f(x)=x^2");

        for (double i = 0; i < 50; i += 0.5) {
            function.add(i, pow(i, 2));
            function.add(-i, pow(i, 2));
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(function);
        return dataset;
    }

    private static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public XYDataset createDatasetFromFile(File file) throws FileNotFoundException {

        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        Vector<XYSeries> vector = new Vector<>();


        while (scanner.hasNext()) {
            String buffer = scanner.next();
            if (!isNumeric(buffer) && buffer != "\n" && buffer != " ") {
                vector.add(new XYSeries("F" + countOfFunction));
                countOfFunction += 1;
            } else {
                Double d = new Double(buffer);
                vector.elementAt(vector.size()-1).add((double) d, d * d + countOfFunction - 1);
            }
        }

        for (XYSeries xySeries : vector) {
            dataset.addSeries(xySeries);
        }

        return dataset;
    }
}