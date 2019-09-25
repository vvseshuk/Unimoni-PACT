package com.unimoni.pact;

import org.junit.runner.RunWith;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;

@RunWith(PactRunner.class)
@Provider("StudentService")
@PactFolder("../pacts")
public class ProviderTest {

	@State("There is a student with id 23")
	public void stateDef() {
		System.out.println("Created pre-reqs for student with id 23..");
	}
	
	@TestTarget
    public final Target target = new HttpTarget("localhost", 8080);

}