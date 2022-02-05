package stockquoter.model;

public class StockQuote implements Comparable<StockQuote> {

	private final String symbol;
	private final String valor;
	private final int companyId;
	private final double lastPrice;
	private double currentPrice;
	private double change;

	public StockQuote(String symbol, String valor, int companyId, double price) {
		this.symbol = symbol;
		this.valor = valor;
		this.lastPrice = price;
		this.companyId = companyId;
		this.currentPrice = price;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getValor() {
		return valor;
	}

	public int getCompanyId() {
		return companyId;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	@Override
	public int compareTo(StockQuote other) {
		return symbol.compareTo(other.symbol);
	}
}
