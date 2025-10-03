package com.demo.productmanagement.service;

import com.demo.productmanagement.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Obtiene todos los usuarios
     * @return Lista de usuarios
     */
    List<User> getAllUsers();

    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> getUserById(Long id);

    /**
     * Obtiene un usuario por su email (para login)
     * @param email Email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Guarda un nuevo usuario
     * @param user Usuario a guardar
     * @return Usuario guardado con ID generado
     */
    User saveUser(User user);

    /**
     * Actualiza un usuario existente
     * @param user Usuario con datos actualizados
     * @return true si fue actualizado, false en caso contrario
     */
    boolean updateUser(User user);

    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @return true si fue eliminado, false en caso contrario
     */
    boolean deleteUser(Long id);

    /**
     * Valida el login con email y password
     * @param email Email del usuario
     * @param password Password del usuario
     * @return true si las credenciales son correctas, false si no
     */
    boolean validateLogin(String email, String password);

    // --------------------------------------------------------
    // AÑADIDO: Listar usuarios excluyendo al logueado
    // --------------------------------------------------------

    /**
     * Obtiene todos los usuarios excluyendo el ID especificado
     * (útil para no mostrar al usuario logueado en la lista).
     * @param excludedId ID a excluir (puede ser null para no excluir ninguno)
     * @return Lista de usuarios sin el excluido
     */
    List<User> getAllUsersExcludingId(Long excludedId);
}
