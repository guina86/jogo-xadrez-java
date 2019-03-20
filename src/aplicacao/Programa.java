package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExcecaoDeXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoDeXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		
		while(true) {
			
			try {
				Interface.limpaTela();
				Interface.desenhaTabuleiro(partidaDeXadrez.getPecas());
				System.out.println();
				System.out.print("Origem: ");
				PosicaoDeXadrez origem = Interface.leiaPosicaoXadrez(sc);
				System.out.print("Destino: ");
				PosicaoDeXadrez destino = Interface.leiaPosicaoXadrez(sc);
				PecaDeXadrez pecaCapturada = partidaDeXadrez.movePeca(origem, destino);
			} catch (ExcecaoDeXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
		
	}

}
