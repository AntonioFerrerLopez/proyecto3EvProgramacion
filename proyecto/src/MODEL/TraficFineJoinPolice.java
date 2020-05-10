package MODEL;

import java.time.LocalDateTime;

public class TraficFineJoinPolice {
    private Long idFine ;
    private String descriptionFine ;
    private LocalDateTime dateFine;
    private Double ammountFine ;
    private String nifInfractorFine ;
    private String descriptionType ;
    private Integer drivingCardPoints;
    private String policeName;
    private String policePlateNumber ;


    public TraficFineJoinPolice(Long idFine, String descriptionFine, LocalDateTime dateFine, Double ammountFine, String nifInfractorFine, String descriptionType, Integer drivingCardPoints, String policeName, String policePlateNumber) {
        this.idFine = idFine;
        this.descriptionFine = descriptionFine;
        this.dateFine = dateFine;
        this.ammountFine = ammountFine;
        this.nifInfractorFine = nifInfractorFine;
        this.descriptionType = descriptionType;
        this.drivingCardPoints = drivingCardPoints;
        this.policeName = policeName;
        this.policePlateNumber = policePlateNumber;

    }

    public Long getIdFine() {
        return idFine;
    }

    public void setIdFine(Long idFine) {
        this.idFine = idFine;
    }

    public String getDescriptionFine() {
        return descriptionFine;
    }

    public void setDescriptionFine(String descriptionFine) {
        this.descriptionFine = descriptionFine;
    }

    public LocalDateTime getDateFine() {
        return dateFine;
    }

    public void setDateFine(LocalDateTime dateFine) {
        this.dateFine = dateFine;
    }

    public Double getAmmountFine() {
        return ammountFine;
    }

    public void setAmmountFine(Double ammountFine) {
        this.ammountFine = ammountFine;
    }

    public String getNifInfractorFine() {
        return nifInfractorFine;
    }

    public void setNifInfractorFine(String nifInfractorFine) {
        this.nifInfractorFine = nifInfractorFine;
    }

    public String getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(String descriptionType) {
        this.descriptionType = descriptionType;
    }

    public Integer getDrivingCardPoints() {
        return drivingCardPoints;
    }

    public void setDrivingCardPoints(Integer drivingCardPoints) {
        this.drivingCardPoints = drivingCardPoints;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getPolicePlateNumber() {
        return policePlateNumber;
    }

    public void setPolicePlateNumber(String policePlateNumber) {
        this.policePlateNumber = policePlateNumber;
    }

    @Override
    public String toString() {
        return "TraficFineJoinPolice{" +
                "idFine=" + idFine +
                ", descriptionFine='" + descriptionFine + '\'' +
                ", dateFine=" + dateFine +
                ", ammountFine=" + ammountFine +
                ", nifInfractorFine='" + nifInfractorFine + '\'' +
                ", descriptionType='" + descriptionType + '\'' +
                ", drivingCardPoints=" + drivingCardPoints +
                ", policeName='" + policeName + '\'' +
                ", policePlateNumber='" + policePlateNumber + '\'' +
                '}';
    }
}
