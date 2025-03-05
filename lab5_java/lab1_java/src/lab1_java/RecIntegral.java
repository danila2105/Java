/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1_java;
import java.io.Serializable;
/**
 *
 * @author Ant
 */
public class RecIntegral implements Serializable{
    private static final long serialVersionUID = 1L;
    private double a;  
    private double b;  
    private double h;  
    private double result; 

    // Constructor
    public RecIntegral(double a, double b, double h) {
        this.a = a;
        this.b = b;
        this.h = h;
        this.result = 0.0;  
    }

    // Getters and Setters
    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }


    public void calculate() {
    long startTime = System.currentTimeMillis(); // Запоминаем время начала
    double mid1 = a + (b - a) / 3;
    double mid2 = a + 2 * (b - a) / 3;

    IntegralCalculatorThread thread1 = new IntegralCalculatorThread(a, mid1, h);
    IntegralCalculatorThread thread2 = new IntegralCalculatorThread(mid1, mid2, h);
    IntegralCalculatorThread thread3 = new IntegralCalculatorThread(mid2, b, h);

    thread1.start();
    thread2.start();
    thread3.start();

    try {
        thread1.join();
        thread2.join();
        thread3.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    this.result = thread1.getPartialResult() + thread2.getPartialResult() + thread3.getPartialResult();
    long endTime = System.currentTimeMillis(); // Запоминаем время начала
    long executionTime = endTime - startTime;  // Вычисляем разницу
    System.out.println("Time: " + executionTime + " ms");
}

}

