/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1_java;

/**
 *
 * @author Ant
 */
public class InvalidRecIntegralException extends Exception {
    // Конструктор, который принимает сообщение об ошибке
    public InvalidRecIntegralException(String message) {
        // Передаем сообщение родительскому конструктору Exception
        super(message);
    }
}

