package deepclone.entity;

import java.math.BigDecimal;
import java.util.List;

public class Employee {

    private Man man;
    private BigDecimal salary;
    private List<String[]> workDays;
    private boolean active;
    private Employee chief;

    public Employee(Man man, BigDecimal salary, List<String[]> workDays, boolean active) {
        this.man = man;
        this.salary = salary;
        this.workDays = workDays;
        this.active = active;
    }

    public String getWorkDaysString() {
        if (workDays != null) {
            StringBuilder sb = new StringBuilder();
            for (String[] s : workDays) {
                sb.append("[").append(String.join(", ", s)).append("]");
            }
            return sb.toString();
        }
        return "";
    }

    public Man getMan() {
        return man;
    }

    public void setMan(Man man) {
        this.man = man;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public List<String[]> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<String[]> workDays) {
        this.workDays = workDays;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Employee getChief() {
        return chief;
    }

    public void setChief(Employee chief) {
        this.chief = chief;
    }

    public String getFullInfo() {
        return String.format("MAN: %s, SALARY: %s, WORKDAYS: %s, ACTIVE: %s, CHIEF: %s",
                man.getName(), salary, getWorkDaysString(), active, chief != null ? chief.getMan().getName() : "");
    }
}
