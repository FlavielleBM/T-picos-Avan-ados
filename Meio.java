package Ethernet;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Meio {
	
	static Transmissor transmissor;
	static Receptor receptor; 
	static Semaphore semaphore;
	public static void main(String[] args) throws InterruptedException {
		transmissor = new Transmissor();
		receptor = new Receptor();
		semaphore = new Semaphore(1);
		int endDestino [] = receptor.getMac();
		
		Scanner lerDados = new Scanner(System.in);
		System.out.println("Insira o numero de dados a ser enviado em bytes: ");
		int numeroDados = lerDados.nextInt();
		lerDados.close();
		int[] quadros[] = transmissor.definirDados(endDestino, numeroDados);
		semaphore.acquire();
		
		for(int tamanho = 0; tamanho < quadros.length; tamanho++){
			adicionarRuido(quadros[tamanho]);
			

			quadros[tamanho] = null;
		}
		semaphore.release();
	}

	/*Inserir um bit de ruido na sequencia do quadro
	caso seja 1 subtituirá por 0 e vice-versa*/
	private static void adicionarRuido(int[] quadro){

		Random numeroAleatorio = new Random();
		int inserirRuido = numeroAleatorio.nextInt(2);
		if(inserirRuido == 1){
			if(quadro[50] == 0){
				quadro[50] = 1;
			}
			else{
				quadro[50] = 0;
			}
		}
	
		receptor.enviarQuadro(quadro);
	}
}
