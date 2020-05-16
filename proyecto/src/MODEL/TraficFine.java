package MODEL;

import java.time.LocalDateTime;

public class TraficFine {
    private Long id ;
    private String description ;
    private LocalDateTime date;
    private Double amount;
    private Long idPolice ;
    private String nifInfractor ;
    private Long idtypeOfFine;

    public TraficFine(Long id, String description, LocalDateTime date, Double amount, Long idPolice, String nifInfractor, Long idtypeOfFine) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.idPolice = idPolice;
        this.nifInfractor = nifInfractor;
        this.idtypeOfFine = idtypeOfFine;
    }

    public TraficFine(String description, LocalDateTime date, Double amount, Long idPolice, String nifInfractor, Long idtypeOfFine) {
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.idPolice = idPolice;
        this.nifInfractor = nifInfractor;
        this.idtypeOfFine = idtypeOfFine;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getIdPolice() {
        return idPolice;
    }

    public String getNifInfractor() {
        return nifInfractor;
    }

    public Long getIdtypeOfFine() {
        return idtypeOfFine;
    }

    @Override
    public String toString() {
        return "Multa{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", ammount=" + amount +
                ", idPolice=" + idPolice +
                ", nifInfractor=" + nifInfractor +
                '}';
    }
}
