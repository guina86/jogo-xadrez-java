package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

	private PartidaDeXadrez partidaDeXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
	}

	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	private boolean testaTorreRoque(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorDeMovimento() == 0;
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

		// #movimento especial Roque
		if (getContadorDeMovimento() == 0 && !partidaDeXadrez.getXeque()) {
			// #movimento especial Roque pequeno
			Posicao posT1 = new Posicao(posicao.getFileira(), posicao.getColuna() + 3);
			if (testaTorreRoque(posT1)) {
				Posicao p1 = new Posicao(posicao.getFileira(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getFileira(), posicao.getColuna() + 2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getFileira()][posicao.getColuna() + 2] = true;
				}
			}
			// #movimento especial Roque grande
			Posicao posT2 = new Posicao(posicao.getFileira(), posicao.getColuna() - 4);
			if (testaTorreRoque(posT2)) {
				Posicao p1 = new Posicao(posicao.getFileira(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getFileira(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getFileira(), posicao.getColuna() - 3);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null
						&& getTabuleiro().peca(p3) == null) {
					mat[posicao.getFileira()][posicao.getColuna() - 2] = true;
				}
			}
		}

		return mat;
	}

}
