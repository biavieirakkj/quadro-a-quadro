package com.quadro_a_quadro.model;

import com.quadro_a_quadro.model.enums.StatusSala;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "sala")
public class Sala
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numSala;

    @NotNull(message = "Capacidade é obrigatória")
    @Min(value = 20, message = "Capacidade mínima é 20 assentos")
    @Max(value = 200, message = "Capacidade máxima é 200 assentos")
    @Column(nullable = false)
    private Integer capacidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSala status = StatusSala.ATIVO;

    // Getters e Setters
    public Long getNumSala() 
    { 
        return numSala; 
    }

    public void setNumSala(Long numSala) 
    { 
        this.numSala = numSala; 
    }

    public Integer getCapacidade() 
    { 
        return capacidade; 
    }

    public void setCapacidade(Integer capacidade) 
    { 
        this.capacidade = capacidade; 
    }

    public StatusSala getStatus() 
    { 
        return status; 
    }

    public void setStatus(StatusSala status) 
    { 
        this.status = status; 
    }

}