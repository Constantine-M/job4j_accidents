package ru.job4j.accidents.exception;

/**
 * @author Constantine on 22.06.2024
 */
public class ServiceException extends Exception {

    public ServiceException(String message, Exception base) {
        super(message, base);
    }

    /**
     * Перегрузил конструктор, т.к. имеются случаи,
     * когда результат вычисления оборачивается в
     * {@link java.util.Optional}.
     *
     * @param message сообщение, которое будет записано в лог.
     */
    public ServiceException(String message) {
        super(message);
    }
}
