import enams.PropertyTypeName;
import enams.ReturnStatus;
import entities.FoundProperty;
import entities.PropertyType;
import entities.User;
import repository.FoundPropertyRepository;
import java.repository.PropertyTypeRepository;
import java.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;


public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        FoundProperty foundProperty = new FoundProperty();
        PropertyTypeName propertyTypeName = PropertyTypeName.ELECTRONICS;
        ReturnStatus returnStatus = ReturnStatus.NOT_RETURNED;
        PropertyTypeRepository propertyTypeRepository = new PropertyTypeRepository();
        PropertyType propertyType = propertyTypeRepository.getByName(propertyTypeName);
        foundProperty.setPropertyType(propertyType);
        foundProperty.setPlaceOfFinding("school");
        foundProperty.setReturnStatus(returnStatus);
        foundProperty.setUser(userRepository.getById(1));
        foundProperty.setDescription("green");
        foundProperty.setDateOfFinding(LocalDate.now());
        foundProperty.setTimeOfFinding(LocalTime.now());
        FoundPropertyRepository fpRepository = new FoundPropertyRepository();
        fpRepository.save(foundProperty);
        var foundProperties = fpRepository.getAll();
        System.out.println(foundProperties.toArray().length);
        for (FoundProperty p : foundProperties) {
            System.out.println(p);
        }
    }
}