package Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Trader extends Employee implements Serializable {
    private BigDecimal comission, comissionLimitPerMonth;

    public Trader(String name, String surname, String position, String pesel, BigDecimal salary, long phoneNumber, BigDecimal comission, BigDecimal comissionLimitPerMonth)
    {
        super(name, surname, position, pesel, salary, phoneNumber);
        this.comission = comission;
        this.comissionLimitPerMonth = comissionLimitPerMonth;
    }

    public BigDecimal getComission(){
        return this.comission;
    }

    public void setComission(BigDecimal comission) {
        this.comission = comission;
    }

    public BigDecimal getComissionLimitPerMonth(){
        return this.comissionLimitPerMonth;
    }

    public void setComissionLimitPerMonth(BigDecimal comissionLimitPerMonth) {
        this.comissionLimitPerMonth = comissionLimitPerMonth;
    }

    @Override
    public void print() {
        System.out.println("Imię: " + getName());
        System.out.println("Nazwisko: " + getSurname());
        System.out.println("Stanowisko: " + getPosition());
        System.out.println("PESEL: " + getPesel());
        System.out.println("Wynagrodzenie: " + getSalary());
        System.out.println("Numer telefonu: " + getPhoneNumber());
        System.out.println("Prowizja: " + comission + "%");
        System.out.println("Limit prowizji/miesiąc: " + comissionLimitPerMonth);
    }
}
