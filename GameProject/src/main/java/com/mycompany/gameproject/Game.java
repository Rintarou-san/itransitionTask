package com.mycompany.gameproject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Game {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        Game game = new Game();
        if (!game.checkLength(args) || !game.checkOdd(args) || game.hasDuplicates(args)) {
            System.out.println("Try again (ex: Rock Paper Scissors)");
            return;
        }
        KeyGenerator keygen = new KeyGenerator();
        Random rand = new Random();
        GameRule gameRule = new GameRule(args.length);
        Scanner in = new Scanner(System.in);
        String choice = "0";
        do {
            int computerMove = rand.nextInt(args.length);
            String computerHMAC = keygen.getHMAC(args[computerMove], keygen.getKey());
            System.out.println("\n\nHMAC : " + computerHMAC);
            game.showMenu(args);
            choice = game.getChoice(in);
            if (choice.equals("?")) {
                Table table = new Table(args.length);
                table.GenerateTable(args);
            } else {
                try {
                    int point = Integer.parseInt(choice);
                    if (point < 1 || point > args.length) {
                        throw new NumberFormatException();
                    }
                    int userMove = Integer.parseInt(choice) - 1;
                    System.out.println("Your move : " + args[userMove]);
                    System.out.println("Computer move : " + args[computerMove]);
                    if (gameRule.getWinner(userMove, computerMove) == gameRule.WIN) {
                        System.out.println("You WIN!");
                    }
                    if (gameRule.getWinner(userMove, computerMove) == gameRule.LOSE) {
                        System.out.println("Computer WIN!");
                    }
                    if (gameRule.getWinner(userMove, computerMove) == gameRule.DRAW) {
                        System.out.println("DRAW");
                    }
                    System.out.println("HMAC key : " + keygen.getKey());
                    if (game.isCheating(keygen.getKey(), args[computerMove], computerHMAC)) {
                        System.out.println("Computer CHEATED");
                    }
                } catch (NumberFormatException ex) {
                    if (!choice.equals("0")) {
                        System.out.println("Incorrect input. Try again");
                    }
                }
            }
        } while (!choice.equals("0"));
    }

    public void showMenu(String[] moves) {
        int i = 0;
        for (String move : moves) {
            System.out.println(++i + " - " + move);
        }
        System.out.println("0 - Exit");
        System.out.println("? - Help");
    }

    private boolean checkLength(String[] moves) {
        if (moves.length < 3) {
            System.out.println("Too few moves. It takes at least three");
            return false;
        }
        return true;
    }

    private boolean checkOdd(String[] moves) {
        if (moves.length % 2 != 1) {
            System.out.println("Number of moves is even. Odd required");
            return false;
        }
        return true;
    }

    public boolean hasDuplicates(String[] moves) {
        Set<String> s = new HashSet<>();
        for (String move : moves) {
            if (!s.add(move)) {
                System.out.println("Has duplicates. Remove them");
                return true;
            }
        }
        return false;
    }

    public String getChoice(Scanner in) {
        System.out.print("Your choice : ");
        String choice = in.nextLine();
        return choice;
    }

    public boolean isCheating(String key, String move, String HMAC) throws NoSuchAlgorithmException, InvalidKeyException {
        KeyGenerator keygen = new KeyGenerator();
        if (keygen.getHMAC(move, key).equals(HMAC)) {
            return false;
        }
        return true;
    }
}
