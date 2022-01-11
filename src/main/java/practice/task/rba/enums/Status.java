package practice.task.rba.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

    AKTIVAN("Aktivan"),
    NEAKTIVAN("Neaktivan");

    private String status;

    Status(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    @JsonCreator
    public static Status getStatus(String value) {

        for (Status status : Status.values())
            if (status.toString().equals(value))
                return status;

        return null;
    }

    @JsonValue
    public String getStatusString(){
        return status;
    }
}
