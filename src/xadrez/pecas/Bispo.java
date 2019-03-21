package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getFileiras()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima a Esquerda
		p.setValores(posicao.getFileira() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
			p.setValores(p.getFileira() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// acima a direita
		p.setValores(posicao.getFileira() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
			p.setValores(p.getFileira() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// abaixo a esquerda
		p.setValores(posicao.getFileira() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
			p.setValores(p.getFileira() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// abaixo a direita
		p.setValores(posicao.getFileira() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
			p.setValores(p.getFileira() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		return mat;
	}

}
