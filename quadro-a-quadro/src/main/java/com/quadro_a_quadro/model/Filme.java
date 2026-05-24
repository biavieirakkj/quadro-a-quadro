package com.quadro_a_quadro.model;

import com.quadro_a_quadro.model.enums.GeneroFilme;
import com.quadro_a_quadro.model.enums.StatusFilme;
import com.quadro_a_quadro.model.enums.ClassificacaoFilme;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String capa;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200, message = "Título deve ter no máximo 200 caracteres")
    @Column(nullable = false, unique = true, length = 200)
    private String titulo;

    @Size(max = 500, message = "Sinopse deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String sinopse;

    @NotBlank(message = "Duração é obrigatória")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
             message = "Duração deve estar no formato HH:MM:SS")
    @Column(nullable = false)
    private String duracao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFilme status = StatusFilme.EM_BREVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassificacaoFilme classificacao;

    @ElementCollection
    @CollectionTable(name = "filme_genero", 
    joinColumns = @JoinColumn(name = "id_filme"))
    @Column(name = "genero")
    @Enumerated(EnumType.STRING)
    private List<GeneroFilme> generos;

    @ManyToMany
    @JoinTable(
        name = "exibicao_sala",
        joinColumns = @JoinColumn(name = "id_filme"),
        inverseJoinColumns = @JoinColumn(name = "numero_sala")
    )
    private List<Sala> salas;

    // Getters e Setters
    public Long getId() 
    { 
        return id; 
    }

    public void setId(Long id) 
    { 
        this.id = id; 
    }

    public String getTitulo() 
    { 
        return titulo; 
    }

    public void setTitulo(String titulo) 
    { 
        this.titulo = titulo; 
    }

    public String getSinopse() 
    { 
        return sinopse;
    }

    public void setSinopse(String sinopse) 
    { 
        this.sinopse = sinopse; 
    }

    public String getDuracao() 
    { 
        return duracao; 
    }

    public void setDuracao(String duracao) 
    { 
        this.duracao = duracao; 
    }

    public List<GeneroFilme> getGeneros() 
    { 
        return generos; 
    }

    public void setGeneros(List<GeneroFilme> generos) 
    { 
        this.generos = generos; 
    }

    public StatusFilme getStatus() 
    { 
        return status; 
    }

    public void setStatus(StatusFilme status) 
    { 
        this.status = status; 
    }

    public ClassificacaoFilme getClassificacao() 
    { 
        return classificacao; 
    }

    public void setClassificacao(ClassificacaoFilme classificacao) 
    { 
        this.classificacao = classificacao; 
    }

    public String getCapa() 
    { 
        return capa;   
    }

    public void setCapa(String capa) 
    { 
        this.capa = capa; 
    }

    public List<Sala> getSalas() 
    { 
        return salas; 
    }

    public void setSalas(List<Sala> salas) 
    { 
        this.salas = salas; 
    }
}