package ui;

import engine.GameEngine;
import engine.GameLoader;
import modelo.Tabuleiro;
import excecoes.MovimentoInvalido;
import excecoes.PosicaoInvalida;
import java.util.Scanner;

public class AppTerminal
{
    public static void iniciarSessao(String caminhoArquivo)
    {
        Scanner scanner = new Scanner(System.in);
        int vitoriasJogador1 = 0;
        int vitoriasJogador2 = 0;
        int empates = 0;
        boolean continuarJogando = true;

        while (continuarJogando)
        {
            try
            {
                // Carrega uma nova instância da Engine a cada partida (tabuleiro limpo)
                GameEngine engine = GameLoader.carregarJogo(caminhoArquivo);
                jogarPartida(engine, scanner);

                // Atualização do placar
                if (engine.isEmpate())
                {
                    empates++;
                }
                else if (engine.getVencedor() != null)
                {
                    // Como os nomes dos jogadores no sorteio são "Jogador 1" e "Jogador 2"
                    if (engine.getVencedor().getNome().equals("Jogador 1"))
                    {
                        vitoriasJogador1++;
                    }
                    else
                    {
                        vitoriasJogador2++;
                    }
                }

                // Exibição do placar acumulado
                System.out.println("\n==================================");
                System.out.println("              PLACAR");
                System.out.println("==================================");
                System.out.println("Jogador 1: " + vitoriasJogador1 + " vitória(s)");
                System.out.println("Jogador 2: " + vitoriasJogador2 + " vitória(s)");
                System.out.println("Empates:   " + empates);
                System.out.println("==================================");

                // Menu de Revanche / Voltar
                System.out.println("\nDeseja jogar novamente?");
                System.out.println("1 - Revanche");
                System.out.println("2 - Voltar ao Menu Principal");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                if (opcao != 1)
                {
                    continuarJogando = false;
                }

            }
            catch (Exception e)
            {
                System.out.println("\n[ERRO NA SESSÃO] " + e.getMessage());
                break;
            }
        }
    }

    public static void jogarPartida(GameEngine engine, Scanner scanner)
    {
        System.out.println("=== Partida Iniciada ===");

        // Calcula dinamicamente o último índice válido de linha e coluna baseado no JSON
        int maxLinha = engine.getTabuleiro().getLinhas() - 1;
        int maxColuna = engine.getTabuleiro().getColunas() - 1;

        while (!engine.isFimDeJogo())
        {
            imprimirTabuleiro(engine.getTabuleiro());
            System.out.println("\nVez do jogador: " + engine.getJogadorAtual().getNome() +
                    " (Peça: " + engine.getJogadorAtual().getPeca().getSimbolo() + ")");

            try
            {
                if (engine.isAguardandoCaptura())
                {
                    System.out.print("Captura pendente! Digite a LINHA e COLUNA da peça alvo (ex: 0 " + maxColuna + "): ");
                    String linhaLida = scanner.nextLine().trim();
                    String[] entrada = linhaLida.split("\\s+");

                    if (entrada.length != 2) throw new Exception("Formato inválido.");

                    int linha = Integer.parseInt(entrada[0]);
                    int coluna = Integer.parseInt(entrada[1]);
                    engine.realizarCaptura(linha, coluna);
                }
                else
                {
                    // Instrução unificada e discreta para qualquer tipo de jogo carregado na Engine
                    System.out.print("Digite as coordenadas da sua jogada (2 números para colocar, 4 para mover): ");

                    String linhaLida = scanner.nextLine().trim();
                    if (linhaLida.isEmpty()) continue; // Ignora se o utilizador apertar Enter sem querer

                    String[] entrada = linhaLida.split("\\s+");

                    if (entrada.length == 2)
                    {
                        // Modo Colocação
                        int linha = Integer.parseInt(entrada[0]);
                        int coluna = Integer.parseInt(entrada[1]);
                        engine.realizarJogada(linha, coluna);
                    }
                    else if (entrada.length == 4)
                    {
                        // Modo Movimento
                        int lOrigem = Integer.parseInt(entrada[0]);
                        int cOrigem = Integer.parseInt(entrada[1]);
                        int lDestino = Integer.parseInt(entrada[2]);
                        int cDestino = Integer.parseInt(entrada[3]);
                        engine.realizarJogada(lOrigem, cOrigem, lDestino, cDestino);
                    }
                    else
                    {
                        throw new Exception("Quantidade de coordenadas inválida. Use 2 ou 4 números.");
                    }
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
    }

    private static void imprimirTabuleiro(Tabuleiro tab)
    {
        System.out.println();

        // 1. Imprime os números das colunas no cabeçalho
        System.out.print("    "); // Espaço para alinhar com o número da linha
        for (int j = 0; j < tab.getColunas(); j++)
        {
            System.out.print(" " + j + "  ");
        }
        System.out.println();

        // 2. Imprime cada linha com o seu respectivo número na lateral
        for (int i = 0; i < tab.getLinhas(); i++)
        {
            System.out.print(i + "  "); // Imprime o número da linha no início
            for (int j = 0; j < tab.getColunas(); j++)
            {
                try
                {
                    if (tab.getCasa(i, j).estaVazia())
                    {
                        System.out.print("[ ] ");
                    }
                    else
                    {
                        System.out.print("[" + tab.getCasa(i, j).getPeca().getSimbolo() + "] ");
                    }
                }
                catch (PosicaoInvalida e)
                {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
    }
}