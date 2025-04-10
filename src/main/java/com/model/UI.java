package com.model;

public class UI {
    
    private MockeryFACADE facade = new MockeryFACADE();

    public void login() {
        String username = "testUser"; // Replace with actual input
        String password = "testPassword"; // Replace with actual input

        if (facade.login(username, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Create new user? (y/n)");
            String response = "y"; // Replace with actual input
            if (response.equalsIgnoreCase("y")) {
                facade.makeUser(username, password); // Replace with actual input
                if (facade.login(username, password))
                    System.out.println("Login successful!");
            } else {
                System.out.println("Exiting...");
            }
        }
        facade.logout();
    }

    public void run() {
        login();
    }

    public static void main(String[] args) {
        UI ui = new UI();
        ui.run();
    }
}
