package tabuleiro;

public class Tabuleiro {

	private int fileiras;
	private int colunas;
	private Peca[][] pecas;

	public Tabuleiro(int fileiras, int colunas) {
		this.fileiras = fileiras;
		this.colunas = colunas;
		pecas = new Peca[fileiras][colunas];
	}

	public int getFileiras() {
		return fileiras;
	}

	public void setFileiras(int linhas) {
		this.fileiras = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}

	public Peca peca(int linha, int coluna) {
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		return pecas[posicao.getFileira()][posicao.getColuna()];
	}

	public void colocaPeca(Peca peca, Posicao posicao) {
		pecas[posicao.getFileira()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}

}
