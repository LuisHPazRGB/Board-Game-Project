package ui;

import Engine.GameEngine;
import modelo.Tabuleiro;
import excecoes.MovimentoInvalido;
import excecoes.PosicaoInvalida;
import java.util.Scanner;

public class AppTerminal
{

    public static void jogar(GameEngine engine)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Partida Iniciada ===");

        while (!engine.isFimDeJogo())
        {
            imprimirTabuleiro(engine.getTabuleiro());
            System.out.println("\nVez do jogador: " + engine.getJogadorAtual().getNome() +
                    " (Peça: " + engine.getJogadorAtual().getPeca().getSimbolo() + ")");

            try
            {
                if (engine.isAguardandoCaptura())
                {
                    System.out.print("Captura pendente! Digite a LINHA e COLUNA da peça alvo (ex: 0 1): ");
                    int linha = scanner.nextInt();
                    int coluna = scanner.nextInt();
                    engine.realizarCaptura(linha, coluna);
                }
                else
                {
                    // Para o Jogo da Velha, pedimos apenas o destino
                    System.out.print("Digite a LINHA e COLUNA para jogar (ex: 0 2): ");
                    int linha = scanner.nextInt();
                    int coluna = scanner.nextInt();
                    engine.realizarJogada(linha, coluna);
                }
            }
            catch (PosicaoInvalida | MovimentoInvalido e)
            {
                System.out.println("\n[ERRO] " + e.getMessage() + " Tente novamente.");
            }
            catch (Exception e)
            {
                System.out.println("\n[ERRO] Entrada inválida. Digite apenas números.");
                scanner.nextLine(); // Limpa o buffer do teclado em caso de erro de digitação
            }
        }

        imprimirTabuleiro(engine.getTabuleiro());

        System.out.println("\n=== FIM DE JOGO ===");
        if (engine.isEmpate())
        {
            System.out.println("A partida terminou em EMPATE!");
        }
        else
        {
            System.out.println("Parabéns! O vencedor é: " + engine.getVencedor().getNome());
        }

        scanner.close();
    }

    private static void imprimirTabuleiro(Tabuleiro tab)
    {
        System.out.println();
        for (int i = 0; i < tab.getLinhas(); i++)
        {
            for (int j = 0; j < tab.getColunas(); j++)
            {
                try
                {
                    if (tab.getCasa(i, j).estaVazia())
                    {
                        System.out.print("[ ] ");
                    }
                    else {
                        System.out.print("[" + tab.getCasa(i, j).getPeca().getSimbolo() + "] ");
                    }
                }
                catch (PosicaoInvalida e) {
                    System.out.print("    "); // Espaço vazio para casas inválidas (útil na Trilha)
                }
            }
            System.out.println();
        }
    }
}