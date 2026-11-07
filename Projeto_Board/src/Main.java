import javafx.application.Application;
import ui.AppTerminal;
import gui.AppGrafica;
import Engine.GameEngine;
import Engine.GameLoader;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Como deseja jogar?\n1 - Terminal\n2 - Interface Gráfica");
        int escolha = scanner.nextInt();

        if (escolha == 1)
        {
            try
            {
                GameEngine engine = GameLoader.carregarJogo("jogos/jogo_da_velha.json");
                AppTerminal.jogar(engine);
            }
            catch (Exception e)
            {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        else
        {
            Application.launch(AppGrafica.class, args);
        }
    }
}