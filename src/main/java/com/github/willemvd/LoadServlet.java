package com.github.willemvd;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Very simple load generator
 */
public class LoadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String ms = req.getParameter("ms");

		boolean validMs = true;
		long milliseconds = 0;
		try {
			milliseconds = Long.parseLong(ms);
		} catch (NumberFormatException e) {
			validMs = false;
		}

		if (milliseconds <= 0) {
			validMs = false;
		}

		if (!validMs) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No valid ms parameter");
			return;
		}

		stress(milliseconds);

		resp.getWriter().println("stressed for " + milliseconds + "ms");
	}

	/**
	 * Stress the machine for given number of seconds
	 * @param milliseconds number of milliseconds to run the stress test
	 */
	private void stress(long milliseconds) {
		long sleepTime = milliseconds*1000000L; // convert to nanoseconds
		long startTime = System.nanoTime();
		while ((System.nanoTime() - startTime) < sleepTime) {}
	}
}
