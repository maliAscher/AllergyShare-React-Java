package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Category {//http://localhost:8585/h2-console

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private String icon;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Sharing> sharing;

    public Category() {
    }

    public Category(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public void addSharing(Sharing sharing) {
        this.sharing.add(sharing);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

