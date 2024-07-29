package br.com.educandoweb;

import br.com.educandoweb.dao.GenericDao;
import br.com.educandoweb.entities.Cidades;
import br.com.educandoweb.entities.Estados;
import br.com.educandoweb.entities.Pessoa;
import br.com.educandoweb.jpautil.JPAutil;
import br.com.educandoweb.repository.IDaoPessoa;
import br.com.educandoweb.repository.IDaoPessoaImpl;
import com.google.gson.Gson;
import net.bootsfaces.component.selectOneMenu.SelectOneMenu;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//@SessionScoped // essa anotação mantem os dados na tela quando eu estou na mesma sesao
//@ApplicationScoped //ele vai manter os dados para todos os usuários (compartilha os dados com outros usuarios) (mesmo abrindo a url em outro navegador)
//@ManagedBean // meu ManagedBean conecta o meu Bean a minha pagina xhtml
@ViewScoped // essa anotação mantem os dados na tela enquanto o usuário estiver logado na tela
@Named(value = "pessoaBean")
public class PessoaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    //ATRIBUTOS
    private Pessoa pessoa = new Pessoa();

    @Inject
    private GenericDao<Pessoa> genericDao;

    @Inject
    private IDaoPessoa iDaoPessoa;

    @Inject
    EntityManager entityManager;

    @Inject
    private JPAutil jpAutil;

    private List<Pessoa> pessoasList = new ArrayList<>();
    private List<SelectItem> estados;
    private List<SelectItem> cidades;
    private Part arquivoFoto; //Part = é uma classe auxiliar do java para fazer upload de arquivo



    //MÉTODOS
    public String salvar() throws IOException {

        if(arquivoFoto != null && arquivoFoto.getInputStream() != null) {
            //PROCESSAR IMAGEM
            byte[] imagemByte = getByte(arquivoFoto.getInputStream());

            //TRANSFORMAR EM BUFFERIMAGE
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));

            if (bufferedImage != null) {
                pessoa.setFotoIconBase64Original(imagemByte); //Salva imagem original

                //IDENTIFICA O TIPO DA IMAGEM
                int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
                int width = 200;
                int height = 200;

                //CRIAR A MINIATURA
                BufferedImage resizedImage = new BufferedImage(width, height, type);
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(bufferedImage, 0, 0, width, height, null);
                g.dispose();

                //ESCREVER NOVAMENTE A IMAGEM EM TAMANHO MENOR
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                String extensao = arquivoFoto.getContentType().split("\\/")[1]; //quebra o arquivo e pega só de qual extensao ele é. EX: /png
                ImageIO.write(resizedImage, extensao, baos);

                String miniImagem = "data:" + arquivoFoto.getContentType() + ";base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());

                //PROCESSAR IMAGEM
                pessoa.setFotoIconBase64(miniImagem);
                pessoa.setExtensao(extensao);
            }
        }

        pessoa = genericDao.merge(pessoa);
        carregarPessoas();
        mostrarMsg("Cadastrado com sucesso!");
        return "";
    }


    public void registraLog(){
        System.out.println("metodo registra log");
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
        pessoasList = genericDao.getListEntityLimit10(Pessoa.class);
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
        }else {
            FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Usuário não encontrado"));
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


    public void carregarCidades(AjaxBehaviorEvent event){

            Estados estado = (Estados) ((SelectOneMenu)event.getSource()).getValue();

                if (estado != null){
                pessoa.setEstados(estado);

                List<Cidades> cidades = jpAutil.getEntityManager().createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();

                List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();

                for (Cidades cidade : cidades){
                    selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
                }

                setCidades(selectItemsCidade);
            }
        }


    public String editar(){
        if (pessoa != null){
            Estados estado = pessoa.getCidades().getEstados();
            pessoa.setEstados(estado);

            List<Cidades> cidades = jpAutil.getEntityManager().createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();

            List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();

            for (Cidades cidade : cidades){
                    selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
            }

            setCidades(selectItemsCidade);
        }

        return "";
    }


    //MÉTODO QUE CONVERTE INPUTSTREAM PARA ARRAY DE BYTES
    private byte[] getByte(InputStream is) throws IOException {

        int length;
        int size = 1024;
        byte[] buffer = null;
        if (is instanceof ByteArrayInputStream){
            size = is.available();
            buffer = new byte[size];
            length = is.read(buffer, 0, size);
        }else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buffer = new byte[size];

            while ((length = is.read(buffer, 0, size)) != -1){
                bos.write(buffer, 0, length);
            }
            buffer = bos.toByteArray();
        }
        return buffer;
    }



    public void download() throws IOException {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String fileDownloadId = params.get("fileDownloadId");

        Pessoa pessoa = genericDao.consultar(Pessoa.class, fileDownloadId);

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        response.addHeader("Content-Disposition", "attachment; filename=download" + pessoa.getExtensao());
        response.setContentType("application/octet-stream");
        response.setContentLength(pessoa.getFotoIconBase64Original().length);
        response.getOutputStream().write(pessoa.getFotoIconBase64Original());
        response.getOutputStream().flush();
        FacesContext.getCurrentInstance().responseComplete();
    }


    public void mudancaDeValor(ValueChangeEvent event){ //para eu ter os dados das mudanças de valor eu uso o ValueChangeEvent
        System.out.println("Valor antigo: " + event.getOldValue());
        System.out.println("Novo valor: " + event.getNewValue());
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
    public IDaoPessoa getiDaoPessoa() {
        return iDaoPessoa;
    }
    public void setiDaoPessoa(IDaoPessoa iDaoPessoa) {
        this.iDaoPessoa = iDaoPessoa;
    }
    public List<SelectItem> getEstados() {
        estados = iDaoPessoa.listaEstados();
        return estados;
    }
    public void setEstados(List<SelectItem> estados) {
        this.estados = estados;
    }
    public List<SelectItem> getCidades() {
        return cidades;
    }
    public void setCidades(List<SelectItem> cidades) {
        this.cidades = cidades;
    }
    public Part getArquivoFoto() {
        return arquivoFoto;
    }
    public void setArquivoFoto(Part arquivoFoto) {
        this.arquivoFoto = arquivoFoto;
    }

}
