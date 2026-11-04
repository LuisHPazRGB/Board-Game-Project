package regras;

import modelo.Casa;
import modelo.Jogador;
import modelo.Tabuleiro;
import excecoes.PosicaoInvalida;

public class CondicaoVitoriaAlinhamento implements CondicaoDeVitoria
{
    private int quantidadeParaVencer;

    public CondicaoVitoriaAlinhamento(int quantidadeParaVencer)
    {
        this.quantidadeParaVencer = quantidadeParaVencer;
    }

    @Override
    public boolean verificarVitoria(Tabuleiro tabuleiro, Jogador jogador)
    {
        char simbolo = jogador.getPeca().getSimbolo();
        int linhas = tabuleiro.getLinhas();
        int colunas = tabuleiro.getColunas();

        for (int i = 0; i < linhas; i++)
        {
            for (int j = 0; j < colunas; j++)
            {
                if (verificarDirecao(tabuleiro, i, j, 0, 1, simbolo) || // Horizontal
                        verificarDirecao(tabuleiro, i, j, 1, 0, simbolo) || // Vertical
                        verificarDirecao(tabuleiro, i, j, 1, 1, simbolo) || // Diagonal Principal
                        verificarDirecao(tabuleiro, i, j, 1, -1, simbolo)) { // Diagonal Secundária
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verificarDirecao(Tabuleiro tab, int linha, int coluna, int dLinha, int dColuna, char simbolo)
    {
        for (int i = 0; i < quantidadeParaVencer; i++)
        {
            int r = linha + i * dLinha;
            int c = coluna + i * dColuna;
            if (r < 0 || r >= tab.getLinhas() || c < 0 || c >= tab.getColunas()) return false;

            try
            {
                Casa casa = tab.getCasa(r, c);
                if (casa.estaVazia() || casa.getPeca().getSimbolo() != simbolo) return false;
            }
            catch (PosicaoInvalida e)
            {
                return false;
            }
        }
        return true;
    }
}
