package bootstrap.domain;

import java.io.Serializable;
import javax.persistence.*;

public class People implements Serializable {
    @Id
    private Integer id;

    private String name;

    private Integer age;

    private Long version;

    @Column(name = "name_scrc")
    private Integer nameScrc;

    public People() {
    }

    public People(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return name_scrc
     */
    public Integer getNameScrc() {
        return nameScrc;
    }

    /**
     * @param nameScrc
     */
    public void setNameScrc(Integer nameScrc) {
        this.nameScrc = nameScrc;
    }
}