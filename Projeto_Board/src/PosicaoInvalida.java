package excecoes;

// Exceção customizada para posições fora dos limites do tabuleiro
public class PosicaoInvalida extends Excecao
{
    public PosicaoInvalida(String mensangem)
    {
        super(mensagem);
    }
}