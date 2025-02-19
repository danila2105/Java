/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1_java;

/**
 *
 * @author Ant
 */
public class RecIntegral {
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

        this.result = sum;  
    }
}

