package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private PecaDeXadrez vulneravelAoPassar;
	private PecaDeXadrez promovido;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		preparacao();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getXeque() {
		return xeque;
	}

	public boolean getXequeMate() {
		return xequeMate;
	}

	public PecaDeXadrez getVulneravelAoPassar() {
		return vulneravelAoPassar;
	}

	public PecaDeXadrez getPromovido() {
		return promovido;
	}

	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getFileiras()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getFileiras(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] movimentosPossiveis(PosicaoDeXadrez posicaoDeOrigem) {
		Posicao posicao = posicaoDeOrigem.toPosicao();
		validarPosicaoDeOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}

	public PecaDeXadrez movePeca(PosicaoDeXadrez posicaoDeOrigem, PosicaoDeXadrez posicaoDeDestino) {
		Posicao origem = posicaoDeOrigem.toPosicao();
		Posicao destino = posicaoDeDestino.toPosicao();
		validarPosicaoDeOrigem(origem);
		validarPosicaoDeDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);

		if (testaXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExcecaoDeXadrez("Voce nao pode se colocar em Xeque");
		}

		PecaDeXadrez pecaMovida = (PecaDeXadrez)tabuleiro.peca(destino);

		// #movimento especial promocao
		promovido = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCor() == Cor.BRANCO && destino.getFileira() == 0)
					|| (pecaMovida.getCor() == Cor.PRETO && destino.getFileira() == 7)) {
				promovido = (PecaDeXadrez) tabuleiro.peca(destino);
				promovido = trocaPecaPromovida("Q");
			}
		}

		xeque = testaXeque(oponente(jogadorAtual));

		if (testaXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}

		// #movimento especial Ao passar
		if (pecaMovida instanceof Peao && (destino.getFileira() == origem.getFileira() - 2
				|| destino.getFileira() == origem.getFileira() + 2)) {
			vulneravelAoPassar = pecaMovida;
		} else {
			vulneravelAoPassar = null;
		}

		return (PecaDeXadrez) pecaCapturada;
	}

	public PecaDeXadrez trocaPecaPromovida(String tipo) {
		if(promovido == null) {
			throw new IllegalStateException("Nao existe peca para ser promovida");
		}
		if(!tipo.equals("B") && !tipo.equals("T") && !tipo.equals("C") && !tipo.equals("Q")) {
			throw new InvalidParameterException("Tipo invalido para promocao");
		}
		
		Posicao pos = promovido.getPosicaoDeXadrez().toPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaDeXadrez novaPeca = novaPeca(tipo, promovido.getCor());
		tabuleiro.colocaPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}

	private PecaDeXadrez novaPeca(String tipo, Cor cor) {
		if (tipo.equals("B"))
			return new Bispo(tabuleiro, cor);
		if (tipo.equals("Q"))
			return new Rainha(tabuleiro, cor);
		if (tipo.equals("C"))
			return new Cavalo(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removePeca(origem);
		p.incrementaContadorDeMovimentos();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocaPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// #movimento especial Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getFileira(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getFileira(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.colocaPeca(torre, destinoT);
			torre.incrementaContadorDeMovimentos();
		}

		// #movimento especial Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getFileira(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getFileira(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.colocaPeca(torre, destinoT);
			torre.incrementaContadorDeMovimentos();
		}

		// #movimento especial Ao passar
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoDoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoDoPeao = new Posicao(destino.getFileira() + 1, destino.getColuna());
				} else {
					posicaoDoPeao = new Posicao(destino.getFileira() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(posicaoDoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removePeca(destino);
		p.decrementaContadorDeMovimentos();
		tabuleiro.colocaPeca(p, origem);
		if (pecaCapturada != null) {
			tabuleiro.colocaPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// #movimento especial Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getFileira(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getFileira(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.colocaPeca(torre, origemT);
			torre.decrementaContadorDeMovimentos();
		}

		// #movimento especial Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getFileira(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getFileira(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.colocaPeca(torre, origemT);
			torre.decrementaContadorDeMovimentos();
		}

		// #movimento especial Ao passar
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == vulneravelAoPassar) {
				PecaDeXadrez peao = (PecaDeXadrez) tabuleiro.removePeca(destino);
				Posicao posicaoDoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoDoPeao = new Posicao(3, destino.getColuna());
				} else {
					posicaoDoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.colocaPeca(peao, posicaoDoPeao);
			}
		}

	}

	private void validarPosicaoDeOrigem(Posicao posicao) {
		if (!(tabuleiro.temPeca(posicao))) {
			throw new ExcecaoDeXadrez("Nao existe peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecaoDeXadrez("A peca escolhida nao e sua");
		}
		if (!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new ExcecaoDeXadrez("Nao existe movimentos possiveis para peca escolhida");
		}
	}

	private void validarPosicaoDeDestino(Posicao origem, Posicao destino) {

		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new ExcecaoDeXadrez("A peca escolhida nao pode se mover para a posicao de destino");
		}

	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaDeXadrez) p;
			}
		}
		throw new IllegalStateException("Nao existe Rei " + cor + " no tabuleiro");
	}

	private boolean testaXeque(Cor cor) {
		Posicao posicaoDoRei = rei(cor).getPosicaoDeXadrez().toPosicao();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasDoOponente) {
			if (p.movimentoPossivel(posicaoDoRei)) {
				return true;
			}
		}
		return false;
	}

	private boolean testaXequeMate(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < mat.length; i++) {
				for (int j = 0; j < mat.length; j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaDeXadrez) p).getPosicaoDeXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testeDeXeque = testaXeque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeDeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocaNovaPeca(char coluna, int fileira, PecaDeXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoDeXadrez(coluna, fileira).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void preparacao() {

		colocaNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocaNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocaNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocaNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocaNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocaNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocaNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocaNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocaNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));

	}

}
