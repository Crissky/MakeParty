package com.inovaufrpe.makeparty.infra.utils;

import com.inovaufrpe.makeparty.cliente.dominio.Customer;
import com.inovaufrpe.makeparty.fornecedor.dominio.Agendamento;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;
import com.inovaufrpe.makeparty.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AgendamentoBuilder {

    public static List<Agendamento> gerarAgendamentos(){
        List<Agendamento> list = new ArrayList<>();
        String[] nomes = new String[]{"Lucas Carvalho", "Carlos Rogério", "Maria Júlia", "Renata Almeida"};
        Agendamento agendamento;
        Ads anuncio = new Ads();
        anuncio.setTitle("Buffet para festa infantil");
        for (int i = 0; i < 4; i++) {
            agendamento = new Agendamento();
            agendamento.setCustomer(new Customer(new Usuario(), nomes[i], "", "", ""));
            agendamento.setAnuncio(anuncio);
            agendamento.setSituacao("Confirmado");
            List<Date> list1;
            if (i == 0) {
                list1 = getDate(1);
            } else{
                list1 = getDate((i * 2) + 1);
            }
            agendamento.setDateInicio(list1.get(0));
            agendamento.setDateFim(list1.get(1));
            list.add(agendamento);
        }
        return list;
    }


    private static List<Date> getDate(int dias){
            List<Date> list = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, dias);
            calendar.set(Calendar.HOUR_OF_DAY, 14);
            calendar.set(Calendar.MINUTE, 0);
            list.add(new Date(calendar.getTimeInMillis()));
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            list.add(new Date(calendar.getTimeInMillis()));
            return list;
    }
}
