package com.acme.greet;

import com.acme.greet.Greeter;
import com.acme.supergreeter.SuperGreeter;

public class MyApplication {

	public static void main(String[] args) {
		Greeter.greet("Florent", "Biville");
		SuperGreeter.greet("fbiville");
	}
}
