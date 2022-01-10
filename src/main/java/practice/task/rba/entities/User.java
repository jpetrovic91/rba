package practice.task.rba.entities;

import practice.task.rba.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name="oib")
    private String OIB;

    private String ime;
    private String prezime;
    private Status status;

    public User(){}

    public User(String OIB, String ime, String prezime, Status status){
        this.OIB = OIB;
        this.ime = ime;
        this.prezime = prezime;
        this.status = status;
    }

    public User(String OIB, String ime, String prezime, String status){
        this.OIB = OIB;
        this.ime = ime;
        this.prezime = prezime;
        this.status = Status.valueOf(status.toUpperCase());
    }

    public void setOIB(String OIB) {
        this.OIB = OIB;
    }

    public String getOIB() {
        return OIB;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getIme() {
        return ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String fileRepresentationString(String statusForFile){
        String delimiter = ";";
        return OIB +
                delimiter +
                ime +
                delimiter +
                prezime +
                delimiter +
                statusForFile;
    }
}
