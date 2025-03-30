package Model;
import java.math.BigDecimal;
import java.io.Serializable;

public abstract class Employee implements Serializable {
    private String name, surname, position, pesel;
    private BigDecimal salary;
    private long phoneNumber;

    public Employee(String name, String surname, String position, String pesel, BigDecimal salary, long phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.pesel = pesel;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getSurname() {return surname;}
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() { return position;}
    public void setPosition(String position) {this.position = position;}

    public String getPesel() { return pesel;}
    public void setPesel(String pesel) {this.pesel = pesel;}

    public BigDecimal getSalary() { return salary;}
    public void setSalary(BigDecimal salary) {this.salary = salary;}

    public long getPhoneNumber() { return phoneNumber;}
    public void setPhoneNumber(long phoneNumber) {this.phoneNumber = phoneNumber;}

    public abstract void print();
}

