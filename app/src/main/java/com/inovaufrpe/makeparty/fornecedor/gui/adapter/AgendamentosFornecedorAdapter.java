package com.inovaufrpe.makeparty.fornecedor.gui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Agendamento;

import java.text.SimpleDateFormat;
import java.util.List;

public class AgendamentosFornecedorAdapter extends ArrayAdapter<Agendamento> {
    private List<Agendamento> agendamentos;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private Context context;

    public AgendamentosFornecedorAdapter(@NonNull Context context, List<Agendamento> agendamentos) {
        super(context, R.layout.adapter_agendamento, agendamentos);
        this.agendamentos = agendamentos;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_agendamento, parent, false);
        TextView nome = view.findViewById(R.id.nome);
        TextView servico = view.findViewById(R.id.servico);
        TextView horario = view.findViewById(R.id.horario);
        TextView situacao = view.findViewById(R.id.situacao);

        nome.setText(agendamentos.get(position).getPessoaFisica().getName());
        servico.setText(agendamentos.get(position).getAnuncio().getTitle());

        String inicio = sdf.format(agendamentos.get(position).getDateInicio());
        String fim = sdf.format(agendamentos.get(position).getDateFim());
        String hora = inicio + " - " + fim;

        horario.setText(hora);
        situacao.setText(agendamentos.get(position).getSituacao());
        return view;
    }
}
