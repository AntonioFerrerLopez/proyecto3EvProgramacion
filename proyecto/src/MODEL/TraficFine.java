package MODEL;

import com.jfoenix.controls.JFXTextField;

import java.sql.Date;
import java.time.LocalDateTime;

public class TraficFine {
    private Long id ;
    private String description ;
    private LocalDateTime date;
    private Double ammount ;
    private Long idPolice ;
    private String nifInfractor ;
    private Long idtypeOfFine;

    public TraficFine(Long id, String description, LocalDateTime date, Double ammount, Long idPolice, String nifInfractor, Long idtypeOfFine) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.ammount = ammount;
        this.idPolice = idPolice;
        this.nifInfractor = nifInfractor;
        this.idtypeOfFine = idtypeOfFine;
    }

    public TraficFine(String description, LocalDateTime date, Double ammount, Long idPolice, String nifInfractor, Long idtypeOfFine) {
        this.description = description;
        this.date = date;
        this.ammount = ammount;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateToDb() {
        return this.date;
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

    public Long getIdPolice() {
        return idPolice;
    }

    public void setIdPolice(Long idPolice) {
        this.idPolice = idPolice;
    }

    public String getNifInfractor() {
        return nifInfractor;
    }


    public Long getIdtypeOfFine() {
        return idtypeOfFine;
    }

    public void setIdtypeOfFine(Long idtypeOfFine) {
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
                ", nifInfractor=" + nifInfractor +
                '}';
    }
}
