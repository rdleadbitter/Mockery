// package com.model;

// import java.util.List;
// import java.util.Scanner;

// public class Main {
//     public static void main(String[] args) {
//         System.out.println("Launching Main...");
//         MockeryFacade app = MockeryFacade.getInstance();
//         System.out.println("App facade initialized");
//         Scanner scanner = new Scanner(System.in);

//         System.out.println("Welcome to the NFL Mock Draft Simulator");
//         while (true) {
//             System.out.print("[login, register, exit]: ");
//             String command = scanner.nextLine();

//             if (command.equals("exit")) break;

//             if (command.equals("register")) {
//                 System.out.print("Username: ");
//                 String user = scanner.nextLine();
//                 System.out.print("Password: ");
//                 String pass = scanner.nextLine();
//                 if (app.register(user, pass)) {
//                     System.out.println("Registered and logged in.");
//                 } else {
//                     System.out.println("Username already taken.");
//                 }
//             }

//             if (command.equals("login")) {
//                 System.out.print("Username: ");
//                 String user = scanner.nextLine();
//                 System.out.print("Password: ");
//                 String pass = scanner.nextLine();
//                 if (app.login(user, pass)) {
//                     System.out.println("Logged in!");
//                     break;
//                 } else {
//                     System.out.println("Invalid login.");
//                 }
//             }
//         }

//         User user = app.getCurrentUser();

//         while (true) {
//             System.out.print("[newdraft, assign, trade, view, exit]: ");
//             String cmd = scanner.nextLine();

//             if (cmd.equals("exit")) break;

//             if (cmd.equals("newdraft")) {
//                 System.out.print("Draft name: ");
//                 String name = scanner.nextLine();
//                 System.out.print("How many rounds? (1-7): ");
//                 int rounds = Integer.parseInt(scanner.nextLine());
//                 MockDraft draft = app.createMockDraft(name, 2025, rounds); // 👈 new overloaded method
//                 System.out.println("Created draft: " + draft.getDraftName());
//                 System.out.println("Draft has " + draft.getPicks().size() + " picks.");
//                 List<Player> players = app.getAllPlayers();

//                 for (Pick pick : draft.getPicks()) {
//                     System.out.println("\n===============================");
//                     System.out.println("Pick " + pick.getNumber() + " - Team: " + pick.getTeam().getAbbreviation());

//                     // Show top 5 unpicked players
//                     System.out.println("Top Available Players:");
//                     players.stream()
//                         .filter(p -> !draft.hasPlayerAlreadySelected(p.getConsensusRank()))
//                         .sorted((a, b) -> Integer.compare(a.getConsensusRank(), b.getConsensusRank()))
//                         .limit(5)
//                         .forEach(p -> System.out.println(
//                             "Rank " + p.getConsensusRank() + ": " + p.getName() + " (" + p.getPosition() + ", " + p.getSchool() + ")"));

//                     System.out.print("Enter consensus rank to draft, or type 'trade': ");
//                     String input = scanner.nextLine();

//                     if (input.equalsIgnoreCase("trade")) {
//                         System.out.print("Enter new team abbreviation (e.g. ATL, MIN): ");
//                         String teamAbbr = scanner.nextLine();
//                         if (app.tradePick(draft, pick.getNumber(), teamAbbr)) {
//                             System.out.println("Pick traded to " + teamAbbr + ".");
//                         } else {
//                             System.out.println("Invalid team.");
//                         }

//                         // Offer chance to still make the pick
//                         System.out.print("Still want to draft a player? (y/n): ");
//                         if (!scanner.nextLine().equalsIgnoreCase("y")) {
//                             continue;
//                         }

//                         System.out.print("Enter consensus rank to draft: ");
//                         input = scanner.nextLine(); // fallthrough to draft block
//                     }

//                     try {
//                         int playerId = Integer.parseInt(input);
//                         if (app.assignPlayerToPick(draft, pick.getNumber(), playerId)) {
//                             System.out.println("Player drafted.");
//                         } else {
//                             System.out.println("Invalid pick or duplicate player.");
//                         }
//                     } catch (NumberFormatException e) {
//                         System.out.println("Invalid input.");
//                     }
//                 }
//             }

//             if (cmd.equals("assign")) {
//                 System.out.print("Draft name: ");
//                 String draftName = scanner.nextLine();
//                 MockDraft draft = user.getMockDrafts().stream()
//                     .filter(d -> d.getDraftName().equals(draftName)).findFirst().orElse(null);
//                 if (draft == null) {
//                     System.out.println("Draft not found.");
//                     continue;
//                 }
//                 System.out.println("Available players:");
                
//                 for (int i = 0; i < 10; i++) {
//                     Player player = app.getAllPlayers().get(i);
//                     System.out.println(i + ": " + player.getName() + " (Rank: " + player.getConsensusRank() + ")");
//                 }

//                 System.out.print("Pick #: ");
//                 int pick = Integer.parseInt(scanner.nextLine());
//                 System.out.print("Consensus Rank of Player: ");
//                 int playerId = Integer.parseInt(scanner.nextLine());

//                 if (app.assignPlayerToPick(draft, pick, playerId)) {
//                     System.out.println("Player assigned.");
//                 } else {
//                     System.out.println("Failed to assign player.");
//                 }
//             }

//             if (cmd.equals("trade")) {
//                 System.out.print("Draft name: ");
//                 String draftName = scanner.nextLine();
//                 MockDraft draft = user.getMockDrafts().stream()
//                     .filter(d -> d.getDraftName().equals(draftName)).findFirst().orElse(null);
//                 if (draft == null) {
//                     System.out.println("Draft not found.");
//                     continue;
//                 }

//                 System.out.print("Pick #: ");
//                 int pick = Integer.parseInt(scanner.nextLine());
//                 System.out.print("New Team Abbr: ");
//                 String newTeam = scanner.nextLine();

//                 if (app.tradePick(draft, pick, newTeam)) {
//                     System.out.println("Pick traded.");
//                 } else {
//                     System.out.println("Failed to trade.");
//                 }
//             }

//             if (cmd.equals("view")) {
//                 for (MockDraft d : user.getMockDrafts()) {
//                     System.out.println(d.getDraftName() + " (Score: " + d.getScore() + ")");
//                     for (Pick p : d.getPicks()) {
//                         String team = p.getTeam().getAbbreviation();
//                         String player = (p.getPlayer() != null) ? p.getPlayer().getName() : "unpicked";
//                         System.out.println("Pick " + p.getNumber() + " - " + team + " - " + player);
//                     }
//                 }
//             }
//         }

//         System.out.println("Goodbye.");
//     }
// }
