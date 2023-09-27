/**
 * Provides filters for handling authentication and authorization.
 */
package com.grievance.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grievance.enums.MemberRole;
import com.grievance.repository.MemberRepo;

/**
 * Filter to authenticate API requests.
 */
@Component
public class Authentication implements Filter {

    /** Logger instance for this class. */
    private static final Logger LOGGER = LoggerFactory
    		.getLogger(Authentication.class);

    /** Repository for Member entities. */
    @Autowired
    private MemberRepo memberRepo;

    /** List of URLs that need admin authentication. */
    private static List<String> adminUrls = new ArrayList<>();

    /** List of URLs that need member authentication. */
    private static List<String> memberUrls = new ArrayList<>();

    static {
    	adminUrls.add("/api/member/create");
    	adminUrls.add("/api/member/changePassword");
		adminUrls.add("/api/member/getAll");
		adminUrls.add("/api/member/delete");
		adminUrls.add("/api/department/create");
		adminUrls.add("/api/department/getAll");
		adminUrls.add("/api/department/delete");
		adminUrls.add("/api/ticket/create");
		adminUrls.add("/api/ticket/viewById");
		adminUrls.add("/api/ticket/update");
		adminUrls.add("/api/ticket/getAll");


    	memberUrls.add("/api/member/changePassword");
    	memberUrls.add("/api/department/getAll");
    	memberUrls.add("/api/ticket/create");
    	memberUrls.add("/api/ticket/viewById");
    	memberUrls.add("/api/ticket/update");
    	memberUrls.add("/api/ticket/getAll");
    }

    /**
     * Constructor for the Authentication filter.
     *
     * @param amemberRepo the member repository
     */
    public Authentication(final MemberRepo amemberRepo) {
        this.memberRepo = amemberRepo;
    }

    /**
     * Filters requests and responses based on certain conditions.
     *
     * @param request  The incoming ServletRequest.
     * @param response The outgoing ServletResponse.
     * @param chain    The FilterChain for this filtering process.
     * @throws IOException      If an input or output error is detected when the
     *                          servlet handles the request.
     * @throws ServletException If the request could not be handled.
     *
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see FilterChain
     */
    @Override
    public void doFilter(final ServletRequest request,
    		final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest =
        		(HttpServletRequest) request;
        String email = httpServletRequest.getHeader("email");
        String password = httpServletRequest.getHeader("password");
        String url = httpServletRequest.getRequestURI();

        LOGGER.info("Email: {}", email);
        LOGGER.info("Password: {}", password);

        if ("OPTIONS".equals(httpServletRequest.getMethod())) {
            HttpServletResponse httpServletResponse =
            		(HttpServletResponse) response;
            httpServletResponse.setHeader(
            		"Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader(
            		"Access-Control-Allow-Methods",
            		"GET, POST, PUT, DELETE");
            httpServletResponse.setHeader(
            		"Access-Control-Allow-Headers",
            		"Authorization, Content-Type, email, password");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            handleNonOptionRequests(request, response,
            		chain, email, password, url);
        }
    }


    /**
     * Handle non-OPTION-request.
     *
     * @param request
     * @param response
     * @param chain
     * @param email
     * @param password
     * @param url
     * @throws IOException
     * @throws ServletException
     */
    private void handleNonOptionRequests(final ServletRequest request,
    		final ServletResponse response, final FilterChain chain,
    		final String email, final String password, final String url)
    				throws IOException, ServletException {
        if ("/api/member/login".equals(url)) {
            chain.doFilter(request, response);
        } else {
            if (email == null || password == null) {
                ((HttpServletResponse) response).sendError(
                		HttpServletResponse.SC_UNAUTHORIZED,
                		"Invalid Details");
            } else if (memberRepo.existsByEmailAndPasswordAndRole(
            		email, password, MemberRole.ADMIN)
                    && checkAdminUrl(url)) {
                LOGGER.info("INSIDE ADMIN");
                chain.doFilter(request, response);
            } else if (memberRepo.existsByEmailAndPasswordAndRole(
            		email, password, MemberRole.MEMBER)
                    && checkUserUrl(url)) {
                LOGGER.info("INSIDE MEMBER");
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendError(
                	HttpServletResponse.SC_UNAUTHORIZED,
                	"Unauthorized Role");
            }
        }
    }

    /**
     * Checks if the provided URL requires admin access.
     *
     * @param url the URL to be checked
     * @return true if the URL requires admin access, false otherwise
     */
    public Boolean checkAdminUrl(final String url) {
        return adminUrls.contains(url);
    }

    /**
     * Checks if the provided URL requires member access.
     *
     * @param url the URL to be checked
     * @return true if the URL requires member access, false otherwise
     */
    private boolean checkUserUrl(final String url) {
        return memberUrls.contains(url);
    }

}
