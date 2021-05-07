package uniandes.dpoo.proyecto1.modelo.usuario;

import uniandes.dpoo.proyecto1.procesamiento.Banner;

public class Admin extends Usuario{
    private Banner banner;

    public Admin(String username, String contrasenha, String nombre) {
        super(username, contrasenha, nombre);
    }

    @Override
    public String getPermission() {
        return "Admin";
    }
}
