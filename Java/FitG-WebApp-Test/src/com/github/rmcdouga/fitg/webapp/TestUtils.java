package com.github.rmcdouga.fitg.webapp;

import java.util.Map;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

public class TestUtils {
	
	private static final boolean internalContainer = true;
	public static final String APPLICATION_PREFIX = internalContainer ? "" : "/FitG";
	
	public static Builder trace(Builder builder) {
		return builder.header("X-Jersey-Tracing-Accept", "Yes")
					  .header("X-Jersey-Tracing-Threshold", "VERBOSE")
				      .header("X-Jersey-Tracing-Accept", "general");

	}

	public static void printTrace(Response result) {
		result.getHeaders()
			  .entrySet().stream()
			  .sorted(Map.Entry.comparingByKey())
			  .forEach((e)->System.out.println("k='" + e.getKey() + "', v='" + e.getValue() + "'."));
	}


}
