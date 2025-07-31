package entity;

public class Employee {
    private Long id;
    private String name;
    private Integer age;
    private Long id_profile;

    public Employee(Long id, String name, Integer age, Long id_profile) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.id_profile = id_profile;
    }
    public Employee() {}
    public Long getId() {return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getId_profile() {
        return id_profile;
    }

    public void setId_profile(Long id_profile) {
        this.id_profile = id_profile;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
