/*
	* Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
	*/
package ch.bfh.piechart.datalayer;

import java.util.Objects;

/**
	* Represents a sales value record.
	*/
public class SalesValue {
	public static final double PRECISION = 1e-5;

	private int id;
	private final int productId;
	private final int value;
	private double percentage;

	/**
		* Creates a new sales value record with given product id and sales value.
		*
		* @param productId a product id
		* @param value     a sales value
		*/
	public SalesValue(int productId, int value) {
		this(0, productId, value);
	}

	/**
		* Creates a new sales value record with given technical id, product id, and
		* sales value.
		*
		* @param id        a (technical) id
		* @param productId a product id
		* @param value     a sales value
		*/
	public SalesValue(int id, int productId, int value) {
		this.id = id;
		this.productId = productId;
		this.value = value;
	}

	/**
		* Returns the (technical) id.
		*
		* @return the id
		*/
	public int getId() {
		return id;
	}

	/**
		* Sets the (technical) id.
		*
		* @param id the (technical) id
		*/
	public void setId(int id) {
		this.id = id;
	}

	/**
		* Returns the product id.
		*
		* @return the product id
		*/
	public int getProductId() {
		return productId;
	}

	/**
		* Returns the sales value.
		*
		* @return the sales value
		*/
	public int getValue() {
		return value;
	}

	/**
		* Sets the percentage to be calculated depending on this value and the sum of
		* all values.
		*
		* @param percentage a calculated percentage
		*/
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	/**
		* Returns the percentage.
		*
		* @return the percentage previously calculated
		*/
	public double getPercentage() {
		return percentage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, percentage, productId, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SalesValue other = (SalesValue) obj;
		boolean okay = percentage - other.percentage < PRECISION;
		return id == other.id && productId == other.productId && value == other.value && okay;
	}

	@Override
	public String toString() {
		return "SalesValue [id=" + id + ", productId=" + productId + ", value=" + value + ", percentage=" + percentage
					+ "]";
	}
}
