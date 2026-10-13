package jogo;

import excecoes.MovimentoInvalido;
import excecoes.PosicaoInvalida;
import modelo.Casa;
import modelo.Jogador;
import modelo.Peca;
import modelo.Tabuleiro;

import java.util.Scanner;

public class JogoDaVelha
{
    private Tabuleiro tabuleiro;
    private Jogador[] jogadores;
    private int indiceAtual; //0 ou 1: de quem é a vez
    private Scanner scanner;

    public JogoDaVelha(String nome1, String nome2)
    {
        tabuleiro = new Tabuleiro();
        jogadores = new Jogador[] {
            new Jogador(nome1, new Peca('X')),
            new Jogador(nome2, new Peca('O'))
        };
        indiceAtual = 0;
        scanner = new Scanner(System.in);
    }

    public void iniciar()
    {
        System.out.println("=== JOGO DA VELHA ===");
        Jogador vencedor = null;

        while (vencedor == null && !tabuleiro.estaCheio())
        {
            tabuleiro.formarTabuleiro();
            Jogador atual = jogadores[indiceAtual];
            System.out.println("\nVez de " + atual.getNome() + " (" + atual.getPeca().getSimbolo() + ")");

            try
            {
                int[] posicao = lerPosicao();
                jogar(atual, posicao[0], posicao[1]);

                if (verificarVitoria(atual.getPeca().getSimbolo()))
                {
                    vencedor = atual;
                }
                else
                {
                    indiceAtual = 1 - indiceAtual; //Alterna o jogador
                }
            }
            catch (PosicaoInvalida | MovimentoInvalido e)
            {
                System.out.println("Erro: " + e.getMessage() + " Tente novamente.");
            }
        }

        tabuleiro.formarTabuleiro();
        if (vencedor != null)
        {
            System.out.println("\nParabens, " + vencedor.getNome() + "! Voce venceu!");
        }
        else
        {
            System.out.println("\nDeu velha! Empate.");
        }
    }

    private int[] lerPosicao() throws PosicaoInvalida
    {
        try
        {
            System.out.print("Linha (0-2): ");
            int linha = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Coluna (0-2): ");
            int coluna = Integer.parseInt(scanner.nextLine().trim());
            return new int[] { linha, coluna };
        }
        catch (NumberFormatException e)
        {
            throw new PosicaoInvalida("Digite apenas numeros inteiros.");
        }
    }

    private void jogar(Jogador jogador, int linha, int coluna) throws PosicaoInvalida, MovimentoInvalido
    {
        Casa casa = tabuleiro.getCasa(linha, coluna); //Lança PosicaoInvalida se fora dos limites
        if (!casa.estaVazia())
        {
            throw new MovimentoInvalido("A casa (" + linha + "," + coluna + ") ja esta ocupada.");
        }
        casa.setPeca(jogador.getPeca());
    }

    private boolean verificarVitoria(char s) throws PosicaoInvalida
    {
        for (int i = 0; i < 3; i++)
        {
            if (ocupa(i, 0, s) && ocupa(i, 1, s) && ocupa(i, 2, s)) return true; //Linha
            if (ocupa(0, i, s) && ocupa(1, i, s) && ocupa(2, i, s)) return true; //Coluna
        }
        if (ocupa(0, 0, s) && ocupa(1, 1, s) && ocupa(2, 2, s)) return true; //Diagonal principal
        return ocupa(0, 2, s) && ocupa(1, 1, s) && ocupa(2, 0, s);           //Diagonal secundária
    }

    private boolean ocupa(int linha, int coluna, char s) throws PosicaoInvalida
    {
        Casa c = tabuleiro.getCasa(linha, coluna);
        return !c.estaVazia() && c.getPeca().getSimbolo() == s;
    }
}
