package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {

	private Cor cor;
	private int contadorDeMovimentos;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public PosicaoDeXadrez getPosicaoDeXadrez() {
		return PosicaoDeXadrez.daPosicao(posicao);
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContadorDeMovimento() {
		return contadorDeMovimentos;
	}
	
	public void incrementaContadorDeMovimentos() {
		contadorDeMovimentos++;
	}
	
	public void decrementaContadorDeMovimentos() {
		contadorDeMovimentos--;
	}

	protected boolean temPecaDoAdversario(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
