package MODEL;

public class Police {
    private Long id ;
    private String name;
    private String policePlateNumber ;
    private Integer age;
    private String department ;
    private String photoLink;

    public Police(Long id, String name, String policePlateNumber, Integer age, String department, String photoLink) {
        this.id = id;
        this.name = name;
        this.policePlateNumber = policePlateNumber;
        this.age = age;
        this.department = department;
        this.photoLink = photoLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolicePlateNumber() {
        return policePlateNumber;
    }

    public void setPolicePlateNumber(String policePlateNumber) {
        this.policePlateNumber = policePlateNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    @Override
    public String toString() {
        return "Police{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", policePlateNumber='" + policePlateNumber + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", photoLink='" + photoLink + '\'' +
                '}';
    }
}
