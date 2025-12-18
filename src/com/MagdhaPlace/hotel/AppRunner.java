package com.MagdhaPlace.hotel;

import com.MagdhaPlace.hotel.handler.HandleCustomer;

import java.util.Scanner;

public class AppRunner {
    private final HandleCustomer handleCustomer;

    public AppRunner(HandleCustomer handleCustomer) {
        this.handleCustomer = handleCustomer;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to MagadhPlace Hotel");
        printHelp();
        boolean running = true;
        while(running){
            System.out.println("\n> ");
            String command = sc.nextLine().trim();
            try{
                String normalizeCommand = command.toLowerCase().trim();
                switch(normalizeCommand){
                    case "help" -> printHelp();
                    case "register-customer" -> handleCustomer.handleAddCustomer(sc);
                    case "exit","quit"   -> running = false;
                    default ->{
                        System.out.println("Unknown command. Type 'help' for list.");
                    }
                }
            }catch(Exception e){
                System.out.println("Unexpected Error "+e.getMessage());
            }
        }

    }

    private void printHelp(){
        System.out.println("commands: ");
        System.out.println("  register-customer     - add a new customer");
        System.out.println("  help                  - show this help");
        System.out.println("  exit / quit           - exit application");
    }

}
