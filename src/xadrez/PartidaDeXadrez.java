package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
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
	
	public PecaDeXadrez movePeca (PosicaoDeXadrez posicaoDeOrigem, PosicaoDeXadrez posicaoDeDestino) {
		Posicao origem = posicaoDeOrigem.toPosicao();
		Posicao destino = posicaoDeDestino.toPosicao();
		validarPosicaoDeOrigem(origem);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		return (PecaDeXadrez)pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocaPeca(p, destino);
		return pecaCapturada;
	}

	private void validarPosicaoDeOrigem(Posicao posicao) {
		if(!(tabuleiro.temPeca(posicao))) {
			throw new ExcecaoDeXadrez("Nao existe peca na posicao de origem");
		}
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new ExcecaoDeXadrez("Nao existe movimentos possiveis para peca escolhida");
		}
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
