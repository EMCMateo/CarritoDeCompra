package ec.edu.ups.modelo;

/**
 * Enumeración que define los posibles roles que puede tener un usuario dentro del sistema.
 */
public enum Rol {
    /**
     * Usuario con privilegios administrativos (gestiona usuarios, productos, etc.)
     */
    ADMINISTRADOR,

    /**
     * Usuario regular con permisos de cliente (realiza compras, consulta productos).
     */
    CLIENTE
}
