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

		colocaNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        colocaNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
        
	}

}
