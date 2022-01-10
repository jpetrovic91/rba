package practice.task.rba.converters;

import practice.task.rba.enums.Status;

import javax.persistence.AttributeConverter;


@javax.persistence.Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(final Status status) {
        return status.toString();
    }

    @Override
    public Status convertToEntityAttribute(final String dbData) {
        return Status.valueOf(dbData.toUpperCase());
    }
}
