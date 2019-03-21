package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecaoDeXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoDeXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		List<PecaDeXadrez> capturadas = new ArrayList<>();
		
		while(!partidaDeXadrez.getXequeMate()) {
			
			try {
				Interface.limpaTela();
				Interface.desenhaPartida(partidaDeXadrez, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoDeXadrez origem = Interface.leiaPosicaoXadrez(sc);
				boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(origem);
				Interface.limpaTela();
				Interface.desenhaTabuleiro(partidaDeXadrez.getPecas(), movimentosPossiveis);
				System.out.println();
				System.out.print("Destino: ");
				PosicaoDeXadrez destino = Interface.leiaPosicaoXadrez(sc);
				PecaDeXadrez pecaCapturada = partidaDeXadrez.movePeca(origem, destino);
				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
			} catch (ExcecaoDeXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
		Interface.limpaTela();
		Interface.desenhaPartida(partidaDeXadrez, capturadas);
	}

}
