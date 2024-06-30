package ru.job4j.accidents.exception;

/**
 * @author Constantine on 30.06.2024
 */
public class ControllerException extends Exception {

    public ControllerException(String message, Exception base) {
        super(message, base);
    }

    /**
     * Перегрузил конструктор, т.к. имеются случаи,
     * когда результат вычисления оборачивается в
     * {@link java.util.Optional}.
     *
     * @param message сообщение, которое будет записано в лог.
     */
    public ControllerException(String message) {
        super(message);
    }
}
