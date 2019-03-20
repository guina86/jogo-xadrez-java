package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Torre extends PecaDeXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getFileiras()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima
		p.setValores(posicao.getFileira() - 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
			p.setFileira(p.getFileira() - 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// abaixo
		p.setValores(posicao.getFileira() + 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
			p.setFileira(p.getFileira() + 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// a esquerda
		p.setValores(posicao.getFileira(), posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		// a direita
		p.setValores(posicao.getFileira(), posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
			mat[p.getFileira()][p.getColuna()] = true;
		}

		return mat;
	}

}
