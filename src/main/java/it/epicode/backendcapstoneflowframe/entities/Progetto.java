package it.epicode.backendcapstoneflowframe.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import it.epicode.backendcapstoneflowframe.entities.Utente;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "progetti")
@Getter
@Setter
@NoArgsConstructor
public class Progetto {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String titolo;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente creatore;

    @Column(nullable = false)
    private LocalDateTime dataCreazione;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String blueprint;

    @PrePersist
    protected void onCreate() {
        this.dataCreazione = LocalDateTime.now();
        if (this.blueprint == null) {
            this.blueprint = "[]";
        }
    }
}