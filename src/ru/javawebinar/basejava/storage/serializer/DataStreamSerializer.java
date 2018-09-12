package ru.javawebinar.basejava.storage.serializer;

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
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeCollection(dos, r.getSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((ExperienceSection) section).getexperiencesList(), experience -> {
                            dos.writeUTF(checkNull(experience.getHomePage().getName()));
                            dos.writeUTF(experience.getHomePage().getUrl());
                            writeCollection(dos, ((Experience) experience).getPositions(), position -> {
                                writeDate(dos, position.getDataFrom());
                                writeDate(dos, position.getDataTo());
                                dos.writeUTF(checkNull(position.getDescription()));
                                dos.writeUTF(position.getPosition());
                            });
                        });
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListSection) section).getDescriptions(), description -> {
                            dos.writeUTF(description);
                        });
                        break;
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getDescription());
                        break;
                }
            });
        }
    }

    private String checkNull(String value) {
        return (value == null) ? "" : value;
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
            readCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readCollection(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case EXPERIENCE:
            case EDUCATION:
                return new ExperienceSection(
                        readList(dis, () -> readExperience(dis)));
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

    private List<String> readListSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> listSection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            listSection.add(dis.readUTF());
        }
        return listSection;
    }

    private Experience readExperience(DataInputStream dis) throws IOException {
        return new Experience(dis.readUTF(), dis.readUTF(), readExperiencePositions(dis));
    }

    private List<Experience.ExperienceList> readExperiencePositions(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Experience.ExperienceList> experiencePositions = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            experiencePositions.add(new Experience.ExperienceList(readDate(dis), readDate(dis), dis.readUTF(), dis.readUTF()));
        }
        return experiencePositions;
    }


    private <T> List<T> readList(DataInputStream dis, ReadListItem<T> readItem) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(readItem.read());
        }
        return list;
    }

    private void readCollection(DataInputStream dis, ReadItem readItem) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readItem.read();
        }
    }

    private interface ReadListItem<T> {
        T read() throws IOException;
    }

    private interface ReadItem {
        void read() throws IOException;
    }
}
