package uniandes.dpoo.proyecto1.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelLogin extends PanelAux implements ActionListener {
    private JTextField userText;
    private JTextField passwordText;

    public PanelLogin(InterfazBannerPrincipal principal) {
        super(principal);
        setLayout(new GridLayout(1, 3));
        JPanel caja = new JPanel();
        caja.setLayout(new GridLayout(3, 1));
        add(new JLabel());
        add(caja);
        add(new JLabel());
        JTextField usuario = new JTextField("Usuario");
        JPasswordField password = new JPasswordField("Contraseña");
        JButton botonLogin = new JButton("Login");
        botonLogin.addActionListener(this);
        botonLogin.setActionCommand("main>LoginTry");
        caja.add(usuario, BorderLayout.CENTER);
        caja.add(password, BorderLayout.CENTER);
        caja.add(botonLogin, BorderLayout.CENTER);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        principal.mostrarPrincipal(principal.getBanner().authenticate(userText.getText(), passwordText.getText()));
    }
}