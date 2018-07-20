package swingy;

import swingy.Controller.Controller;

public class Start {
	public static void main(String[] args) {
		Controller controller;
		try {
			if (args.length == 0) {
				controller = new Controller().setView("gui");
			} else {
				controller = new Controller().setView(args[0]);
			}
			controller.initialize();
		} catch (Exception ex) {
			System.out.println("MainError: " + ex.getMessage());
			System.exit(1);
		}
	}
}
