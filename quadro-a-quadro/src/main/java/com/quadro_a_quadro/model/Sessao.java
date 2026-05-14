package com.quadro_a_quadro.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "sessao")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data é obrigatória")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "Horário é obrigatório")
    @Column(nullable = false)
    private LocalTime horario;

    @ManyToOne
    @JoinColumn(name = "id_filme", nullable = false)
    @NotNull(message = "Filme é obrigatório")
    private Filme filme;

    @ManyToOne
    @JoinColumn(name = "num_sala", nullable = false)
    @NotNull(message = "Sala é obrigatória")
    private Sala sala;

    // Getters e Setters
    public Long getId() 
    { 
        return id;
    }

    public void setId(Long id) 
    { 
        this.id = id; 
    }

    public LocalDate getData() 
    { 
        return data; 
    }

    public void setData(LocalDate data) 
    { 
        this.data = data; 
    }

    public LocalTime getHorario() 
    { 
        return horario; 
    }

    public void setHorario(LocalTime horario) 
    { 
        this.horario = horario; 
    }

    public Filme getFilme() 
    { 
        return filme; 
    }

    public void setFilme(Filme filme) 
    { 
        this.filme = filme; 
    }

    public Sala getSala() 
    { 
        return sala; 
    }

    public void setSala(Sala sala) 
    { 
        this.sala = sala; 
    }
}