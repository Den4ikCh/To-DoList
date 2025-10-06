package ru.chuprikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chuprikov.Task;
import ru.chuprikov.dao.TaskDAO;

@Controller
@RequestMapping("/task")
public class TaskController {
    final TaskDAO taskDAO;

    @Autowired
    public TaskController(TaskDAO taskManager) {
        this.taskDAO = taskManager;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("tasks", taskDAO.index());
        return "/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("task", taskDAO.get(id));
        return "/show";
    }

    @GetMapping("/new")
    public String newTask(@ModelAttribute("task") Task task) {
        return "/new";
    }

    @PostMapping
    public String create(@ModelAttribute("task") Task task) {
        taskDAO.insert(task);
        return "redirect:/task";
    }

    @GetMapping("/{id}/update")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskDAO.get(id));
        return "/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("task") Task task, @PathVariable("id") int id) {
        taskDAO.update(id, task);
        return "redirect:/task";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        taskDAO.delete(id);
        return "redirect:/task";
    }
}
