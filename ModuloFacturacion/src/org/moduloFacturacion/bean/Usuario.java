package org.moduloFacturacion.bean;

public class Usuario {
    int UsuarioId;
    String usuarioNombre;
    String usuarioPassword;
    String TipoUsuario;

    public Usuario(int UsuarioId, String usuarioNombre, String usuarioPassword, String TipoUsuario) {
        this.UsuarioId = UsuarioId;
        this.usuarioNombre = usuarioNombre;
        this.usuarioPassword = usuarioPassword;
        this.TipoUsuario = TipoUsuario;
    }

    public Usuario() {
    }
    

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int UsuarioId) {
        this.UsuarioId = UsuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioPassword() {
        return usuarioPassword;
    }

    public void setUsuarioPassword(String usuarioPassword) {
        this.usuarioPassword = usuarioPassword;
    }

    public String getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(String TipoUsuario) {
        this.TipoUsuario = TipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" + "UsuarioId=" + UsuarioId + ", usuarioNombre=" + usuarioNombre + ", usuarioPassword=" + usuarioPassword + ", TipoUsuario=" + TipoUsuario + '}';
    }

}