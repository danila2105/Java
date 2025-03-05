/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1_java;

/**
 *
 * @author Ant
 */
public class IntegralCalculatorThread extends Thread {
    private double a, b, h;
    private double partialResult;
    
    public IntegralCalculatorThread(double a, double b, double h) {
        this.a = a;
        this.b = b;
        this.h = h;
        this.partialResult = 0.0;
    }

    @Override
    public void run() {
        System.out.println("Potok " + Thread.currentThread().getId() + " nachal vichislat ot " + a + " do " + b);
        double sum = 0.0;
        double x = a;

        while (x + h <= b) {
            sum += (h / 2.0) * (Math.cos(x) + Math.cos(x + h));
            x += h;
        }

        if (x < b) {
            double last_h = b - x;
            sum += (last_h / 2.0) * (Math.cos(x) + Math.cos(b));
        }

        this.partialResult = sum;
        
        System.out.println("Potok " + Thread.currentThread().getId() + " zavershil vichislenie: " + partialResult);
    }

    public double getPartialResult() {
        return partialResult;
    }
}