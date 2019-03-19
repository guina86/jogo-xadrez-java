package aplicacao;

import xadrez.PartidaDeXadrez;

public class Programa {

	public static void main(String[] args) {
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		Interface.desenhaTabuleiro(partidaDeXadrez.getPecas());
		
	}

}
