package org.kainos.ea.cli;

public class Contractor  implements IPayable{

    private String name;
    private  double dailyRate;
    private int monthlyDaysWorked;

    public Contractor(String name, double dailyRate, int monthlyDaysWorked) {
        this.setName(name);
        this.setDailyRate(dailyRate);
        this.setMonthlyDaysWorked(monthlyDaysWorked);
    }

    public double calcPay(){
        return getDailyRate() * getMonthlyDaysWorked();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getMonthlyDaysWorked() {
        return monthlyDaysWorked;
    }

    public void setMonthlyDaysWorked(int monthlyDaysWorked) {
        this.monthlyDaysWorked = monthlyDaysWorked;
    }
}
