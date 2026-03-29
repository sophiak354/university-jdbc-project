package com.solvd.university.dao.impl;

import com.solvd.university.dao.InstructorDao;
import com.solvd.university.model.Instructor;
import com.solvd.university.model.Instructors;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstructorDaoImpl implements InstructorDao {
    private static final String FILE_PATH = "src/main/resources/xml/instructors.xml";
    private static final String XSD_PATH = "src/main/resources/xml/instructors.xsd";

    @Override
    public Optional<Instructor> findById(int id) {
        return readFromFile().getInstructors().stream()
                .filter(instructor -> instructor.getInstructorId() == id)
                .findFirst();
    }

    @Override
    public List<Instructor> findAll() {
        return new ArrayList<>(readFromFile().getInstructors());
    }

    @Override
    public void save(Instructor instructor) {
        Instructors instructors = readFromFile();
        instructors.getInstructors().add(instructor);
        writeToFile(instructors);
    }

    @Override
    public void update(Instructor updatedInstructor) {
        Instructors instructors = readFromFile();
        List<Instructor> instructorList = instructors.getInstructors();
        for (int i = 0; i < instructorList.size(); i++) {
            if (instructorList.get(i).getInstructorId() == updatedInstructor.getInstructorId()) {
                instructorList.set(i, updatedInstructor);
                writeToFile(instructors);
                return;
            }
        }
        throw new RuntimeException("Instructor with id " + updatedInstructor.getInstructorId() + " not found.");
    }

    @Override
    public void deleteById(int id) {
        Instructors instructors = readFromFile();
        boolean removed = instructors.getInstructors()
                .removeIf(instructor -> instructor.getInstructorId() == id);
        if (!removed) {
            throw new RuntimeException("Instructor with id " + id + " not found.");
        }
        writeToFile(instructors);
    }

    private Instructors readFromFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(Instructors.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(getSchemaFile());
            unmarshaller.setSchema(schema);

            File file = getFile();
            if (!file.exists() || file.length() == 0) {
                return new Instructors(new ArrayList<>());
            }
            return (Instructors) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to read instructors from XML.", e);
        } catch (SAXException e) {
            throw new RuntimeException("Failed to load XSD schema.", e);
        }
    }

    private void writeToFile(Instructors instructors) {
        try {
            JAXBContext context = JAXBContext.newInstance(Instructors.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = getFile();
            marshaller.marshal(instructors, file);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to write instructors to XML.", e);
        }
    }

    private File getFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + FILE_PATH);
        }

        return file;
    }

    private File getSchemaFile() {
        File file = new File(XSD_PATH);

        if (!file.exists()) {
            throw new RuntimeException("Schema file not found: " + XSD_PATH);
        }
        return file;
    }
}
