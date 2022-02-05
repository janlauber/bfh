package stockquoter.model;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public class StockQuoterService {

	private static final String STOCK_DATA_PATH = "/stock-data.csv";

	private static final Map<String, StockQuote> quotes = new HashMap<>();
	private static final Map<Integer, Company> companies = new HashMap<>();

	static {
		InputStream stream = StockQuoterService.class.getResourceAsStream(STOCK_DATA_PATH);
		Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8);
		int companyId = 1;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] tokens = line.split(";");
			StockQuote quote = new StockQuote(tokens[0], tokens[1], companyId, Double.parseDouble(tokens[2]));
			quotes.put(quote.getSymbol(), quote);
			Address address = new Address(tokens[4], tokens[5], tokens[6], tokens[7]);
			Company company = new Company(companyId, tokens[3], address, tokens[8], tokens[9]);
			companies.put(companyId, company);
			companyId++;
		}
	}

	public static List<StockQuote> getQuotes() {
		updateQuotes();
		return quotes.values().stream().sorted().collect(toList());
	}

	public static Company findCompany(Integer id) throws CompanyNotFoundException {
		if (!companies.containsKey(id)) throw new CompanyNotFoundException();
		return companies.get(id);
	}

	public static void updateCompany(Company company) throws CompanyNotFoundException {
		if (companies.remove(company.getId()) == null)
			throw new CompanyNotFoundException();
		companies.put(company.getId(), company);
	}

	public static void removeQuote(String symbol) throws QuoteNotFoundException {
		if (quotes.remove(symbol) == null)
			throw new QuoteNotFoundException();
	}

	public static void removeCompany(int id) throws CompanyInUseException, CompanyNotFoundException {
		if (quotes.values().stream().anyMatch(quote -> quote.getCompanyId() == id))
			throw new CompanyInUseException();
		if (companies.remove(id) == null)
			throw new CompanyNotFoundException();
	}

	private static void updateQuotes() {
		Random random = new Random();
		quotes.values().forEach(quote -> {
			quote.setCurrentPrice(round(quote.getLastPrice() * (1.0 + 0.1 * random.nextGaussian())));
			quote.setChange(round((quote.getCurrentPrice() - quote.getLastPrice()) / quote.getLastPrice()));
		});
	}

	private static double round(double x) {
		return Math.round(100 * x) / 100.0;
	}
}
