package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.Mask;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.user.dominio.Address;
import com.inovaufrpe.makeparty.user.servico.ValidacaoGuiRapida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditarAnuncioActivity extends AppCompatActivity {

    private Spinner spinnerTipoAnuncio;
    private EditText editTextTituloAnuncio,editTextValorAnuncio,editTextDescAnuncio,editTextTagsAnuncio,editTextTelefoneAnuncio;
    private EditText editTextRuaAnuncio,editTextNumeroEndAnuncio,editTextBairroEndAnuncio,editTextCidadeEndAnuncio,editTextCepEndAnuncio;
    private TextView textViewLimitesAnuncio;
    private Button buttonAtualizarAnuncio,buttonExcluirAnuncio;
    private ImageButton imgButtonAnexarMaisFtAnEdit;
    ValidacaoGuiRapida validacaoGuiRapida = new ValidacaoGuiRapida();
    private String validar = "";
    boolean isValido = false;

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
        imgButtonAnexarMaisFtAnEdit =findViewById(R.id.imgButtonAnexarMaisFtAnEdit);
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
        imgButtonAnexarMaisFtAnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarTela(TesteCapturaGaleriaActivity.class);
            }
        });

    }

    public void setandoInfoItensViewAntesEdicao(){
        Ad anuncioSelecionado = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();

        //spinnerTipoAnuncio.setOnItemSelectedListener(anuncioSelecionado.getType());
        //spinnerTipoAnuncio.setSelection(adapter.getPosition("SP"))
        editTextTituloAnuncio.setText(anuncioSelecionado.getTitle());
        editTextValorAnuncio.setText((String.valueOf(anuncioSelecionado.getPrice())));
        editTextDescAnuncio.setText(anuncioSelecionado.getDescription());
        //editTextTagsAnuncio.setText((anuncioSelecionado.getTags().toString()));
        editTextTelefoneAnuncio.setText((anuncioSelecionado.getPhone()));
        editTextTelefoneAnuncio.addTextChangedListener(Mask.insert("(##)#####-####",editTextTelefoneAnuncio));
        editTextRuaAnuncio.setText(anuncioSelecionado.getAddress().getStreet());
        editTextNumeroEndAnuncio.setText(anuncioSelecionado.getAddress().getNumber());
        editTextBairroEndAnuncio.setText(anuncioSelecionado.getAddress().getNeighborhood());
        editTextCidadeEndAnuncio .setText(anuncioSelecionado.getAddress().getCity());
        editTextCepEndAnuncio.setText(anuncioSelecionado.getAddress().getZipcode());
        editTextCepEndAnuncio.addTextChangedListener(Mask.insert("#####-###",editTextCepEndAnuncio));
        textViewLimitesAnuncio.setText("");
        //buscarAntigasImgAntesEdicao();
    }
    public void buscarAntigasImgAntesEdicao(){

    }
    public void cliqueAtualizarItensAnuncio(){
        SimOuNaoDialog.show(getSupportFragmentManager(),"Deseja confirmar a atualização desse anúncio?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                if(verficarCampos()) {
                    Ad anuncioParaAtualizar = retornandoAnuncioComNovosDadosParaAtualizar();
                    Gson gson= new Gson();
                    String ad = gson.toJson(anuncioParaAtualizar);
                    ad=ad.substring(0,ad.length()-1)+","+"\"token\""+":"+ "\""+SessaoApplication.getInstance().getTokenUser()+"\""+ "}";
                    Log.i("Script", "OLHAAA: "+ ad);
                    ///EEEEEEEEEEEEEEEEEERRRADO AI EMBAIXXXXXXXXXXXO, CONFUNDI COM DELETE
                    showProgressDialogWithTitle("Por favor, espere", "atualizando dados do anúncio");
                    try{
                        editarAnuncioOficial(ad);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                exibirMsgSeValidouAtualizaoOuExclusao();

                if (isValido){
                    msgToast("Anúncio atualizado com sucesso");
                    mudarTela(AnunciosFornecedorActivity.class);
                }else{
                    msgToast("Erro");
                };
            }

        });

    }
    public Ad retornandoAnuncioComNovosDadosParaAtualizar(){
        Ad dadosAnuncioSelecionadoAntesEdicao = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();
        Ad anuncioASerEnviadoPUT =  new Ad();
        anuncioASerEnviadoPUT.set_id( dadosAnuncioSelecionadoAntesEdicao.get_id());
        String tipo = spinnerTipoAnuncio.getSelectedItem().toString().trim();
        tipo=ValidacaoGuiRapida.deAccent(tipo);
        anuncioASerEnviadoPUT.setType(tipo);
        anuncioASerEnviadoPUT.setTitle(editTextTituloAnuncio.getText().toString());
        anuncioASerEnviadoPUT.setPrice(Double.parseDouble(editTextValorAnuncio.getText().toString().trim()));
        anuncioASerEnviadoPUT.setDescription(editTextDescAnuncio.getText().toString());
        anuncioASerEnviadoPUT.setPhone(editTextTelefoneAnuncio.getText().toString());

        String textoTags = editTextTagsAnuncio.getText().toString();
        String[] itensDasTags = textoTags.split(",");
        List<String> arrayDasTags = new ArrayList<String>();
        Collections.addAll(arrayDasTags, itensDasTags);
        anuncioASerEnviadoPUT.setTags((ArrayList) arrayDasTags);

        Address endAnuncioEditado = new Address();
        endAnuncioEditado.setStreet(editTextRuaAnuncio.getText().toString());
        endAnuncioEditado.setNeighborhood(editTextBairroEndAnuncio.getText().toString());
        endAnuncioEditado.setCity(editTextCidadeEndAnuncio.getText().toString());
        endAnuncioEditado.setNumber(editTextNumeroEndAnuncio.getText().toString());
        endAnuncioEditado.setState("Pernambuco"); //MUDAR ISSO AQ EINNNNNNNNNN
        endAnuncioEditado.setZipcode(editTextCepEndAnuncio.getText().toString());
        anuncioASerEnviadoPUT.setAddress(endAnuncioEditado);


        //textViewLimitesAnuncio.setText("");
        guardandoNovasImgsSelecionadasEditadas();

        return anuncioASerEnviadoPUT;
    }
    public void guardandoNovasImgsSelecionadasEditadas(){

    }

    public void cliqueExcluindoAnuncio() {
        final Ad anuncioSelecionado = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();
        SimOuNaoDialog.show(getSupportFragmentManager(), "Deseja mesmo excluir esse anúncio ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                String tokenJsonAmao = ","+"\"token\":"+ "\""+ SessaoApplication.getInstance().getTokenUser() +"\""+"}";
                String jsonAnuncioParaDeletarComToken = "{"+"\"_id\":"+ "\""+ anuncioSelecionado.get_id()+"\""+tokenJsonAmao;
                showProgressDialogWithTitle("Por favor, espere", "excluindo anúncio");
                try{
                    excluirAnuncioOficial(jsonAnuncioParaDeletarComToken);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                exibirMsgSeValidouAtualizaoOuExclusao();

                if (isValido){
                        msgToast("Anúncio excluído com sucesso");
                        mudarTela(AnunciosFornecedorActivity.class);
                }else{
                    msgToast("Erro");
                };
                }
            });
        }
    private boolean verficarCampos(){
        String cidade = editTextCidadeEndAnuncio.getText().toString().trim();
        String bairro = editTextBairroEndAnuncio.getText().toString().trim();
        String cep = editTextCepEndAnuncio.getText().toString().trim();
        String rua = editTextRuaAnuncio.getText().toString().trim();
        String numero = editTextNumeroEndAnuncio.getText().toString().trim();
        String titulo = editTextTituloAnuncio.getText().toString().trim();
        String valor = editTextValorAnuncio.getText().toString().trim();
        //double valor = Double.parseDouble(editTextValorAnuncio.getText().toString().trim());
        String descricao = editTextDescAnuncio.getText().toString().trim();
        String telefone = editTextTelefoneAnuncio.getText().toString().trim();

        Boolean camposOk = true;
        if (!validacaoGuiRapida.isCampoAceitavel(titulo)){
            this.editTextTituloAnuncio.setError(("Digite o título do seu anúncio"));
            this.editTextTituloAnuncio.requestFocus();
            return false;
        }else if(!validacaoGuiRapida.isCampoAceitavel(descricao)) {
            this.editTextDescAnuncio.setError("Descreva melhor o seu serviço");
            this.editTextDescAnuncio.requestFocus();
            return false;
        }else if(!validacaoGuiRapida.isDouble(valor)){
            this.editTextValorAnuncio.setError("Digite o valor do seu serviço");
            this.editTextValorAnuncio.requestFocus();
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
    private void editarAnuncioOficial(String json) throws InterruptedException{
        callServer("PUT",json);
    }
    private void excluirAnuncioOficial(String json) throws InterruptedException{
        callServer("DELETE",json);
    }

    private void callServer(final String method, final String data) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (method.equals("PUT")){
                    validar = ConectarServidor.putJadiel("https://makepartyserver.herokuapp.com/ads", data);
                    Log.i("Script", "OLHAAA: "+ validar);
                    if (validar.substring(2, 5).equals("err")){
                        // Não sei qual o erro
                        validar = "Não foi possível editar o anúncio";
                        // Rever a mensagem
                    }else{
                        validar = "Anúncio editado com sucesso";
                        isValido = true;
                    }
                }else{
                    validar = ConectarServidor.deleteDeJadiel("https://makepartyserver.herokuapp.com/ads", data);
                    Log.i("Script", "OLHAAA: "+ validar);
                    if (validar.substring(2, 5).equals("err")){
                        // Não sei qual o erro
                        validar = "Não foi possível excluir o anúncio";
                        // Rever a mensagem
                    }else{
                        validar = "Anúncio excluido com sucesso";
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
        this.mudarTela(AnunciosFornecedorActivity.class);

    }

}
