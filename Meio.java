package Ethernet;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Meio {
	
	static Transmissor t;
	static Receptor r; 
	static Semaphore s;
	public static void main(String[] args) throws InterruptedException {
		t = new Transmissor();
		r = new Receptor();
		s = new Semaphore(1);
		int endDestino [] = r.getMac();
		
		Scanner ler = new Scanner(System.in);
		System.out.println("Insira o numero de dados a ser enviado em bytes: ");
		int n = ler.nextInt();
		ler.close();
		int[] quadros[] = t.definirDados(endDestino, n);
		s.acquire();
		
		for(int i = 0; i < quadros.length; i++){
			adicionarRuido(quadros[i]);
			quadros[i] = null;
		}
		s.release();
	}

	private static void adicionarRuido(int[] quadro){
		//random de 0 e 1 se for 0 não add ruido se 1 add ruido
		Random rd = new Random();
		int ruido = rd.nextInt(2);
		if(ruido == 1){
			if(quadro[50] == 0){
				quadro[50] = 1;
			}
			else{
				quadro[50] = 0;
			}
		}
		// chama envia quadro
		r.receberQuadro(quadro);
	}
}
