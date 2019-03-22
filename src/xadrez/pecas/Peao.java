package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	private PartidaDeXadrez partidaDeXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getFileiras()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			p.setValores(posicao.getFileira() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getFileira()][p.getColuna()] = true;
			}
			p.setValores(posicao.getFileira() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getFileira() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && getTabuleiro().existePosicao(p2)
					&& !getTabuleiro().temPeca(p2) && getContadorDeMovimento() == 0) {
				mat[p.getFileira()][p.getColuna()] = true;
			}
			p.setValores(posicao.getFileira() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
				mat[p.getFileira()][p.getColuna()] = true;
			}
			p.setValores(posicao.getFileira() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
				mat[p.getFileira()][p.getColuna()] = true;
			}

			// #movimento especial Ao passar branco
			if (posicao.getFileira() == 3) {
				Posicao esquerda = new Posicao(posicao.getFileira(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && temPecaDoAdversario(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.getVulneravelAoPassar()) {
					mat[esquerda.getFileira() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getFileira(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && temPecaDoAdversario(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.getVulneravelAoPassar()) {
					mat[direita.getFileira() - 1][direita.getColuna()] = true;
				}
			}

		} else {
			p.setValores(posicao.getFileira() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getFileira()][p.getColuna()] = true;
			}
			p.setValores(posicao.getFileira() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getFileira() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && getTabuleiro().existePosicao(p2)
					&& !getTabuleiro().temPeca(p2) && getContadorDeMovimento() == 0) {
				mat[p.getFileira()][p.getColuna()] = true;
			}
			p.setValores(posicao.getFileira() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
				mat[p.getFileira()][p.getColuna()] = true;
			}
			p.setValores(posicao.getFileira() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && temPecaDoAdversario(p)) {
				mat[p.getFileira()][p.getColuna()] = true;
			}

			// #movimento especial Ao passar preto
			if (posicao.getFileira() == 4) {
				Posicao esquerda = new Posicao(posicao.getFileira(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && temPecaDoAdversario(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.getVulneravelAoPassar()) {
					mat[esquerda.getFileira() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getFileira(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && temPecaDoAdversario(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.getVulneravelAoPassar()) {
					mat[direita.getFileira() + 1][direita.getColuna()] = true;
				}
			}
		}

		return mat;
	}

}
