package com.acme.supergreeter;

import com.acme.greet.Greeter;

public class SuperGreeter {

	public static void greet(String firstName, String lastName) {
		Greeter.greet(firstName, lastName);
		Greeter.greet(firstName, lastName);
	}
}
