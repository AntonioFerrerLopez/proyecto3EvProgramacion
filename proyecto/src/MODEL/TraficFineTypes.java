package MODEL;

public class TraficFineTypes {

    private Long id;
    private String description ;
    private Double amount;
    private String fineType;
    private Integer drivingCardPoints;

    public TraficFineTypes(Long id, String description, Double amount, String fineType, Integer drivingCardPoints) {
        this.id = id;
        this.description = description;
        this.amount = amount;
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

    public Double getAmount() {
        return amount;
    }

    public String getFineType() {
        return fineType;
    }

    public Integer getDrivingCardPoints() {
        return drivingCardPoints;
    }

    @Override
    public String toString() {
        return "PoliceRelatedFine{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", ammount=" + amount +
                ", fineType='" + fineType + '\'' +
                ", drivingCardPoints=" + drivingCardPoints +
                '}';
    }
}
