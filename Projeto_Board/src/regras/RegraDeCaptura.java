package regras;

import excecoes.MovimentoInvalido;
import excecoes.PosicaoInvalida;
import modelo.Jogador;
import modelo.Tabuleiro;

public interface RegraDeCaptura
{
    // Verifica se a jogada recém-feita dá o direito de capturar
    boolean verificaDireitoCaptura(Tabuleiro tabuleiro, Jogador jogadorAtual, int linhaDestino, int colunaDestino) throws PosicaoInvalida;

    // Executa a remoção da peça escolhida
    void executarCaptura(Tabuleiro tabuleiro, Jogador jogadorAtual, int linhaAlvo, int colunaAlvo) throws PosicaoInvalida, MovimentoInvalido;
}
