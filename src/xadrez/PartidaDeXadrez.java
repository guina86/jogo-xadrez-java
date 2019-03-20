package xadrez;

import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {

	private Tabuleiro tabuleiro;

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		preparacao();
	}

	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getFileiras()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getFileiras(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez)tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	private void colocaNovaPeca(char coluna, int fileira, PecaDeXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoDeXadrez(coluna, fileira).toPosicao());
	}
	
	private void preparacao() {
		colocaNovaPeca('b', 6, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		colocaNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));
	}

}
