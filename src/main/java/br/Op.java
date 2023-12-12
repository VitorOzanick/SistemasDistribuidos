/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br;

/**
 *
 * @author vitor
 */
public class Op {
		public String tipo="-1";
		public String transacao="-1";
		public Op(String tipo, String transacao){
			this.tipo=tipo;
			this.transacao=transacao;
		}
		public String toString(){
			return tipo+" "+transacao;
		}
	}