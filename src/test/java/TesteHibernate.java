import br.com.educandoweb.HibernateUtil;
import br.com.educandoweb.dao.GenericDao;
import br.com.educandoweb.model.UsuarioPessoaIntro;
import org.junit.Test;

public class TesteHibernate {

    @Test
    public void testeHibernateUtil(){

        GenericDao<UsuarioPessoaIntro> genericDao = new GenericDao<UsuarioPessoaIntro>();

        UsuarioPessoaIntro pessoaIntro = new UsuarioPessoaIntro();
        pessoaIntro.setNome("Araujo");
        pessoaIntro.setSobrenome("teste");
        pessoaIntro.setIdade(22);
        pessoaIntro.setEmail("teste@email.com");
        pessoaIntro.setLogin("teste");
        pessoaIntro.setSenha("1234");

        genericDao.salvar(pessoaIntro);

    }


    @Test
    public void testeBuscar(){

        GenericDao<UsuarioPessoaIntro> genericDao = new GenericDao<UsuarioPessoaIntro>();

        UsuarioPessoaIntro pessoaIntro = genericDao.pesquisar(1L, UsuarioPessoaIntro.class);
        pessoaIntro.setId(129L);

        System.out.println(pessoaIntro);

    }


    @Test
    public void testeUpdate(){
        GenericDao<UsuarioPessoaIntro> genericDao = new GenericDao<UsuarioPessoaIntro>();

        UsuarioPessoaIntro pessoaIntro = genericDao.pesquisar(129L, UsuarioPessoaIntro.class);

        pessoaIntro.setIdade(99);
        pessoaIntro.setNome("Nome atualizado Hibernate");

        genericDao.updateMerge(pessoaIntro);

        System.out.println(pessoaIntro);

    }


    @Test
    public void testeDelete(){

        GenericDao<UsuarioPessoaIntro> genericDao = new GenericDao<>();

        UsuarioPessoaIntro pessoaIntro = genericDao.pesquisar(131L, UsuarioPessoaIntro.class);

        genericDao.deletePorId(pessoaIntro);


    }
}
