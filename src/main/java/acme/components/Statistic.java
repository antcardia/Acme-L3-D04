
package acme.components;

import java.util.Collection;

public class Statistic {

	public static Double averageCalc(final Collection<Double> values) {
		double res = 0.0;
		if (!values.isEmpty()) {
			final Double total = values.stream().mapToDouble(Double::doubleValue).sum();
			res = total / values.size();
		}
		return res;
	}

	public static Double deviationCalc(final Collection<Double> values, final Double average) {
		Double res = 0.0;
		Double aux = 0.0;
		if (!values.isEmpty()) {
			for (final Double value : values)
				aux = Math.pow(value - average, 2);
			res = Math.sqrt(aux / values.size());
		}
		return res;
	}

	public static Double minimumCalc(final Collection<Double> values) {
		Double res = 0.0;
		if (!values.isEmpty())
			res = values.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
		return res;
	}

	public static Double maximumCalc(final Collection<Double> values) {
		Double res = 0.0;
		if (!values.isEmpty())
			res = values.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
		return res;
	}
}
