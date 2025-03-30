package Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Director extends Employee implements Serializable {
    private BigDecimal serviceAllowance, costLimitPerMonth;
    private String serviceCard;
    public Director(String name, String surname, String position, String pesel, BigDecimal salary, long phoneNumber, BigDecimal serviceAllowance, BigDecimal costLimitPerMonth, String serviceCard)
    {
        super(name, surname, position, pesel, salary, phoneNumber);
        this.serviceAllowance = serviceAllowance;
        this.costLimitPerMonth = costLimitPerMonth;
        this.serviceCard = serviceCard;
    }

    public BigDecimal getServiceAllowance() {
        return serviceAllowance;
    }

    public void setServiceAllowance(BigDecimal serviceAllowance) {
        this.serviceAllowance = serviceAllowance;
    }

    public BigDecimal getCostLimitPerMonth() {
        return costLimitPerMonth;
    }
    public void setCostLimitPerMonth(BigDecimal costLimitPerMonth) {
        this.costLimitPerMonth = costLimitPerMonth;
    }
    public String getServiceCard() {
        return serviceCard;
    }
    public void setServiceCard(String serviceCard) {
        this.serviceCard = serviceCard;
    }

    @Override
    public void print() {
        System.out.println("Imię: " + getName());
        System.out.println("Nazwisko: " + getSurname());
        System.out.println("Stanowisko: " + getPosition());
        System.out.println("PESEL: " + getPesel());
        System.out.println("Wynagrodzenie: " + getSalary());
        System.out.println("Numer telefonu: " + getPhoneNumber());
        System.out.println("Dodatek służbowy: " + serviceAllowance);
        System.out.println("Limit kosztów/miesiąc: " + costLimitPerMonth);
        System.out.println("Numer karty służbowej: " + serviceCard);
    }
}
