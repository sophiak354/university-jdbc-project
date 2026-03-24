package com.solvd.university.dao.impl;

import com.solvd.university.dao.SemesterDao;
import com.solvd.university.exception.DaoException;
import com.solvd.university.model.Semester;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SemesterXmlDaoImpl implements SemesterDao {

    private static final Path FILE_PATH = Path.of("src/main/resources/xml/semesters.xml");

    @Override
    public Optional<Semester> findById(int id) {
        return findAll().stream()
                .filter(semester -> semester.getSemesterId() == id)
                .findFirst();
    }

    @Override
    public List<Semester> findAll() {
        List<Semester> semesters = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();

        try (InputStream inputStream = Files.newInputStream(FILE_PATH)) {
            XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

            Semester semester = null;
            String currentElement = null;

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        currentElement = reader.getLocalName();

                        if ("semester".equals(currentElement)) {
                            semester = new Semester();
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        String text = reader.getText().trim();

                        if (text.isEmpty() || semester == null || currentElement == null) {
                            continue;
                        }

                        switch (currentElement) {
                            case "semesterId" -> semester.setSemesterId(Integer.parseInt(text));
                            case "semesterName" -> semester.setSemesterName(text);
                            case "startDate" -> semester.setStartDate(LocalDate.parse(text));
                            case "endDate" -> semester.setEndDate(LocalDate.parse(text));
                            case "academicYear" -> semester.setAcademicYear(text);
                        }
                    }
                    case XMLStreamConstants.END_ELEMENT -> {
                        if ("semester".equals(reader.getLocalName()) && semester != null) {
                            semesters.add(semester);
                            semester = null;
                        }
                        currentElement = null;
                    }
                }
            }
            reader.close();
            return semesters;
        } catch (Exception e) {
            throw new DaoException("Failed to read semesters from XML.", e);
        }
    }

    @Override
    public void save(Semester semester) {
        List<Semester> semesters = findAll();

        int nextId = semesters.stream()
                .mapToInt(Semester::getSemesterId)
                .max()
                .orElse(0) + 1;

        semester.setSemesterId(nextId);
        semesters.add(semester);

        writeAll(semesters);
    }

    @Override
    public void update(Semester updatedSemester) {
        List<Semester> semesters = findAll();

        boolean updated = false;

        for (int i = 0; i < semesters.size(); i++) {
            if (semesters.get(i).getSemesterId() == updatedSemester.getSemesterId()) {
                semesters.set(i, updatedSemester);
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new DaoException("Semester with id " + updatedSemester.getSemesterId() + " not found.");
        }

        writeAll(semesters);
    }

    @Override
    public void deleteById(int id) {
        List<Semester> semesters = findAll();

        boolean removed = semesters.removeIf(semester -> semester.getSemesterId() == id);

        if (!removed) {
            throw new DaoException("Semester with id " + id + " not found.");
        }

        writeAll(semesters);
    }

    private void writeAll(List<Semester> semesters) {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();

        try (OutputStream outputStream = Files.newOutputStream(FILE_PATH)) {
            XMLStreamWriter writer = factory.createXMLStreamWriter(outputStream, "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeCharacters("\n");
            writer.writeStartElement("semesters");
            writer.writeCharacters("\n");

            for (Semester semester : semesters) {
                writer.writeCharacters("    ");
                writer.writeStartElement("semester");
                writer.writeCharacters("\n");

                writeElement(writer, "semesterId", String.valueOf(semester.getSemesterId()));
                writeElement(writer, "semesterName", semester.getSemesterName());
                writeElement(writer, "startDate", semester.getStartDate().toString());
                writeElement(writer, "endDate", semester.getEndDate().toString());
                writeElement(writer, "academicYear", semester.getAcademicYear());

                writer.writeCharacters("    ");
                writer.writeEndElement();
                writer.writeCharacters("\n");
            }

            writer.writeEndElement();
            writer.writeCharacters("\n");
            writer.writeEndDocument();

            writer.flush();
            writer.close();

        } catch (Exception e) {
            throw new DaoException("Failed to write semesters to XML.", e);
        }
    }

    private void writeElement(XMLStreamWriter writer, String elementName, String value) throws XMLStreamException {
        writer.writeCharacters("        ");
        writer.writeStartElement(elementName);
        writer.writeCharacters(value);
        writer.writeEndElement();
        writer.writeCharacters("\n");
    }
}