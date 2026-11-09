
import ui.AppTerminal;
import engine.GameEngine;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando)
        {
            System.out.println("\n=== MOTOR DE JOGOS DE TABULEIRO ===");
            System.out.println("1 - Jogar no Terminal");
            System.out.println("2 - Interface Gráfica");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            int modo = scanner.nextInt();

            if (modo == 1)
            {
                System.out.println("\nQual jogo deseja carregar?");
                System.out.println("1 - Jogo da Velha");
                System.out.println("2 - Trilha");
                System.out.print("Escolha o jogo: ");
                int opcaoJogo = scanner.nextInt();

                String caminhoArquivo;
                if (opcaoJogo == 1)
                {
                    caminhoArquivo = "Projeto_Board/jogos/jogo_da_velha.json";
                }
                else if (opcaoJogo == 2)
                {
                    caminhoArquivo = "Projeto_Board/jogos/trilha.json";
                }
                else
                {
                    System.out.println("Opção inválida! Retornando ao menu.");
                    continue;
                }

                // Inicia a sessão no terminal enviando o caminho do JSON
                AppTerminal.iniciarSessao(caminhoArquivo);
            }
            else if (modo == 2)
            {
                System.out.println("\nA interface gráfica ainda esta em desenvolvimento...");
            }
            else if (modo == 3)
            {
                System.out.println("\nSaindo do programa. Até logo!");
                executando = false;
            }
            else
            {
                System.out.println("\nOpção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
}