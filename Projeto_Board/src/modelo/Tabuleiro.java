package modelo;

import excecoes.PosicaoInvalida;

public class Tabuleiro
{
    private Casa[][] grid; //Matriz bidimensional representando o tabuleiro
    private int linhas; //Número de linhas
    private int colunas; //Número de colunas
    private int pecasNoTabuleiro;

    //Construtor, inicializa o tabuleiro criando as casas
    public Tabuleiro()
    {
        this.linhas = linhas;
        this.colunas = colunas;
        grid = new Casa[linhas][colunas];
        this.pecasNoTabuleiro = 0;
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

    //O Tabuleiro controla a entrada de peças e atualiza o contador
    public void colocarPeca(int linha, int coluna, Peca peca) throws PosicaoInvalida
    {
        Casa casa = getCasa(linha, coluna);
        if (casa.estaVazia())
        {
            pecasNoTabuleiro++; // Aumenta o contador
        }
        casa.setPeca(peca);
    }

    //O Tabuleiro controla a remoção (captura) e atualiza o contador
    public void removerPeca(int linha, int coluna) throws PosicaoInvalida
    {
        Casa casa = getCasa(linha, coluna);
        if (!casa.estaVazia())
        {
            pecasNoTabuleiro--; // Diminui o contador
        }
        casa.setPeca(null);
    }

    //Verifica se o tabuleiro está cheio
    public boolean estaCheio()
    {
        return pecasNoTabuleiro == (linhas * colunas);
    }
}
