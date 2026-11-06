package Engine;

import excecoes.MovimentoInvalido;
import excecoes.PosicaoInvalida;
import modelo.Jogador;
import modelo.Tabuleiro;
import regras.RegraDeMovimento;
import regras.RegraDeCaptura;
import regras.CondicaoDeVitoria;

import java.util.List;

public class GameEngine
{
    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private int indiceAtual;
    private RegraDeMovimento regraMovimento;
    private RegraDeCaptura regraCaptura;
    private List<CondicaoDeVitoria> condicoesVitoria;
    private Jogador vencedor;
    private boolean empate;
    private boolean aguardandoCaptura; // Estado especial para a Trilha

    public GameEngine(Tabuleiro tabuleiro, List<Jogador> jogadores, RegraDeMovimento regraMovimento, RegraDeCaptura regraCaptura, List<CondicaoDeVitoria> condicoesVitoria)
    {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        this.regraMovimento = regraMovimento;
        this.regraCaptura = regraCaptura;
        this.condicoesVitoria = condicoesVitoria;
        this.indiceAtual = 0;
        this.aguardandoCaptura = false;
    }

    // Para o Jogos que a jogada não tem origem
    public void realizarJogada(int linhaDestino, int colunaDestino) throws PosicaoInvalida, MovimentoInvalido
    {
        realizarJogada(-1, -1, linhaDestino, colunaDestino);
    }

    // Para jogos que a peça tem um ponto de origem
    public void realizarJogada(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) throws PosicaoInvalida, MovimentoInvalido
    {
        if (isFimDeJogo()) throw new MovimentoInvalido("A partida já foi encerrada.");
        if (aguardandoCaptura) throw new MovimentoInvalido("Tens uma captura pendente. Escolhe a peça do adversário para remover.");

        Jogador atual = getJogadorAtual();
        regraMovimento.executarMovimento(tabuleiro, atual, linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);

        // Verifica se a jogada ativou um modo de captura (ex: fez Moinho/Trilha)
        if (regraCaptura != null && regraCaptura.verificaDireitoCaptura(tabuleiro, atual, linhaDestino, colunaDestino))
        {
            aguardandoCaptura = true;
            return; // Interrompe aqui. O turno não passa até o jogador chamar o método realizarCaptura().
        }

        finalizarTurno(atual);
    }

    // Metodo que o JavaFX chama quando o jogador clica na peça que quer comer
    public void realizarCaptura(int linhaAlvo, int colunaAlvo) throws PosicaoInvalida, MovimentoInvalido
    {
        if (!aguardandoCaptura || regraCaptura == null)
        {
            throw new MovimentoInvalido("Não tens nenhuma captura pendente neste momento.");
        }

        Jogador atual = getJogadorAtual();
        regraCaptura.executarCaptura(tabuleiro, atual, linhaAlvo, colunaAlvo);

        aguardandoCaptura = false;
        finalizarTurno(atual);
    }

    private void finalizarTurno(Jogador atual)
    {
        for (CondicaoDeVitoria condicao : condicoesVitoria)
        {
            if (condicao.verificarVitoria(tabuleiro, atual))
            {
                this.vencedor = atual;
                return;
            }
        }
        if (tabuleiro.estaCheio())
        {
            this.empate = true;
            return;
        }
        indiceAtual = (indiceAtual + 1) % jogadores.size();
    }

    public Jogador getJogadorAtual() { return jogadores.get(indiceAtual); }
    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public boolean isFimDeJogo() { return vencedor != null || empate; }
    public Jogador getVencedor() { return vencedor; }
    public boolean isEmpate() { return empate; }
    public boolean isAguardandoCaptura() { return aguardandoCaptura; } // Para o JavaFX saber que deve pedir uma captura
}
