package MODEL;

public class TraficFineTypes {

    private Long id;
    private String description ;
    private Double ammount ;
    private String fineType;
    private Integer drivingCardPoints;

    public TraficFineTypes(Long id, String description, Double ammount, String fineType, Integer drivingCardPoints) {
        this.id = id;
        this.description = description;
        this.ammount = ammount;
        this.fineType = fineType;
        this.drivingCardPoints = drivingCardPoints;
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

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public String getFineType() {
        return fineType;
    }

    public void setFineType(String fineType) {
        this.fineType = fineType;
    }

    public Integer getDrivingCardPoints() {
        return drivingCardPoints;
    }

    public void setDrivingCardPoints(Integer drivingCardPoints) {
        this.drivingCardPoints = drivingCardPoints;
    }

    @Override
    public String toString() {
        return "PoliceRelatedFine{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", ammount=" + ammount +
                ", fineType='" + fineType + '\'' +
                ", drivingCardPoints=" + drivingCardPoints +
                '}';
    }
}
