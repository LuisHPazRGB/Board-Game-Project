package regras;

import excecoes.MovimentoInvalido;
import excecoes.PosicaoInvalida;
import modelo.Jogador;
import modelo.Tabuleiro;

public interface RegraDeMovimento
{
    void executarMovimento(Tabuleiro tabuleiro, Jogador jogador, int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) throws PosicaoInvalida, MovimentoInvalido;
}