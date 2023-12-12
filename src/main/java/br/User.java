/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vitor
 */
package br;
import java.util.ArrayList;

class User implements Runnable {
   	public Thread thrd;
	private Compartilhado x;
	private ArrayList<Op> op;

	User(String name, Compartilhado x,ArrayList<Op> op) {
		thrd = new Thread(this, name);
		thrd.start();
		this.x = x;
		this.op = op;
	}

	public void run() {

		x.executar2PL(op,thrd);

	}	
}
