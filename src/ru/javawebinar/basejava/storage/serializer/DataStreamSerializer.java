package ru.javawebinar.basejava.storage.serializer;

import org.omg.CORBA.Object;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements ReaderWriterObject {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(dos, contacts.entrySet(),
                    new WriterItem() {
                        @Override
                        public void write(Object o) throws IOException {
                            dos.writeUTF(o.getKey().name());
                            dos.writeUTF(o.getValue());
                        }

                    });
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case EXPERIENCE:
                    case EDUCATION:
                        dos.writeInt(((ExperienceSection) section).getexperiencesList().size());
                        for (Experience experience : ((ExperienceSection) section).getexperiencesList()) {
                            writeDate(dos, experience.getDataFrom());
                            writeDate(dos, experience.getDataTo());
                            dos.writeUTF(checkNull(experience.getDescription()));
                            dos.writeUTF(experience.getPosition());
                            dos.writeUTF(experience.getHomePage().getUrl());
                            dos.writeUTF(checkNull(experience.getHomePage().getName()));
                        }
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        dos.writeInt(((ListSection) section).getDescriptions().size());
                        for (String description : ((ListSection) section).getDescriptions()) {
                            dos.writeUTF(description);
                        }
                        break;
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getDescription());
                        break;
                }
            }
        }
    }

    private String checkNull(String value) {
        return (value == null) ? " " : value;
    }

    private void writeDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
        dos.writeInt(ld.getDayOfMonth());
    }

    private interface WriterItem<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, WriterItem<T> writeItem) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writeItem.write(item);
        }
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSection = dis.readInt();
            for (int i = 0; i < sizeSection; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            }
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case EXPERIENCE:
            case EDUCATION:
                return new ExperienceSection(readExperienceList(dis));
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readListSection(dis));
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            default:
                throw new IOException();
        }
    }

    private List<Experience> readExperienceList(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Experience> experiencesList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            experiencesList.add(readExperience(dis));
        }
        return experiencesList;
    }

    private List<String> readListSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> listSection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            listSection.add(dis.readUTF());
        }
        return listSection;
    }

    private Experience readExperience(DataInputStream dis) throws IOException {
        return new Experience(readDate(dis), readDate(dis), dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF());
    }
}
