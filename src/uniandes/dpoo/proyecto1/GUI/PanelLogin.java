package uniandes.dpoo.proyecto1.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelLogin extends PanelAux{

    public PanelLogin(InterfazBannerPrincipal principal) {
        super(principal);
        setLayout(new GridLayout(1, 3));
        JPanel caja = new JPanel();
        caja.setLayout(new GridLayout(3,1));
        add(new JLabel());
        add(caja);
        add(new JLabel());
        JTextField usuario = new JTextField("Usuario");
        JPasswordField password = new JPasswordField("Contraseña");
        JButton botonLogin = new JButton("Login");
        botonLogin.addActionListener(principal);
        botonLogin.setActionCommand("main>LoginTry");
        caja.add(usuario, BorderLayout.CENTER);
        caja.add(password, BorderLayout.CENTER);
        caja.add(botonLogin,BorderLayout.CENTER);

    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

    }

    public void ocultar() {
        setVisible(false);
    }

    @Override
    public void mostrar(PanelImagenFondo fondo) {
        setOpaque(false);
        fondo.add(this,BorderLayout.SOUTH);
    }

    @Override
    public void reset() {

    }
}