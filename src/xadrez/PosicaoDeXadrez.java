package xadrez;

import tabuleiro.Posicao;

public class PosicaoDeXadrez {

	private char coluna;
	private int fileira;

	public PosicaoDeXadrez(char coluna, int fileira) {
		if (coluna < 'a' || coluna > 'h' || fileira < 1 || fileira > 8) {
			throw new ExcecaoDeXadrez("Erro ao instanciar a posicao de Xadrez. Posicoes validas sao de a1 a h8");
		}
		this.coluna = coluna;
		this.fileira = fileira;
	}

	public char getColuna() {
		return coluna;
	}

	public int getFileira() {
		return fileira;
	}

	protected Posicao toPosicao () {
		return new Posicao(8 - fileira, coluna - 'a');
	}
	
	protected static PosicaoDeXadrez daPosicao(Posicao posicao) {
		return new PosicaoDeXadrez((char)('a' + posicao.getColuna()), 8 - posicao.getFileira() );
	}
	
	@Override
	public String toString() {
		return "" + coluna + fileira;
	}
	
}
