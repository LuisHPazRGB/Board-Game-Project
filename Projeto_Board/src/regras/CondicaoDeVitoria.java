package regras;

import modelo.Jogador;
import modelo.Tabuleiro;

public interface CondicaoDeVitoria
{
    boolean verificarVitoria(Tabuleiro tabuleiro, Jogador jogador);
}