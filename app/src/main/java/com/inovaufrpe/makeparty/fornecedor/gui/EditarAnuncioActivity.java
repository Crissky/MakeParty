package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.ProgressDialog;
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
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.usuario.gui.LoginActivity;
import com.inovaufrpe.makeparty.usuario.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.usuario.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;
import com.inovaufrpe.makeparty.usuario.dominio.Endereco;
import com.inovaufrpe.makeparty.usuario.servico.ValidacaoGuiRapida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditarAnuncioActivity extends AppCompatActivity {

    private Spinner spinnerTipoAnuncio;
    private EditText editTextTituloAnuncio,editTextValorAnuncio,editTextDescAnuncio,editTextTagsAnuncio,editTextTelefoneAnuncio;
    private EditText editTextRuaAnuncio,editTextNumeroEndAnuncio,editTextBairroEndAnuncio,editTextCidadeEndAnuncio,editTextCepEndAnuncio;
    private TextView textViewLimitesAnuncio;
    private Button buttonAtualizarAnuncio,buttonExcluirAnuncio;
    private ImageButton ImgButtonGalFotosAnex;
    ValidacaoGuiRapida validacaoGuiRapida = new ValidacaoGuiRapida();
    boolean isValido=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_anuncio);
        SessaoApplication.getInstance().setTelaAtual(EditarAnuncioActivity.class);
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
        acoesButoesAtualizarOuExcluirAnuncio();
        setandoInfoItensViewAntesEdicao();

    }
    public void acoesButoesAtualizarOuExcluirAnuncio(){
        buttonAtualizarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliqueAtualizarItensAnuncio();
            }
        });
        buttonExcluirAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliqueExcluindoAnuncio();
            }
        });
    }

    public void setandoInfoItensViewAntesEdicao(){
        Ads anuncioSelecionado = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();

        //spinnerTipoAnuncio.setOnItemSelectedListener(anuncioSelecionado.getType());

        editTextTituloAnuncio.setText(anuncioSelecionado.getTitle());
        //editTextValorAnuncio.setText((int) anuncioSelecionado.getPrice());
        editTextDescAnuncio.setText(anuncioSelecionado.getDescription());
        //editTextTagsAnuncio.setText((anuncioSelecionado.getTags().toString()));
        editTextTelefoneAnuncio.setText((anuncioSelecionado.getPhone()));
        /*editTextRuaAnuncio.setText(anuncioSelecionado.getAddress().getStreet());
        editTextNumeroEndAnuncio.setText(anuncioSelecionado.getAddress().getNumber());
        editTextBairroEndAnuncio.setText(anuncioSelecionado.getAddress().getNeighborhood());
        editTextCidadeEndAnuncio .setText(anuncioSelecionado.getAddress().getCity());
        editTextCepEndAnuncio.setText(anuncioSelecionado.getAddress().getZipcode());
        textViewLimitesAnuncio.setText("");
        ImgButtonGalFotosAnex = findViewById(R.id.imgButtonGalFotosAnexAnEdit);
        buscarAntigasImgAntesEdicao(); */
    }
    public void buscarAntigasImgAntesEdicao(){

    }
    public void cliqueAtualizarItensAnuncio(){
        SimOuNaoDialog.show(getSupportFragmentManager(),"Deseja confirmar a atualização desse anúncio?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                //
                //
             /*   if(verficarCampos()) {
                    //Ads anuncioParaAtualizar = retornandoAnuncioComNovosDadosParaAtualizar();
                    try {
                        showProgressDialogWithTitle("Por favor, espere", "atualizando dados do anúncio");
                        //atualizarAnuncio(anuncioParaAtualizar);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                exibirMsgSeValidouAtualizao();

                if (isValido){
                    msgToast("Anúncio atualizado com sucesso");
                    mudarTela(AnunciosFornecedorActivity.class);
                }else{
                    msgToast("Erro");
                }
              */
            }

        });

    }
    public Ads retornandoAnuncioComNovosDadosParaAtualizar(){
        Ads dadosAnuncioSelecionadoAntesEdicao = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();
        Ads anuncioASerEnviadoPUT =  new Ads();
        anuncioASerEnviadoPUT.set_id( dadosAnuncioSelecionadoAntesEdicao.get_id());
        anuncioASerEnviadoPUT.setTitle(editTextTituloAnuncio.getText().toString());
        anuncioASerEnviadoPUT.setPrice(Double.parseDouble(editTextValorAnuncio.getText().toString().trim()));
        anuncioASerEnviadoPUT.setDescription(editTextDescAnuncio.getText().toString());
        anuncioASerEnviadoPUT.setPhone(editTextTelefoneAnuncio.getText().toString());

        String textoTags = editTextTagsAnuncio.getText().toString();
        String[] itensDasTags = textoTags.split(",");
        List<String> arrayDasTags = new ArrayList<String>();
        Collections.addAll(arrayDasTags, itensDasTags);
        anuncioASerEnviadoPUT.setTags((ArrayList) arrayDasTags);

        Endereco endAnuncioEditado = new Endereco();
        endAnuncioEditado.setStreet(editTextRuaAnuncio.getText().toString());
        endAnuncioEditado.setNeighborhood(editTextBairroEndAnuncio.getText().toString());
        endAnuncioEditado.setCity(editTextCidadeEndAnuncio.getText().toString());
        endAnuncioEditado.setNumber(editTextNumeroEndAnuncio.getText().toString());
        endAnuncioEditado.setState("Pernambuco"); //MUDAR ISSO AQ EINNNNNNNNNN
        endAnuncioEditado.setZipcode(editTextCepEndAnuncio.getText().toString());
        anuncioASerEnviadoPUT.setAddress(endAnuncioEditado);


        //textViewLimitesAnuncio.setText("");
        ImgButtonGalFotosAnex = findViewById(R.id.imgButtonGalFotosAnexAnEdit);
        guardandoNovasImgsSelecionadasEditadas();

        return anuncioASerEnviadoPUT;
    }
    public void guardandoNovasImgsSelecionadasEditadas(){

    }

    public void cliqueExcluindoAnuncio() {
        SimOuNaoDialog.show(getSupportFragmentManager(), "Deseja mesmo excluir esse anúncio ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                //
                //

                showProgressDialogWithTitle("Por favor, espere", "excluindo anúncio");
                msgToast("Anúncio excluído com sucesso");
                msgToast("Erro");
                mudarTela(AnunciosFornecedorActivity.class);

            }
        });
    }
    private boolean verficarCampos(){
        String cidade = editTextCidadeEndAnuncio.getText().toString().trim();
        String bairro = editTextBairroEndAnuncio.getText().toString().trim();
        String cep = editTextCepEndAnuncio.getText().toString().trim();
        String rua = editTextRuaAnuncio.getText().toString().trim();
        String titulo = editTextTituloAnuncio.getText().toString().trim();
//        double valor = Double.parseDouble(edtValor.getText().toString().trim());
        String descricao = editTextDescAnuncio.getText().toString().trim();
        String telefone = editTextTelefoneAnuncio.getText().toString().trim();

        Boolean camposOk = true;
        if (!validacaoGuiRapida.isCampoAceitavel(titulo)){
            this.editTextTituloAnuncio.setError(("Digite o título do seu anúncio"));
            this.editTextTituloAnuncio.requestFocus();
            return false;
        }else if(!validacaoGuiRapida.isCampoAceitavel(descricao)){
            this.editTextDescAnuncio.setError("Descreva melhor o seu serviço");
            this.editTextDescAnuncio.requestFocus();
            return false;
        }else if(!validacaoGuiRapida.isTelefoneValido(telefone)){
            this.editTextTelefoneAnuncio.setError("Telefone Invalido ou sem ddd");
            this.editTextTelefoneAnuncio.requestFocus();
            return false;
        }else if(validacaoGuiRapida.isCampoVazio(cidade)){
            this.editTextCidadeEndAnuncio.setError("Favor insira a cidade");
            this.editTextCidadeEndAnuncio.requestFocus();
            return false;
        }else if(validacaoGuiRapida.isCampoVazio(bairro)){
            this.editTextBairroEndAnuncio.setError("Favor insira o Bairro");
            this.editTextBairroEndAnuncio.requestFocus();
            return false;
        }else if(validacaoGuiRapida.isCampoVazio(rua)){
            this.editTextRuaAnuncio.setError("Favor insira a Rua");
            return false;
        }else if(!validacaoGuiRapida.isCepValido(cep)){
            this.editTextCepEndAnuncio.setError("Favor insira um CEP Válido");
            this.editTextCepEndAnuncio.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    public void showProgressDialogWithTitle(String title, String msgEmbaixo) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msgEmbaixo);
        progressDialog.show();
    }
    public void msgToast(String msgToast){
        Toast.makeText(this, msgToast, Toast.LENGTH_SHORT).show();
        finish();
    }
    public void exibirMsgSeValidouAtualizao(){

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
