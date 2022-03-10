package com.jobseekers.mc1.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Jsdata.
 */
@Entity
@Table(name = "jsdata")
public class Jsdata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(min = 0, max = 20)
    @Column(name = "gender", length = 20, unique = true)
    private String gender;

    @Size(min = 0, max = 50)
    @Column(name = "region", length = 50)
    private String region;

    @Size(min = 0, max = 50)
    @Column(name = "city", length = 50)
    private String city;

    @DecimalMin(value = "0")
    @DecimalMax(value = "30")
    @Column(name = "age")
    private Double age;

    @Size(min = 0, max = 50)
    @Column(name = "degree", length = 50)
    private String degree;

    @Size(min = 0, max = 50)
    @Column(name = "major", length = 50)
    private String major;

    @Size(min = 0, max = 50)
    @Column(name = "inistitute", length = 50)
    private String inistitute;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Jsdata id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return this.gender;
    }

    public Jsdata gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return this.region;
    }

    public Jsdata region(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return this.city;
    }

    public Jsdata city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getAge() {
        return this.age;
    }

    public Jsdata age(Double age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public String getDegree() {
        return this.degree;
    }

    public Jsdata degree(String degree) {
        this.setDegree(degree);
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return this.major;
    }

    public Jsdata major(String major) {
        this.setMajor(major);
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getInistitute() {
        return this.inistitute;
    }

    public Jsdata inistitute(String inistitute) {
        this.setInistitute(inistitute);
        return this;
    }

    public void setInistitute(String inistitute) {
        this.inistitute = inistitute;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jsdata)) {
            return false;
        }
        return id != null && id.equals(((Jsdata) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jsdata{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", region='" + getRegion() + "'" +
            ", city='" + getCity() + "'" +
            ", age=" + getAge() +
            ", degree='" + getDegree() + "'" +
            ", major='" + getMajor() + "'" +
            ", inistitute='" + getInistitute() + "'" +
            "}";
    }
}
