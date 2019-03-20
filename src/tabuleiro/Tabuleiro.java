package tabuleiro;

public class Tabuleiro {

	private int fileiras;
	private int colunas;
	private Peca[][] pecas;

	public Tabuleiro(int fileiras, int colunas) {
		if (fileiras < 1 || colunas < 1) {
			throw new ExcecaoDeTabuleiro("Erro ao criar o tabuleiro: Tem que ter pelo menos 1 fileira e 1 coluna");
		}
		this.fileiras = fileiras;
		this.colunas = colunas;
		pecas = new Peca[fileiras][colunas];
	}

	public int getFileiras() {
		return fileiras;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca(int fileira, int coluna) {
		if (!existePosicao(fileira, coluna)) {
			throw new ExcecaoDeTabuleiro("Posicao nao existe no tabuleiro");
		}
		return pecas[fileira][coluna];
	}

	public Peca peca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new ExcecaoDeTabuleiro("Posicao nao existe no tabuleiro");
		}
		return pecas[posicao.getFileira()][posicao.getColuna()];
	}

	public void colocaPeca(Peca peca, Posicao posicao) {
		if (temPeca(posicao)) {
			throw new ExcecaoDeTabuleiro("Ja existe peca nesta posicao");
		}
		pecas[posicao.getFileira()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}

	public Peca removePeca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new ExcecaoDeTabuleiro("Posicao nao existe no tabuleiro");
		}
		if (peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getFileira()][posicao.getColuna()] = null;
		return aux;
	}

	private boolean existePosicao(int fileira, int coluna) {
		return fileira >= 0 && fileira < fileiras && coluna >= 0 && coluna < colunas;
	}

	public boolean existePosicao(Posicao posicao) {
		return existePosicao(posicao.getFileira(), posicao.getColuna());
	}

	public boolean temPeca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new ExcecaoDeTabuleiro("Posicao nao existe no tabuleiro");
		}
		return peca(posicao) != null;
	}

}
