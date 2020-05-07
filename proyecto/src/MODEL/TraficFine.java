package MODEL;

import java.sql.Date;
import java.time.LocalDateTime;

public class TraficFine {
    private Long id ;
    private String description ;
    private LocalDateTime date;
    private Double ammount ;
    private Integer idPolice ;
    private String nifOffender ;
    private Integer idtypeOfFine;

    public TraficFine(Long id, String description, LocalDateTime date, Double ammount, Integer idPolice, String nifOffender, Integer idtypeOfFine) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.ammount = ammount;
        this.idPolice = idPolice;
        this.nifOffender = nifOffender;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateToDb() {
        LocalDateTime dateTime = this.date;
        return Date.valueOf(dateTime.toLocalDate());
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public Integer getIdPolice() {
        return idPolice;
    }

    public void setIdPolice(Integer idPolice) {
        this.idPolice = idPolice;
    }

    public String getNifOffender() {
        return nifOffender;
    }

    public void setNifOffender(String nifOffender) {
        this.nifOffender = nifOffender;
    }

    public Integer getIdtypeOfFine() {
        return idtypeOfFine;
    }

    public void setIdtypeOfFine(Integer idtypeOfFine) {
        this.idtypeOfFine = idtypeOfFine;
    }

    @Override
    public String toString() {
        return "Multa{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", ammount=" + ammount +
                ", idPolice=" + idPolice +
                ", nifOffender=" + nifOffender +
                '}';
    }
}
