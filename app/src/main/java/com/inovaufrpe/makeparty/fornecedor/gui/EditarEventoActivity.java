package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.user.servico.ValidacaoGuiRapida;

public class EditarEventoActivity extends AppCompatActivity {
    private Button editarEvForn,excluirEvFornTelaEditarEv;
    ValidacaoGuiRapida validacaoGuiRapida = new ValidacaoGuiRapida();
    private String validar = "";
    boolean isValido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento_forn);
    }
    private void editarEventoOficial(String json) throws InterruptedException{
        callServer("PUT",json);
    }
    private void excluirEventoOficial(String json) throws InterruptedException{
        callServer("DELETE",json);
    }

    private void callServer(final String method, final String data) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (method.equals("PUT")){
                    validar = ConectarServidor.putJadiel("https://makepartyserver.herokuapp.com/events", data);
                    Log.i("Script", "OLHAAA: "+ validar);
                    if (validar.substring(2, 5).equals("err")){
                        // Não sei qual o erro
                        validar = "Não foi possível editar o evento";
                        // Rever a mensagem
                    }else{
                        validar = "Anúncio editado com sucesso";
                        isValido = true;
                    }
                }else{
                    validar = ConectarServidor.deleteDeJadiel("https://makepartyserver.herokuapp.com/events", data);
                    Log.i("Script", "OLHAAA: "+ validar);
                    if (validar.substring(2, 5).equals("err")){
                        // Não sei qual o erro
                        validar = "Não foi possível excluir o evento";
                        // Rever a mensagem
                    }else{
                        validar = "Evento excluido com sucesso";
                        isValido = true;
                    }
                }
            }
        });
        thread.start();
        thread.join();
    }
    public void msgToast(String msgToast){
        Toast.makeText(this, msgToast, Toast.LENGTH_SHORT).show();
        finish();
    }
    public void exibirMsgSeValidouAtualizaoOuExclusao(){
        Toast.makeText(getApplicationContext(), validar, Toast.LENGTH_SHORT).show();
    }

    public void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        this.mudarTela(CapturaDadosCalendarFornActivity.class);

    }

}
