package model;

/**
 * @author Pieter Herremans
 */

public class ModelException extends RuntimeException {

    public ModelException(Exception exception){ super(exception);}

    public ModelException(String message) { super(message);}


}
