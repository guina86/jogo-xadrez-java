package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);

	}
	
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getFileiras()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);
		
		if(getCor() == Cor.BRANCO) {
			p.setValores(posicao.getFileira() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getFileira()][p.getColuna()] = true;
			}
			p.setValores(posicao.getFileira() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getFileira() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().temPeca(p2) && getContadorDeMovimento() == 0) {
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
		} else {
			p.setValores(posicao.getFileira() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getFileira()][p.getColuna()] = true;
			}
			p.setValores(posicao.getFileira() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getFileira() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().temPeca(p2) && getContadorDeMovimento() == 0) {
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
		}

		return mat;
	}
	
}
