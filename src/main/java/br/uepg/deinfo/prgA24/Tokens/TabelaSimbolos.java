package br.uepg.deinfo.prgA24.Tokens;
import java.util.Queue;
import java.util.LinkedList;

public class TabelaSimbolos {
    private Queue<TokenTipo[]> tabela;

    public TabelaSimbolos()
    {
        tabela = new LinkedList<TokenTipo[]>();
    }

    public void push(TokenTipo[] linha)
    {
        tabela.add(linha);
    }

    public TokenTipo[] get()
    {
        return tabela.poll();
    }
}
