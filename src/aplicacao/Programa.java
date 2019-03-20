package aplicacao;

import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoDeXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		
		while(true) {
			
			Interface.desenhaTabuleiro(partidaDeXadrez.getPecas());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoDeXadrez origem = Interface.leiaPosicaoXadrez(sc);
			System.out.print("Destino: ");
			PosicaoDeXadrez destino = Interface.leiaPosicaoXadrez(sc);
			PecaDeXadrez pecaCapturada = partidaDeXadrez.movePeca(origem, destino);
			
		}
		
	}

}
