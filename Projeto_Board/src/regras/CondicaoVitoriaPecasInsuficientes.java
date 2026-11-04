package regras;

import excecoes.PosicaoInvalida;
import modelo.Casa;
import modelo.Jogador;
import modelo.Tabuleiro;

public class CondicaoVitoriaPecasInsuficientes implements CondicaoDeVitoria
{
    private int minimoPecasAdversario;

    public CondicaoVitoriaPecasInsuficientes(int minimoPecasAdversario)
    {
        this.minimoPecasAdversario = minimoPecasAdversario;
    }

    @Override
    public boolean verificarVitoria(Tabuleiro tabuleiro, Jogador jogadorAtual)
    {
        int contagemPecasAdversario = 0;
        char simboloAtual = jogadorAtual.getPeca().getSimbolo();

        for (int i = 0; i < tabuleiro.getLinhas(); i++)
        {
            for (int j = 0; j < tabuleiro.getColunas(); j++)
            {
                try
                {
                    Casa casa = tabuleiro.getCasa(i, j);
                    if (!casa.estaVazia() && casa.getPeca().getSimbolo() != simboloAtual)
                    {
                        contagemPecasAdversario++;
                    }
                }
                catch (PosicaoInvalida e)
                {
                    // Ignora casas inválidas
                }
            }
        }
        // Se o adversário tiver menos que o mínimo permitido,garante vitoria
        return contagemPecasAdversario > 0 && contagemPecasAdversario <= minimoPecasAdversario;
    }
}