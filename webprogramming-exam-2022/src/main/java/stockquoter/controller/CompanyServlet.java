package stockquoter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import stockquoter.model.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "CompanyServlet", urlPatterns = {"/api/companies/*"})
public class CompanyServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(StockQuoterServlet.class.getName());
    private final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

    @Override
    public void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException {
        int companyId = Integer.parseInt(request.getPathInfo().substring(1));
        logger.info("Requested company: " + companyId);
        response.setContentType("application/json");

        if (companyId != 0) {
            try {
                Company company = StockQuoterService.findCompany(companyId);
                response.getWriter().write(objectMapper.writeValueAsString(company));
            } catch (CompanyNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // return not found header
            response.setStatus(404);
        }
    }

    @Override
    public void doPut(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException {
        int companyId = Integer.parseInt(request.getPathInfo().substring(1));
        logger.info("Updating company: " + companyId);
        response.setContentType("application/json");

        if (companyId != 0) {
            Company company = objectMapper.readValue(request.getInputStream(), Company.class);
            try {
                StockQuoterService.updateCompany(company);
            } catch (CompanyNotFoundException e) {
                e.printStackTrace();
            }
            response.setStatus(204);
        } else {
            // return not found header
            response.setStatus(404);
        }
    }

    @Override
    public void doDelete(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException {
        int companyId = Integer.parseInt(request.getPathInfo().substring(1));
        logger.info("Deleting company: " + companyId);
        response.setContentType("application/json");

        if (companyId != 0) {
            // check if there are any quotes for this company
            List<StockQuote> stockQuotes = StockQuoterService.getQuotes();
            for (StockQuote stockQuote : stockQuotes) {
                if (stockQuote.getCompanyId() == companyId) {
                    // return not allowed header
                    response.setStatus(405);
                    return;
                }
            }
            try {
                StockQuoterService.removeCompany(companyId);
            } catch (CompanyNotFoundException | CompanyInUseException e) {
                e.printStackTrace();
            }
            response.setStatus(204);
        } else {
            // return not found header
            response.setStatus(404);
        }
    }
}
