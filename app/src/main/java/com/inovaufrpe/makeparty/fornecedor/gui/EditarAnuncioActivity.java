package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.inovaufrpe.makeparty.fornecedor.dominio.Owner;
import com.inovaufrpe.makeparty.fornecedor.dominio.Plano;
import com.inovaufrpe.makeparty.fornecedor.gui.adapter.FotosAnuncioAdapter;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.Mask;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.user.dominio.Address;
import com.inovaufrpe.makeparty.user.servico.ValidacaoGuiRapida;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private String validarPlano = "";
    boolean isValido = false;
    //imagens
    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST_CODE = 228;
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Bitmap bitmap;
    private Owner owner;
    private int limiteFotos, limiteAds;

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
        try{
            owner = SessaoApplication.getInstance().getObjOwnerSeEleForTipoLogado();
            if (owner.getPlan() != null) {
                calcularLimites();
                textViewLimitesAnuncio.setText("Você ainda pode postar " + (limiteFotos - owner.getPlan().getTotalphoto()) + " fotos e " + (limiteAds - owner.getPlan().getTotalad()) + " anúncios");
            }
        }catch (Error e){
            e.printStackTrace();
        }
        imgButtonAnexarMaisFtAnEdit =findViewById(R.id.imgButtonAnexarMaisFtAnEdit);
        buttonAtualizarAnuncio = findViewById(R.id.button_atualizar_anuncio);
        buttonExcluirAnuncio = findViewById(R.id.button_excluir_anuncio);
        // imagens
        recyclerView = findViewById(R.id.view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FotosAnuncioAdapter(bitmaps);
        recyclerView.setAdapter(adapter);
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
                onGaleriaClicked(v);
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
    public void onGaleriaClicked(View v) {
        Intent selecionarFoto = new Intent(Intent.ACTION_PICK);
        File diretorioImagem = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String diretorioImagemPath = diretorioImagem.getPath();
        Uri data = Uri.parse(diretorioImagemPath);
        selecionarFoto.setDataAndType(data, "image/*");
        startActivityForResult(selecionarFoto, IMAGE_GALLERY_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Toast.makeText(this, "Imagem salva", Toast.LENGTH_LONG).show();
            }
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(bitmap);
                    adapter.notifyDataSetChanged();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Não foi possível abrir a imagem", Toast.LENGTH_LONG).show();
                }

            }
        }
    }
    public void cliqueAtualizarItensAnuncio(){
        SimOuNaoDialog.show(getSupportFragmentManager(),"Deseja confirmar a atualização desse anúncio?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                boolean estourouLimite = false;
                try {
                    if (limiteFotos - owner.getPlan().getTotalphoto() < 0) {
                        Toast.makeText(EditarAnuncioActivity.this, "Você não tem limite suficiente para postar este anúncio", Toast.LENGTH_SHORT).show();
                        estourouLimite = true;
                    }
                }catch (Error e){
                    e.printStackTrace();
                }
                if (!estourouLimite) {
                    if (verficarCampos()) {
                        Ad anuncioParaAtualizar = retornandoAnuncioComNovosDadosParaAtualizar();
                        Gson gson = new Gson();
                        String ad = gson.toJson(anuncioParaAtualizar);
                        ad = ad.substring(0, ad.length() - 1) + "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
                        Log.i("Script", "OLHAAA: " + ad);
                        ///EEEEEEEEEEEEEEEEEERRRADO AI EMBAIXXXXXXXXXXXO, CONFUNDI COM DELETE
                        showProgressDialogWithTitle("Por favor, espere", "atualizando dados do anúncio");
                        try {
                            editarAnuncioOficial(ad);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    exibirMsgSeValidouAtualizaoOuExclusao();

                    if (isValido) {
                        msgToast("Anúncio atualizado com sucesso");
                        mudarTela(AnunciosFornecedorActivity.class);
                    } else {
                        msgToast("Erro");
                    }
                    ;
                }
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
                    atualizarOwner();
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

    private void calcularLimites(){
        if (owner.getPlan().getType().equals("Plano Gratuito")){
            limiteFotos = 1;
            limiteAds = 1;
        }else if (owner.getPlan().getType().equals("Plano Bronze Mensal") || owner.getPlan().getType().equals("Plano Bronze Anual")){
            limiteFotos = 50;
            limiteAds = 10;
        }else if (owner.getPlan().getType().equals("Plano Prata Mensal") || owner.getPlan().getType().equals("Plano Prata Anual")){
            limiteFotos = 100;
            limiteAds = 20;
        }else if (owner.getPlan().getType().equals("Plano Ouro Mensal") || owner.getPlan().getType().equals("Plano Ouro Anual")){
            limiteAds = 40;
            limiteFotos = 200;
        }
    }

    public void atualizarPlano(String json) throws InterruptedException {
        callServerPlan("PUT", json);
    }

    private void callServerPlan(final String method, final String data) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (method.equals("PUT")) {
                    validarPlano = ConectarServidor.putJadiel("https://makepartyserver.herokuapp.com/advertisers", data);
                    Log.i("Script", "OLHAAA: " + validarPlano);
                    if (validarPlano.substring(2, 5).equals("err")) {
                        validarPlano = "Não foi possível editar o perfil";
                    } else {
                        validarPlano = "Perfil editado com sucesso";
                    }
                }
            }
        });
        thread.start();
        thread.join();
    }

    private void atualizarOwner(){
        Plano plano = owner.getPlan();
        plano.setTotalad(plano.getTotalad() - 1);
//        plano.setTotalphoto(plano.getTotalphoto() - bitmaps.size());
        Gson gson = new Gson();
        String newOwner = gson.toJson(owner);
        newOwner = newOwner.substring(0, newOwner.length() - 1) + "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
        try {
            atualizarPlano(newOwner);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
