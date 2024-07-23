package br.com.educandoweb.filter;

import br.com.educandoweb.entities.Pessoa;
import br.com.educandoweb.jpautil.JPAutil;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}) // urlPatterns = vai mapear todas as urls que tem esse indicador
public class FilterAutenticacao implements Filter {

    @Inject
    private JPAutil jpAutil;



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jpAutil.getEntityManager();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
        String url = req.getServletPath();

        if (!url.equalsIgnoreCase("index.xhtml") && usuarioLogado == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.xhtml");
            dispatcher.forward(request, response);
        }else {
            //executa as açoes do request e do response
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
