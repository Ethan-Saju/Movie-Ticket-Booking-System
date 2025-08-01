package com.movie.booking.exceptions;

public class ResourceNotFoundException  extends RuntimeException{

    String resourceName;
    String field;
    Long fieldId;
    String fieldValue;

    public ResourceNotFoundException(){

    }

    public ResourceNotFoundException( String resourceName, String field, String fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName,field,fieldValue));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException( String resourceName, String field , Long fieldId) {
        super(String.format("%s not found with %s: %d", resourceName,field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;

    }


}
