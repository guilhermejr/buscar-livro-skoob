package net.guilhermejr.livro;

import net.guilhermejr.livro.exceptions.ISBNInexistente;
import net.guilhermejr.livro.exceptions.ISBNInvalido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BuscaTest {

    private List<String> autores = new ArrayList<>();
    private List<String> generos = new ArrayList<>();
    private Livro livro = new Livro();
    private Busca busca = new Busca();

    @BeforeEach
    public void inicializa() {

        this.autores.add("Nikolai Gógol");

        this.generos.add("Contos");
        this.generos.add("Fantasia");
        this.generos.add("Ficção");
        this.generos.add("Humor, Comédia");
        this.generos.add("Literatura Estrangeira");

        this.livro.setCapa("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");
        this.livro.setTitulo("O nariz");
        this.livro.setSubtitulo("Ilustrações de Nicholas Steinmetz");
        this.livro.setIsbn("9786586490268");
        this.livro.setDescricao("E se, um belo dia ao acordar, você não encontrasse mais o seu nariz? Respire fundo antes de entrar nesta história, Gógol está prestes a deixá-lo sem ar. Ilustrado por Nicholas Steinmetz, com apresentação de Tamy Ghannam, este conto é explorado de forma mais profunda nas videoaulas de Raquel Toledo.\\n\\nEm uma manhã como qualquer outra, um barbeiro, conhecido pela quantidade de sangue que faz jorrar do rosto de sua clientela, toma seu café da manhã. Ao afundar a faca sobre um pão recém-assado, encontra um ingrediente que não estava na receita: um nariz.\\n\\nDo outro lado da cidade, seu cliente, o assessor colegial Kovaliov, acorda e dá de cara com uma panqueca. Não em seu prato, infelizmente -- trata-se de seu próprio rosto no espelho, liso como uma massa corrida, carente de qualquer resquício de um nariz.\\n\\nPublicado em 1836, este conto reúne o que há de mais marcante na escrita de Nikolai Gógol: a comédia, a cultura popular, a sátira política e a crítica à burocracia. A insólita história de um duplo, permeada pelo fantástico, absurdo e pelo grotesco, tem como cenário a fria e burocrática cidade de São Petersburgo.\\n\\nA nova edição da Antofágica ganhou notas e nova tradução direta do russo por Lucas Simone, doutor em Literatura e Cultura Russa (USP), e ilustrações de Nicholas Steinmetz. Os posfácios são assinados por Raquel Toledo, mestre em Literatura e Cultura Russa (USP), Inti Queiroz, doutoranda em Filologia e Língua Portuguesa (USP).\\n\\n");
        this.livro.setGeneros(this.generos);
        this.livro.setAutores(this.autores);
        this.livro.setEditora("Antofágica");
        this.livro.setIdioma("português");
        this.livro.setAno("2021");
        this.livro.setPaginas("160");

    }

    @Test
    public void deveRetornarUmLivroQuandoPassarISBNValido() {

        Livro livroBusca = this.busca.livro("9786586490268");
        Assertions.assertEquals(this.livro.getCapa(), livroBusca.getCapa());
        Assertions.assertEquals(this.livro.getTitulo(), livroBusca.getTitulo());
        Assertions.assertEquals(this.livro.getSubtitulo(), livroBusca.getSubtitulo());
        Assertions.assertEquals(this.livro.getIsbn(), livroBusca.getIsbn());
        Assertions.assertEquals(this.livro.getDescricao(), livroBusca.getDescricao());
        Assertions.assertEquals(this.livro.getGeneros().get(0), livroBusca.getGeneros().get(0));
        Assertions.assertEquals(this.livro.getGeneros().get(1), livroBusca.getGeneros().get(1));
        Assertions.assertEquals(this.livro.getGeneros().get(2), livroBusca.getGeneros().get(2));
        Assertions.assertEquals(this.livro.getGeneros().get(3), livroBusca.getGeneros().get(3));
        Assertions.assertEquals(this.livro.getGeneros().get(4), livroBusca.getGeneros().get(4));
        Assertions.assertEquals(this.livro.getAutores().get(0), livroBusca.getAutores().get(0));
        Assertions.assertEquals(this.livro.getEditora(), livroBusca.getEditora());
        Assertions.assertEquals(this.livro.getIdioma(), livroBusca.getIdioma());
        Assertions.assertEquals(this.livro.getAno(), livroBusca.getAno());
        Assertions.assertEquals(this.livro.getPaginas(), livroBusca.getPaginas());

    }

    @Test
    public void deveRetornarISBNInvalidoExceptionQuandoPassarISBNInvalido() {

        Assertions.assertThrows(ISBNInvalido.class, () -> this.busca.livro("XXXXXXXXXXXXX"));

    }

    @Test
    public void deveRetornarISBNInexistenteExceptionQuandoPassarISBNInexistente() {

        Assertions.assertThrows(ISBNInexistente.class, () -> this.busca.livro("0123456789012"));

    }

}
