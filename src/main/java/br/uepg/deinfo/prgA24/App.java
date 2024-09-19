package br.uepg.deinfo.prgA24;

import java.io.File;

import br.uepg.deinfo.prgA24.Exception.TokenInvalido;


public class App {
    public static void main(String[] args){
        if(args.length != 2)
        {
            System.err.println("Uso -f arquivo.prg");
            System.exit(-1);
        }
        File arquivo = new File(args[1]);
        Scanner sc = new Scanner(arquivo);
        
        try {
            Parse analisador = new Parse(sc.getTabela());
            analisador.analisar();
        } catch (TokenInvalido e) {
            System.err.println("token invalido "+ e.getMessage());
        }
        catch(RuntimeException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
