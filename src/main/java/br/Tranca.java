/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br;

/**
 *
 * @author vitor
 */
public class Tranca {
		public String tipo="-1";
		public String transacao="-1";
		public Tranca(Op op){
			this.tipo=op.tipo;
			this.transacao=op.transacao;
		}		
		public String toString(){
			return tipo+" "+transacao;
		}
	}