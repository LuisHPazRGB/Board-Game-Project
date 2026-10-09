package modelo;

public class Tabuleiro
{
    private Casa[][] grid; //Matriz bidimensional representando o tabuleiro
    private int linhas = 3; //Número de linhas
    private in colunas = 3; //Número de colunas

    //Construtor , inicializa o tabuleiro criando as casas
    public Tabuleiro()
    {
        grid = new Casa[linhas][colunas];
        inicializar();
    }
    //Metodo auxiliar para instanciar todas as posições todas as posições da matriz com as células vazias
    public void inicializar()
    {
        for (int i = 0 ; i < linhas ; i++)
        {
            for(int j = 0 ; i < colunas ; i++)
            {
                grid[i][j] = new Casa();
            }
        }
    }

    //Válida se as coordenas informadas estão dentro da matriz
    public boolean estaDentroDosLimites(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    //Retorna uma célula específica garantindo a validação dos limites
    public Casa getCasa(int linha , int coluna)
    {
        if estaDentroDosLimites(linha , coluna)
    {
        throw new excecoes.PosicaoInvalida("A posicao eh invalida");
    }

        return grid[linhas][colunas];
    }

    //Verifica se o tabuleiro está cheio
    public boolean estaCheio()
    {
        for (int i = 0 ; i < linhas ; i++)
        {
            for(int j = 0 ; i < colunas ; i++)
            {
                if (grid[i][j].estaVazia());
                {
                    return false;
                }
            }
        }
        return true;
    }

    //Forma o tabuleiro
    public void formarTabuleiro()
    {
        System.out.println("\n    0   1   2");
        for (int i = 0; i < linhas ; i++)
        {
            System.out.println("  +---+---+---+");
            System.out.println();
            System.out.println(i + " ");
            for (int j = 0 ; j < colunas ; j++)
            {
                char simbolo = grid[i][j].estaVazia() ? ' ' : grid[i][j].getPeca().getsimbolo();
                System.out.println("| " + simbolo + " ");
            }
            System.out.println("|");
        }
        System.out.println("  +---+---+---+");
    }



}
