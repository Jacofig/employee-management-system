package Controller;

import java.io.*;
import java.util.zip.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import View.View;
import Model.Employee;
import Model.Director;
import Model.Trader;

public class Controller {
    private final List<Employee> employees = new ArrayList<>();
    private final View view = new View();

    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            int choice = view.displayMainMenu();

            switch (choice) {
                case 1:
                    view.displayEmployees(employees);
                    break;
                case 2:
                    addEmployee();
                    break;
                case 3:
                    removeEmployee();
                    break;
                case 4:
                    backupEmployees();
                    break;
                case 5:
                    view.displayMessage("Wyjśćie z programu.");
                    System.exit(0);
                default:
                    view.displayError("Nieprawidłowa opcja.");
            }
        }
    }

    private void addEmployee() {
        String type = view.getType();
        String name = view.getName();
        String surname = view.getSurname();
        String pesel;

        while (true) {
            pesel = view.getPesel();
            if (!isPeselValid(pesel)) {
                view.displayError("Nieprawidłowy PESEL.");
                continue;
            }
            if (!isPeselUnique(pesel)) {
                view.displayError("Pracownik o podanym PESEL już istnieje.");
                continue;
            }
            break;
        }

        BigDecimal salary = view.getSalary();
        long phoneNumber = view.getPhoneNumber();

        if (type.equals("D")) {
            BigDecimal serviceAllowance = view.getServiceAllowance();
            BigDecimal costLimitPerMonth = view.getCostLimitPerMonth();
            String serviceCard = view.getServiceCard();
            addDirector(name, surname, pesel, salary, phoneNumber, serviceAllowance, costLimitPerMonth, serviceCard);
        } else if (type.equals("H")) {
            BigDecimal commission = view.getCommission();
            BigDecimal commissionLimitPerMonth = view.getCommissionLimitPerMonth();
            addTrader(name, surname, pesel, salary, phoneNumber, commission, commissionLimitPerMonth);
        } else {
            view.displayError("Nieprawidłowy typ pracownika.");
        }
    }

    private boolean isPeselValid(String pesel) {
        if (pesel == null || !pesel.matches("\\d{11}")) {
            return false;
        }

        int[] weights = {9, 7, 3, 1, 9, 7, 3, 1, 9, 7};
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(pesel.charAt(i)) * weights[i];
        }

        int checksum = sum % 10;
        int lastDigit = Character.getNumericValue(pesel.charAt(10));

        return checksum == lastDigit;
    }

    private boolean isPeselUnique(String pesel) {
        for (Employee employee : employees) {
            if (employee.getPesel().equals(pesel)) {
                return false;
            }
        }
        return true;
    }

    private void removeEmployee() {
        String pesel = view.getPesel();
        Employee employeeToRemove = null;

        for (Employee employee : employees) {
            if (employee.getPesel().equals(pesel)) {
                employeeToRemove = employee;
                view.displayMessage("Znaleziono pracownika do usunięcia: ");
                view.displayEmployee(employee);
                break;
            }
        }

        if (employeeToRemove != null) {
            String decision = view.getDecision();
            if (decision.equals("")) {
                employees.remove(employeeToRemove);
                view.displayMessage("Usunięto pracownika.");
            } else if (decision.equals("Q")) {
                view.displayMessage("Usuwanie porzucone.");
            } else {
                view.displayError("Nieprawidłowa opcja.");
            }
        } else {
            view.displayError("Nie znaleziono pracownika o podanym numerze pesel.");
        }
    }

    private void addDirector(String name, String surname, String pesel, BigDecimal salary, long phoneNumber,
                             BigDecimal serviceAllowance, BigDecimal costLimitPerMonth, String serviceCard) {
        Director director = new Director(
                name, surname, "Dyrektor", pesel, salary, phoneNumber, serviceAllowance, costLimitPerMonth, serviceCard);
        String decision = view.getDecision();
        if (decision.equals("")) {
            employees.add(director);
            view.displayMessage("Dodano nowego dyrektora.");
        } else if (decision.equals("Q")) {
            view.displayMessage("Dodawanie porzucone.");
        }
    }

    private void addTrader(String name, String surname, String pesel, BigDecimal salary, long phoneNumber,
                           BigDecimal commission, BigDecimal commissionLimitPerMonth) {
        Trader trader = new Trader(name, surname, "Handlowiec", pesel, salary, phoneNumber, commission, commissionLimitPerMonth);
        String decision = view.getDecision();
        if (decision.equals("")) {
            employees.add(trader);
            view.displayMessage("Dodano nowego handlowca.");
        } else if (decision.equals("Q")) {
            view.displayMessage("Dodawanie porzucone.");
        }
    }

    private void backupEmployees() {
        int choice = view.displayBackupMenu();

        switch (choice) {
            case 1:
                createBackup();
                break;
            case 2:
                restoreFromArchive();
                break;
            default:
                view.displayError("Nieprawidłowa opcja.");
        }
    }

    private void createBackup() {
        String fileFormat = view.getBackupFormat();
        String fileName = view.getBackupName();

        switch (fileFormat) {
            case "Z":
                backupToZip(fileName);
                break;
            case "G":
                backupToGzip(fileName);
                break;
            default:
                view.displayError("Wybrano nieprawidłowy format pliku.");
        }
    }

    private void backupToZip(String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName + ".zip");
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.putNextEntry(new ZipEntry("employees.dat"));
            ObjectOutputStream oos = new ObjectOutputStream(zos);
            oos.writeObject(employees);
            oos.flush();
            zos.closeEntry();
            view.displayMessage("Utworzono kopię w archiwum .zip.");
        } catch (IOException e) {
            view.displayError("Błąd podczas tworzenia kopii: " + e.getMessage());
        }
    }

    private void backupToGzip(String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName + ".gzip");
             GZIPOutputStream gos = new GZIPOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(gos)) {
            oos.writeObject(employees);
            view.displayMessage("Utworzono kopię w archiwum .gzip.");
        } catch (IOException e) {
            view.displayError("Błąd podczas tworzenia kopii: " + e.getMessage());
        }
    }

    private void restoreFromArchive() {
        try {
            String backupName = view.getBackupName();

            if (!backupName.endsWith(".zip") && !backupName.endsWith(".gzip")) {
                view.displayError("Niepoprawny format. Obsługiwane formaty: .zip lub .gzip.");
                return;
            }

            String decision = view.getDecision();
            if (decision.equals("Q")) {
                view.displayMessage("Porzucono.");
                return;
            } else if (!decision.equals("")) {
                view.displayError("Nieprawidłowa opcja.");
                return;
            }

            if (backupName.endsWith(".zip")) {
                restoreFromZip(backupName);
            } else if (backupName.endsWith(".gzip")) {
                restoreFromGzip(backupName);
            }
        } catch (Exception e) {
            view.displayError("Błąd podczas wypakowywania: " + e.getMessage());
        }
    }

    private void restoreFromZip(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName);
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry entry = zis.getNextEntry();
            if (entry != null && entry.getName().equals("employees.dat")) {
                ObjectInputStream ois = new ObjectInputStream(zis);
                employees.clear();
                employees.addAll((List<Employee>) ois.readObject());
                view.displayMessage("Wypakowanie zakończone sukcesem.");
            } else {
                view.displayError("Wypakowanie zakończone niepowodzeniem.");
            }
        } catch (IOException | ClassNotFoundException e) {
            view.displayError("Wypakowanie zakończone niepowodzeniem:  " + e.getMessage());
        }
    }

    private void restoreFromGzip(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName);
             GZIPInputStream gzis = new GZIPInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(gzis)) {
            employees.clear();
            employees.addAll((List<Employee>) ois.readObject());
            view.displayMessage("Wypakowanie zakończone sukcesem.");
        } catch (IOException | ClassNotFoundException e) {
            view.displayError("Wypakowanie zakończone niepowodzeniem: " + e.getMessage());
        }
    }
}
