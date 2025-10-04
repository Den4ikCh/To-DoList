package ru.chuprikov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.chuprikov.Task;
import java.util.List;

@Component
public class TaskDAO {
    final JdbcTemplate jdbcTemplate;
    @Autowired
    public TaskDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Task> index() {
        return jdbcTemplate.query("SELECT * FROM Tasks", new BeanPropertyRowMapper<>(Task.class));
    }

    public Task get(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Tasks WHERE id=?", new BeanPropertyRowMapper<>(Task.class), id);
    }

    public void insert(Task task) {
        int id = jdbcTemplate.queryForObject("SELECT MIN(t1.id + 1) as free_id FROM Tasks t1 " +
                "WHERE NOT EXISTS (SELECT 1 FROM Tasks t2 WHERE t2.id = t1.id + 1) " +
                "UNION ALL SELECT 1 WHERE NOT EXISTS (SELECT 1 FROM Tasks WHERE id = 1) LIMIT 1", Integer.class);
        jdbcTemplate.update("INSERT INTO Tasks VALUES(?, ?, ?, ?::taskdifficulty, ?)", id, task.getName(), task.getDescription(), task.getDifficulty().name(), task.isCompleted());
    }

    public void update(int id, Task task) {
        jdbcTemplate.update("UPDATE Tasks SET name=?, description=?, difficulty=?::taskdifficulty, completed=? WHERE id=?", task.getName(), task.getDescription(), task.getDifficulty().name(), task.isCompleted(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Tasks WHERE id=?", id);
    }
}
