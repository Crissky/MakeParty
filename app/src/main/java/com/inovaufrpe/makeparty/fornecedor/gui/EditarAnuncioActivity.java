package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.TelaInicialClienteActivity;
import com.inovaufrpe.makeparty.cliente.gui.fragment.dialog.SimOuNaoDialog;

public class EditarAnuncioActivity extends AppCompatActivity {

    private Spinner spinnerTipoAnuncio;
    private EditText editTextTituloAnuncio,editTextValorAnuncio,editTextDescAnuncio,editTextTagsAnuncio,editTextTelefoneAnuncio;
    private EditText editTextRuaAnuncio,editTextNumeroEndAnuncio,editTextBairroEndAnuncio,editTextCidadeEndAnuncio,editTextCepEndAnuncio;
    private TextView textViewLimitesAnuncio;
    private Button buttonAtualizarAnuncio,buttonExcluirAnuncio;
    private ImageButton ImgButtonGalFotosAnex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_anuncio);
        encontrandoItensView();
    }
    public void encontrandoItensView(){
        spinnerTipoAnuncio = findViewById(R.id.spinnertipoAnuncioEditar);
        editTextTituloAnuncio = findViewById(R.id.editTextTituloEditarAn);
        editTextValorAnuncio = findViewById(R.id.editTextValorEditarAn);
        editTextDescAnuncio = findViewById(R.id.editTextDescricaoEditarAn);
        editTextTagsAnuncio = findViewById(R.id.editTextTagsEditarAn);
        editTextTelefoneAnuncio = findViewById(R.id.editTextTelefoneEditarAnun);
        editTextRuaAnuncio = findViewById(R.id.RuaIdEditarAnuncio);
        editTextNumeroEndAnuncio = findViewById(R.id.editTextNumeroEditarAnuncio);
        editTextBairroEndAnuncio = findViewById(R.id.editTextBairroEditarAnuncio);
        editTextCidadeEndAnuncio = findViewById(R.id.editTextCidadeEditarAnuncio);
        editTextCepEndAnuncio = findViewById(R.id.editTextCepEditarAnuncio);
        textViewLimitesAnuncio= findViewById(R.id. textViewObsLimitesAnuncioForn);
        ImgButtonGalFotosAnex = findViewById(R.id.imgButtonGalFotosAnexAnEdit);
        buttonAtualizarAnuncio = findViewById(R.id.button_atualizar_anuncio);
        buttonExcluirAnuncio = findViewById(R.id.button_excluir_anuncio);

    }
    public void setandoInfoItensView(){

    }
    public void atualizarItensAnuncio(){
        SimOuNaoDialog.show(getSupportFragmentManager(),"Deseja confirmar a atualização desse anúncio?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {

            }
        });
    }
    public void cliqueExcluindoAnuncio() {
        SimOuNaoDialog.show(getSupportFragmentManager(), "Deseja mesmo excluir esse anúncio ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {

            }
        });
    }
    public void msgToast(String msgToast){
        Toast.makeText(this, msgToast, Toast.LENGTH_SHORT).show();
        finish();
    }
    public void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        this.mudarTela(AnunciosFornecedorActivity.class);

    }

}
