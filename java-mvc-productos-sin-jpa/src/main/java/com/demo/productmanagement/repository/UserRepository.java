package com.demo.productmanagement.repository;

import com.demo.productmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Row mapper para User
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setNombre(rs.getString("nombre"));
            user.setEmail(rs.getString("email"));
            user.setRol(rs.getString("rol"));
            user.setPassword(rs.getString("password"));
            return user;
        };
    }

    // Listar todos los usuarios
    public List<User> findAll() {
        String sql = "SELECT * FROM usuarios ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    // Buscar usuario por ID
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, userRowMapper());
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Buscar usuario por email (para login)
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, userRowMapper());
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Guardar nuevo usuario
    public User save(User user) {
        String sql = "INSERT INTO usuarios (nombre, email, rol, password) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getRol());
            ps.setString(4, user.getPassword());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            user.setId(((Number) keys.get("id")).longValue());
        }
        return user;
    }

    // Actualizar usuario
    public boolean update(User user) {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, rol = ?, password = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                user.getNombre(),
                user.getEmail(),
                user.getRol(),
                user.getPassword(),
                user.getId());
        return rowsAffected > 0;
    }

    // Eliminar usuario
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    // ----------------------------
    // AÑADIDOS (solo lo que pediste)
    // ----------------------------

    /**
     * Buscar usuario por email y password (para validar login y obtener el User completo).
     * @param email correo
     * @param password contraseña (texto plano, acorde a tu implementación actual)
     * @return Optional<User> con resultado o vacío si no existe
     */
    public Optional<User> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email, password}, userRowMapper());
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Listar todos los usuarios excluyendo el ID pasado (útil para no mostrar al usuario logueado).
     * Si id es null, devuelve todos.
     * @param id ID a excluir
     * @return lista de usuarios sin el ID excluido
     */
    public List<User> findAllExcludingId(Long id) {
        if (id == null) {
            return findAll();
        }
        String sql = "SELECT * FROM usuarios WHERE id <> ? ORDER BY id";
        return jdbcTemplate.query(sql, new Object[]{id}, userRowMapper());
    }
}
