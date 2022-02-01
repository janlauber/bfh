/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import ch.bfh.matrix.Matrix;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ch.bfh.piechart.datalayer.SalesValue;
import ch.bfh.piechart.datalayer.SalesValueLoader;

public class PieChartProviderTest {

    private static PieChartProvider provider = null;

    @BeforeAll
    public static void init() {
       SalesValueLoader.loadSalesValues();
       provider = new PieChartProvider();
    }

    @Test
    public void testPercentages() throws Exception {
        List<SalesValue> salesValues = provider.getPieChartSalesValues();
        assertTrue(salesValues.size() > 0);
        double sum = 0.0;
        for (SalesValue value : salesValues) {
            sum += value.getPercentage();
        }
        assertEquals(100.0, sum, SalesValue.PRECISION);
    }

    @Test
    public void testGetPieChart() throws Exception {
        PieChart chart = provider.getPieChart();
        assertEquals(7, chart.getNbOfSlices());
        double width = 320;
        double height = 240;
        double r = Math.min(width, height) * 0.8;
        chart.setPosAndRadius(width,height,r);
        double startX = width;
        double startY = 0.0;
        for (int i = 0; i < chart.getNbOfSlices(); i++) {
            PieChartSlice slice = chart.getSlice(i);
            Matrix m = slice.getCoords();
            assertEquals(3, m.getNbOfColumns());
            assertEquals(3, m.getNbOfLines());
            assertEquals(width, m.get(0,0), SalesValue.PRECISION);
            assertEquals(startX, m.get(0,1), SalesValue.PRECISION);
            startX = m.get(0,2);
            if (i != 0) {
                assertEquals(startY, m.get(1,1), SalesValue.PRECISION);
            }
            startY = m.get(1,2);
            assertEquals(height, m.get(1,0));
            assertEquals(1.0, m.get(2,0), SalesValue.PRECISION);
            assertEquals(1.0, m.get(2,1), SalesValue.PRECISION);
            assertEquals(1.0, m.get(2,2), SalesValue.PRECISION);
        }
    }
}
