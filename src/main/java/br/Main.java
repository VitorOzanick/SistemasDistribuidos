/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br;
/**
 *
 * @author vitor
 */
import java.security.SecureRandom;
import java.util.ArrayList;

// Exemplo de utilização:
public class Main {

    public static ArrayList<Tranca> tranca;
    public static ArrayList<Op> fila;

    public static boolean liberatranca = false;

    public Main(){

        tranca = new ArrayList<>();
        tranca.add(new Tranca(new Op("","0")));
        fila = new ArrayList<>();
        fila.add(new Op("","0"));	
        
        System.out.println("SISTEMA DE ESTOQUE");
        int NUM = 20;
        String[] transacao = new String[NUM];
        Compartilhado x = new Compartilhado("x");
        SecureRandom sr = new SecureRandom();
        ArrayList<Op> ops;
        int op =0;
        String operacao ="r";
        User []user = new User[NUM]; 
        for (int i = 0; i < NUM; i++) {
            ops = new ArrayList<>();           
            transacao[i] = String.valueOf(i);
        switch (sr.nextInt(2)) {
            
            case 0: operacao = "r";
                break;
            case 1:
                operacao = "w";
                break;
        }
        ops.add(new Op(operacao, transacao[i]));
        ops.add(new Op(operacao, transacao[i]));
        user[i] = new User(transacao[i],x, ops);
        }
        for (int i = 0; i < NUM; i++) {
            try {
                user[i].thrd.join();
            } catch (InterruptedException e) {
                System.out.println("Thread Main interrompida.");
            }
        }
    
        System.out.println("Acessos concluidos.");

    }
    
    public static void main(String args[]){
    
        new Main();
    }
}
