package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        String value;
        for (ContactType type : ContactType.values()) {
            value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new ListSection(value.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        String[] values = request.getParameterValues(type.name());
                        List<Experience> list = new ArrayList<>();
                        for (int i = 0; i < values.length; i++) {
                            String index = String.valueOf(i);
                            String name = values[i];
                            String url = request.getParameter(type.name() + index + "url");
                            String[] posValues = request.getParameterValues(type.name() + index + "pos");
                            List<Experience.ExperienceList> positionList = new ArrayList<>();
                            String[] pos = request.getParameterValues(type.name() + index + "pos");
                            String[] des = request.getParameterValues(type.name() + index + "des");
                            String[] start = request.getParameterValues(type.name() + index + "start");
                            String[] end = request.getParameterValues(type.name() + index + "end");
                            for (int j = 0; j < posValues.length; j++) {
                                positionList.add(new Experience.ExperienceList(
                                        DateUtil.parse(start[j]), DateUtil.parse(end[j]), des[j], pos[j]));
                            }
                            list.add(new Experience(new Link(name, url), positionList));
                        }
                        r.addSection(type, new ExperienceSection(list));
                }
            } else r.getSections().remove(type);
        }

        if (isActionOfAdding(request)) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    public boolean isActionOfAdding(HttpServletRequest request) {
        String action = request.getParameter("action");
        return "add".equals(action);
    }
}
