package model.database;

import java.io.FileNotFoundException;

/**
 * @author Pieter Herremans
 */

public class DomainException extends Throwable {

    public DomainException(String message, FileNotFoundException ex){ super(message);}
}
