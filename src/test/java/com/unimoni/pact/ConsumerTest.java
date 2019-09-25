package com.unimoni.pact;

import static io.restassured.RestAssured.get;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class ConsumerTest {
	
	//https://www.baeldung.com/pact-junit-consumer-driven-contracts - explains pact example
	
	@Rule
	public PactProviderRuleMk2 providerMock = new PactProviderRuleMk2("StudentService", "localhost", 8112, this);
		
	@Pact(consumer = "RestConsumer")
	public RequestResponsePact createPactForGet(PactDslWithProvider builder) {
	
		Map<String, String> headers = new HashMap();
		headers.put("Content-Type", "application/json");
		
		DslPart results = new PactDslJsonBody()
				.stringType("firstName", "Rams")
				.stringType("lastName", "Kumars")
//				.integerType("lastName", 20)
				.asBody();
		
		RequestResponsePact pactRequestResponse =
				builder
					.given("There is a student with id 23")
					.uponReceiving("A request for student details")
					.path("/student/23")
					.method("GET")
					.willRespondWith()
					.status(200)
					.headers(headers)
					.body(results)
					.toPact();
		
		return pactRequestResponse;		
	}
	
	
	@Test
	@PactVerification
	public void testConsumer() {
		System.setProperty("pact.rootDir", "../pacts");
		get("http://localhost:" + providerMock.getPort() + "/student/23").then().log().all();
	}

}
