package com.acme.supergreeter;

import com.acme.greet.Greeter;

public class SuperGreeter {

	public static void greet(String name) {
		Greeter.greet(name);
		Greeter.greet(name);
	}
}
