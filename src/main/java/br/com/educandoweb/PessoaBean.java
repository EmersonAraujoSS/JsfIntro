package br.com.educandoweb;

import br.com.educandoweb.dao.GenericDao;
import br.com.educandoweb.entities.Pessoa;


import br.com.educandoweb.repository.IDaoPessoa;
import br.com.educandoweb.repository.IDaoPessoaImpl;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;



//@SessionScoped // essa anotação mantem os dados na tela quando eu estou na mesma sesao
@ViewScoped // essa anotação mantem os dados na tela enquanto o usuário estiver logado na tela
//@ApplicationScoped //ele vai manter os dados para todos os usuários (compartilha os dados com outros usuarios) (mesmo abrindo a url em outro navegador)
@ManagedBean // meu ManagedBean conecta o meu Bean a minha pagina xhtml
@Named(value = "pessoaBean")

public class PessoaBean {

    //ATRIBUTOS
    private Pessoa pessoa = new Pessoa();
    private GenericDao<Pessoa> genericDao = new GenericDao<Pessoa>();
    private List<Pessoa> pessoasList = new ArrayList<>();
    private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();


    //MÉTODOS
    public String salvar() {
        pessoa = genericDao.merge(pessoa);
        carregarPessoas();
        mostrarMsg("Cadastrado com sucesso!");

        return "";
    }


    private void mostrarMsg(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(msg);
        context.addMessage(null, message);
    }


    public String novo(){
        //executa algum processo antes de novo
        pessoa = new Pessoa();
        return "";
    }


    public String limpar(){
        //executa algum processo antes de limpar
        pessoa = new Pessoa();
        return "";
    }


    public String remove(){
        genericDao.deletePorId(pessoa);
        pessoa = new Pessoa();
        carregarPessoas();
        mostrarMsg("Removido com sucesso!");

        return "";
    }

    @PostConstruct  //essa anotação indica que sempre que for carregado em memória, ele irá carregar a minha lista
    public void carregarPessoas(){
        pessoasList = genericDao.getListEntity(Pessoa.class);
    }



    public String deslogar(){

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.getSessionMap().remove("usuarioLogado");

        HttpServletRequest httpServletRequest = (HttpServletRequest)
                context.getCurrentInstance().getExternalContext().getRequest();

        httpServletRequest.getSession().invalidate();

        return "index.xhtml";
    }



    public String logar(){

        Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

        if(pessoaUser != null){

            //adicionar o usuário na sessão usuarioLogado
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.getSessionMap().put("usuarioLogado", pessoaUser);

            return "firstpage.xhtml";
        }

        return "index.xhtml";
    }


    public boolean permiteAcesso(String acesso){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        return pessoaUser.getPerFilUser().equals(acesso);
    }


    public void pesquisaCep(AjaxBehaviorEvent event){

        try {
            URL url = new URL("https://viacep.com.br/ws/"+ pessoa.getCep()+"/json/"); //consumindo WEB SERVICE DE CEP
            URLConnection connection = url.openConnection();  //URLConnection = iniciando minha conexão de cep
            InputStream inputStream = connection.getInputStream(); //InputStream = executa as configurações no servidor e retorna a informação
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8")); //BufferedReader = vai ser usado para fazer leitura de fluxo de dados

            String cep = "";
            StringBuilder jsonCep = new StringBuilder(); //StringBuilder = facilita a criação e manipulação de strings de maneira eficiente.

            while ((cep = br.readLine()) != null){
                jsonCep.append(cep); //append = adicionar um novo elemento ao final da lista.
            }

            Pessoa gsonAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
            pessoa.setCep(gsonAux.getCep());
            pessoa.setLogradouro(gsonAux.getLogradouro());
            pessoa.setComplemento(gsonAux.getComplemento());
            pessoa.setUf(gsonAux.getUf());
            pessoa.setBairro(gsonAux.getBairro());
            pessoa.setLocalidade(gsonAux.getLocalidade());
            pessoa.setUf(gsonAux.getUf());
            pessoa.setUnidade(gsonAux.getUnidade());
            pessoa.setIbge(gsonAux.getIbge());
            pessoa.setGia(gsonAux.getGia());


        }catch (Exception e){
            e.printStackTrace();
            mostrarMsg("Erro ao consultar o CEP");
        }
    }



    //MÉTODOS ESPECIAIS
    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public GenericDao<Pessoa> getGenericDao() {
        return genericDao;
    }
    public void setGenericDao(GenericDao<Pessoa> genericDao) {
        this.genericDao = genericDao;
    }
    public List<Pessoa> getPessoasList() {
        return pessoasList;
    }
    public void setPessoasList(List<Pessoa> pessoasList) {
        this.pessoasList = pessoasList;
    }
}
