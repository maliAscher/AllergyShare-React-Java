package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
public class Content {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String text;
  private String Title;
  private int numRow;
  @JsonIgnore
  @ManyToOne
  private Sharing sharing;

  public Content(Long id, String text, int numRow,String Title, Sharing s){
    this.id = id;
    this.text = text;
    this.Title= Title;
    this.numRow = numRow;
    this.sharing = s;
  }

  public Content() {

  }

  public Long getId() {return id;}

  public void setId(Long id) {this.id = id ;}

  public String getText() {return text;}

  public void setText(String text) {this.text = text;}

  public int getNumRow() {return numRow;}

  public void setNumRow(int numRow) {this.numRow = numRow;}

  public Sharing getSharing() {return sharing;}

  public void setSharing(Sharing sharing) {this.sharing = sharing;}

  public String getTitle() {return Title;}

  public void setTitle(String title) {Title = title;}
}
