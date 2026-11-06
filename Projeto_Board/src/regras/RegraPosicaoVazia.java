package regras;

import excecoes.MovimentoInvalido;
import excecoes.PosicaoInvalida;
import modelo.Casa;
import modelo.Jogador;
import modelo.Tabuleiro;

public class RegraPosicaoVazia implements RegraDeMovimento
{
    @Override
    public void executarMovimento(Tabuleiro tabuleiro, Jogador jogador, int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) throws PosicaoInvalida, MovimentoInvalido
    {
        Casa casaDestino = tabuleiro.getCasa(linhaDestino, colunaDestino);

        if (!casaDestino.estaVazia())
        {
            throw new MovimentoInvalido("A casa de destino já está ocupada.");
        }

        // Se houver origem, remove a peça da casa antiga
        if (linhaOrigem != -1 && colunaOrigem != -1)
        {
            Casa casaOrigem = tabuleiro.getCasa(linhaOrigem, colunaOrigem);
            if (casaOrigem.estaVazia() || casaOrigem.getPeca().getSimbolo() != jogador.getPeca().getSimbolo())
            {
                throw new MovimentoInvalido("Jogada invalida na origem");
            }
            // Remove a peça da origem (o contador diminui temporariamente)
            tabuleiro.removerPeca(linhaOrigem, colunaOrigem);
        }
        // Coloca no destino (o contador aumenta novamente, ficando equilibrado num movimento normal)
        tabuleiro.colocarPeca(linhaDestino, colunaDestino, jogador.getPeca());
    }
}