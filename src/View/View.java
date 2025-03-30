package View;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Scanner;
import Model.Employee;
import Model.Trader;
import Model.Director;

public class View {
    public final Scanner scanner = new Scanner(System.in);

    public int displayMainMenu() {
        System.out.print("MENU\n1. Lista pracowników\n2. Dodaj pracownika\n3. Usuń pracownika\n4. Kopia zapasowa\n5. Wyjście\nWybór> ");
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input == 1 || input == 2 || input == 3 || input == 4 || input == 5) {
                    return input;
                } else {
                    displayError("Nieprawidłowy wybór. Wybierz 1/2/3/4/5.");
                }
            } catch (NumberFormatException e) {
                displayError("Nieprawidłowy wybór. Wprowadź liczbę 1/2/3/4/5.");
            }
        }
    }

    public String getType() {
        System.out.print("Dodać dyrektora (D) czy handlowca (H)? ");
        while (true) {
            String type = scanner.nextLine().trim().toUpperCase();
            if (type.equals("D") || type.equals("H")) {
                return type;
            }
            displayError("Nieprawidłowy wybór. Wybierz D (dyrektor) lub H (handlowiec).");
        }
    }


    public String getDecision() {
        System.out.println("[Enter] - zapisz | [Q] - porzuc");
        return scanner.nextLine().trim().toUpperCase();
    }

    public String getName() {
        System.out.print("Imię: ");
        while (true) {
            String name = scanner.nextLine().trim();
            if (!name.isEmpty() && name.matches("[A-Za-z]+")) {
                return name;
            }
            displayError("Imię nie może być puste i musi zawierać same litery.");
        }
    }

    public String getSurname() {
        System.out.print("Nazwisko: ");
        while (true) {
            String surname = scanner.nextLine().trim();
            if (!surname.isEmpty() && surname.matches("[A-Za-z]+")) {
                return surname;
            }
            displayError("Nazwisko nie może być puste i musi zawierać same litery.");
        }
    }

    public String getPesel() {
        System.out.print("Pesel: ");
        while (true) {
            String pesel = scanner.nextLine().trim();
            if (pesel.matches("\\d{11}")) {
                return pesel;
            }
            displayError("Pesel musi mieć 11 cyfr.");
        }
    }

    public BigDecimal getSalary() {
        System.out.print("Wypłata: ");
        while (true) {
            try {
                BigDecimal salary = new BigDecimal(scanner.nextLine().trim());
                if (salary.compareTo(BigDecimal.ZERO) > 0) {
                    return salary;
                }
                displayError("Wypłata musi być liczbą dodatnią.");
            } catch (NumberFormatException e) {
                displayError("Nieprawidłowa wartość. Wprowadź liczbę.");
            }
        }
    }

    public long getPhoneNumber() {
        System.out.print("Numer telefonu: ");
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.matches("\\d{9}")) {
                    return Long.parseLong(input);
                }
                displayError("Numer telefonu musi mieć 9 cyfr.");
            } catch (NumberFormatException e) {
                displayError("Nieprawidłowa wartość. Wprowadź liczbę.");
            }
        }
    }

    public BigDecimal getServiceAllowance() {
        System.out.print("Dodatek służbowy: ");
        while (true) {
            try {
                BigDecimal allowance = new BigDecimal(scanner.nextLine().trim());
                if (allowance.compareTo(BigDecimal.ZERO) >= 0) {
                    return allowance;
                }
                displayError("Dodatek służbowy nie może być ujemny.");
            } catch (NumberFormatException e) {
                displayError("Nieprawidłowa wartość. Wprowadź liczbę.");
            }
        }
    }

    public BigDecimal getCostLimitPerMonth() {
        System.out.print("Limit kosztów/miesiąc: ");
        while (true) {
            try {
                BigDecimal limit = new BigDecimal(scanner.nextLine().trim());
                if (limit.compareTo(BigDecimal.ZERO) >= 0) {
                    return limit;
                }
                displayError("Limit kosztów nie może być ujemny.");
            } catch (NumberFormatException e) {
                displayError("Nieprawidłowa wartość. Wprowadź liczbę.");
            }
        }
    }

    public String getServiceCard() {
        System.out.print("Numer karty służbowej: ");
        while (true) {
            String cardNumber = scanner.nextLine().trim();
            if (!cardNumber.isEmpty()) {
                return cardNumber;
            }
            displayError("Numer karty służbowej nie może być pusty.");
        }
    }

    public BigDecimal getCommission() {
        System.out.print("Prowizja (%): ");
        while (true) {
            try {
                BigDecimal commission = new BigDecimal(scanner.nextLine().trim());
                if (commission.compareTo(BigDecimal.ZERO) >= 0 && commission.compareTo(BigDecimal.valueOf(100)) <= 0) {
                    return commission;
                }
                displayError("Prowizja musi być w zakresie 0-100%.");
            } catch (NumberFormatException e) {
                displayError("Nieprawidłowa wartość. Wprowadź liczbę.");
            }
        }
    }

    public BigDecimal getCommissionLimitPerMonth() {
        System.out.print("Limit prowizji/miesiąc: ");
        while (true) {
            try {
                BigDecimal limit = new BigDecimal(scanner.nextLine().trim());
                if (limit.compareTo(BigDecimal.ZERO) >= 0) {
                    return limit;
                }
                displayError("Limit prowizji nie może być ujemny.");
            } catch (NumberFormatException e) {
                displayError("Nieprawidłowa wartość. Wprowadź liczbę.");
            }
        }
    }

    public void displayEmployee(Employee employee) {
        System.out.println("\n--- Dane pracownika ---");
        System.out.println("Imię: " + employee.getName());
        System.out.println("Nazwisko: " + employee.getSurname());
        System.out.println("Stanowisko: " + employee.getPosition());
        System.out.println("PESEL: " + employee.getPesel());
        System.out.println("Wynagrodzenie: " + employee.getSalary());
        System.out.println("Numer telefonu: " + employee.getPhoneNumber());
    }

    public void displayEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            displayError("Brak pracowników\n");
            return;
        }
        int index = 0;
        while (true) {
            Employee employee = employees.get(index);
            employee.print(); // Use the print method

            System.out.printf("[Pozycja: %d/%d]\n", index + 1, employees.size());
            System.out.println("[Enter] - następny | [Q] - poprzedni | [X] - wyjście");

            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("")) {
                index = (index + 1) % employees.size();
            } else if (input.equals("Q")) {
                index = (index - 1 + employees.size()) % employees.size();
            } else if (input.equals("X")) {
                break;
            } else {
                displayError("Nieprawidłowy wybór. Wybierz: [Enter] - następny | [Q] - poprzedni | [X] - wyjście");
            }
        }
    }


    public int displayBackupMenu() {
        System.out.println("Kopia zapasowa");
        System.out.println("1. Utwórz kopię");
        System.out.println("2. Rozpakuj archiwum");
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input == 1 || input == 2) {
                    return input;
                } else {
                    displayError("Nieprawidłowy wybór. Wybierz 1 lub 2.");
                }
            } catch (NumberFormatException e) {
                displayError("Nieprawidłowy wybór. Wprowadź liczbę 1 lub 2.");
            }
        }
    }

    public String getBackupFormat() {
        while (true) {
            System.out.println("Kompresja [G]zip/[Z]ip");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("G")) {
                return "G";
            } else if (input.equals("Z")) {
                return "Z";
            } else {
                displayError("Nieprawidłowy wybór. Wybierz G lub Z.");
            }
        }
    }

    public String getBackupName() {
        System.out.print("Podaj nazwę pliku: ");
        return scanner.nextLine().trim();
    }

    public void displayMessage(String message) {
        System.out.println("\n" + message);
    }

    public void displayError(String error) {
        System.err.println(error);
    }
}
