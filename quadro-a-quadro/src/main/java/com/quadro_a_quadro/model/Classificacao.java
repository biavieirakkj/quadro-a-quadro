package com.quadro_a_quadro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "classificacao")
public class Classificacao 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    //get e set
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
}
