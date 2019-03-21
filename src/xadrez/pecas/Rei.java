package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getFileiras()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima
		p.setValores(posicao.getFileira() - 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// acima a esquerda
		p.setValores(posicao.getFileira() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// acima a direita
		p.setValores(posicao.getFileira() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// esquerda
		p.setValores(posicao.getFileira(), posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// direita
		p.setValores(posicao.getFileira(), posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// abaixo
		p.setValores(posicao.getFileira() + 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// abaixo a esquerda
		p.setValores(posicao.getFileira() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}
		// abaixo a direita
		p.setValores(posicao.getFileira() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		return mat;
	}

}
