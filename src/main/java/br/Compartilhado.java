/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br;

/**
 *
 * @author vitor
 */
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

public class Compartilhado {//Responsavel por gerenciar acesso ao recurso compartilhado

    private boolean leitura = true;
    private boolean escrita = true;

    private String x;

    public Compartilhado(String recurso) {

        this.x = recurso;
    }

    public synchronized void removeTranca(Op op) { //synchronized: apenas uma thread por vez acessa 
    //Remove uma tranca específica associada a uma operação, indicando que a operação foi concluída e libera o acesso ao recurso.
        String tipo = op.tipo;
        String transacao = op.transacao;
    
        if (Main.tranca.size() > 0) 
            for (int i = 0; i < Main.tranca.size(); i++) 
                if (Main.tranca.get(i).tipo.equals(tipo)
                        && Main.tranca.get(i).transacao.equals(transacao)) 
                    Main.tranca.remove(i);
        leitura = false; //Náo permite acesso durante liberação
    }

    public String toString() {
        return x;
    }

    public synchronized boolean enfileirar(Op op, Thread thrd) {
        //Coloca uma operação na fila de acesso ao recurso, aguardando até que seja possível executá-la.
        boolean abort = false;
        boolean timeout = false;
        System.out.println("User " + thrd.getName() + " com operacao " + op.tipo + op.transacao + " entrou na fila para acesso");
        Main.fila.add(new Op(op.tipo, op.transacao));

        while (Main.liberatranca== false && !timeout) { 
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Main.liberatranca = false;
        System.out.println("Usuario " +thrd.getName() + " assumiu acesso");

        if (timeout) {
            abort = true;
        }
        return abort;
    }

    public void dormir() {
        //Simula uma pausa/tempo de espera antes de executar uma operação.
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
    }

    public synchronized boolean Conflito(Op op, String thread) {
    //Verifica se uma operação conflita com outras operações já em execução no recurso compartilhado.  
        boolean result = false;

        while (!leitura && Main.liberatranca == false) {
            try {
                System.out.println(thread + ": WAIT");
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }
        leitura = false;
        Iterator iterator = Main.tranca.iterator();
        String trancas = "";
        while (iterator.hasNext()) { //percorre as trancas
            Tranca t = (Tranca) iterator.next();
            trancas += thread + "-[" + t.tipo + "l" + t.transacao + "] ";
            try {
           } catch (Exception e) {
                System.out.println("Excecao:[" + e.getMessage() + "]");
            }

            if (!t.transacao.equals(op.transacao) && (                  
                    (t.tipo.equals("w") && op.tipo.equals("w"))
                    || 
                    (t.tipo.equals("r") && op.tipo.equals("w")) || (t.tipo
                    .equals("w") && op.tipo.equals("r")))) {
                result = true;
            }

        }
        //System.out.println(trancas);

        leitura = true;
        notifyAll();

        return result;
    }

    public void executar2PL(ArrayList<Op> ops, Thread thrd) {
        boolean abort = false;

        for (int i =0; i<ops.size() && !abort;i++) {
            Op op = ops.get(i);
            abort = false;

            while (Conflito(op, thrd.getName()) && !abort) {
                abort = enfileirar(op, thrd);
            }
          
                Main.tranca.add(new Tranca(op));
                System.out.println("Usuario "+thrd.getName() + " com tranca para " + op.tipo + " do "
                        +"estoque");
                System.out.println("Usuario "+thrd.getName() + " executando operacao");
                leitura = true;

                
            
        }
        liberaTranca(ops, thrd);
    }

    public synchronized void liberaTranca(ArrayList<Op> ops, Thread thrd) {
        Main.liberatranca = false;

        for (Op op : ops) {
            System.out.println("User "+thrd.getName()+" liberando trancas..");
            removeTranca(op);
        }
        System.out.println("User" + thrd.getName() + " liberou acesso");
        notifyAll();
        Main.liberatranca = true;
        leitura = true;
    }

}
