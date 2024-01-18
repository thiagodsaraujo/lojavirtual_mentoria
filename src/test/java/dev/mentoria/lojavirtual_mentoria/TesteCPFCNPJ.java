package dev.mentoria.lojavirtual_mentoria;

import dev.mentoria.lojavirtual_mentoria.util.ValidaCNPJ;
import dev.mentoria.lojavirtual_mentoria.util.ValidaCPF;

public class TesteCPFCNPJ {

    public static void main(String[] args){

        var cnpj = ValidaCNPJ.isCNPJ("38.787.462/0001-73");
        var cnpjImp = ValidaCNPJ.imprimeCNPJ("38.787.462/0001-73");

        System.out.println("CNPJ VÁLIDO: "+ cnpj);
        System.out.println("CNPJ VÁLIDO: "+ cnpjImp);

        var cpf = ValidaCPF.isCPF("060.110.434-02");
        var cpfImp = ValidaCPF.imprimeCPF("060.110.434-02");
        System.out.println("CPF VÁLIDO: " + cpf);
        System.out.println("CPF VÁLIDO: " + cpfImp);
    }
}
