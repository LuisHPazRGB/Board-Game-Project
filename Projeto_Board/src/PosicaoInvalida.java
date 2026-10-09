package excecoes;

// Exceção customizada para posições fora dos limites do tabuleiro
public class PosicaoInvalida extends Exception
{
    public PosicaoInvalida(String mensangem)
    {
        super(mensagem);
    }
}