package modelo;

import excecoes.PosicaoInvalida;

public class Tabuleiro
{
    private Casa[][] grid; //Matriz bidimensional representando o tabuleiro
    private int linhas; //Número de linhas
    private int colunas; //Número de colunas

    //Construtor, inicializa o tabuleiro criando as casas
    public Tabuleiro()
    {
        this.linhas = linhas;
        this.colunas = colunas;
        grid = new Casa[linhas][colunas];
        inicializar();
    }

    //Instancia todas as posições da matriz com casas vazias
    public void inicializar()
    {
        for (int i = 0; i < linhas; i++)
        {
            for (int j = 0; j < colunas; j++)
            {
                grid[i][j] = new Casa();
            }
        }
    }

    public void getLinhas()
    {
        return linhas;
    }

    public void getColunas()
    {
        return colunas;
    }

    //Valida se as coordenadas informadas estão dentro da matriz
    public boolean estaDentroDosLimites(int linha, int coluna)
    {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    //Retorna uma casa específica garantindo a validação dos limites
    public Casa getCasa(int linha, int coluna) throws PosicaoInvalida
    {
        if (!estaDentroDosLimites(linha, coluna))
        {
            throw new PosicaoInvalida("A posicao eh invalida.");
        }
        return grid[linha][coluna];
    }

    //Verifica se o tabuleiro está cheio
    public boolean estaCheio()
    {
        for (int i = 0; i < linhas; i++)
        {
            for (int j = 0; j < colunas; j++)
            {
                if (grid[i][j].estaVazia())
                {
                    return false;
                }
            }
        }
        return true;
    }
}
