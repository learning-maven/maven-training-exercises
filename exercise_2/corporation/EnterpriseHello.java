package corporation;

import printer.HelloPrinter;

public class EnterpriseHello {

	private final HelloPrinter printer;

	public EnterpriseHello(HelloPrinter printer) {
		this.printer = printer;
	}

	public static void main(String[] args) {
		EnterpriseHello hello = new EnterpriseHello(
			new HelloPrinter()
		);
		hello.print("Hello world!");
	}

	public void print(String contents) {
		printer.print(contents);
	}
}

