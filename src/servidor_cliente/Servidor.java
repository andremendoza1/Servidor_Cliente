package servidor_cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor extends JFrame {

    private JTextArea chatArea;
    private JTextField messageField;
    private List<PrintWriter> listaSalidaClientes;
    private JComboBox<String> emojiComboBox;

    private List<String> emoticones = new ArrayList<>();

    public Servidor() {
        setTitle("Servidor Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField();

      
        emoticones.add("\uD83D\uDE01"); // Emoticono sonriente
        emoticones.add("\uD83D\uDE00"); // Emoticono feliz
        emoticones.add("\uD83D\uDE02"); // Emoticono risueño
        emoticones.add("\uD83D\uDE03"); // Emoticono sonrisa grande
        emoticones.add("\uD83D\uDE04"); // Emoticono sonrisa abierta
        emoticones.add("\uD83D\uDE05"); // Emoticono sonrisa con lágrimas
        emoticones.add("\uD83D\uDE06"); // Emoticono sonrisa traviesa
        emoticones.add("\uD83D\uDE09"); // Emoticono guiño y sonrisa
        emoticones.add("\uD83D\uDE0A"); // Emoticono sonrisa coqueta
        emoticones.add("\uD83D\uDE0D"); // Emoticono sonrisa con corazones
        emoticones.add("\uD83D\uDC94"); // Corazón rojo
emoticones.add("\uD83D\uDC95"); // Corazón brillante
emoticones.add("\uD83D\uDC96"); // Corazón resplandeciente
emoticones.add("\uD83D\uDC97"); // Corazón con dos corazones
emoticones.add("\uD83D\uDC98"); // Corazón con flecha
emoticones.add("\uD83D\uDC99"); // Corazón con lazo
emoticones.add("\uD83D\uDC9A"); // Corazón azul
emoticones.add("\uD83D\uDC9B"); // Corazón verde
emoticones.add("\uD83D\uDC9C"); // Corazón amarillo
emoticones.add("\uD83D\uDC9D"); // Corazón morado
emoticones.add("\uD83C\uDDF5\uD83C\uDDF7"); // Bandera de Estados Unidos
emoticones.add("\uD83C\uDDEB\uD83C\uDDF7"); // Bandera de España
emoticones.add("\uD83C\uDDEC\uD83C\uDDE7"); // Bandera de Francia
emoticones.add("\uD83C\uDDE9\uD83C\uDDEA"); // Bandera de Alemania
emoticones.add("\uD83C\uDDEE\uD83C\uDDF9"); // Bandera de Italia
emoticones.add("\uD83C\uDDEE\uD83C\uDDEA"); // Bandera de Reino Unido
emoticones.add("\uD83C\uDDE8\uD83C\uDDE6"); // Bandera de Brasil
emoticones.add("\uD83C\uDDFA\uD83C\uDDF8"); // Bandera de Canadá
emoticones.add("\uD83C\uDDF8\uD83C\uDDEE"); // Bandera de Australia
emoticones.add("\uD83C\uDDE9\uD83C\uDDEA"); // Bandera de Argentina
emoticones.add("\uD83D\uDC68"); // Hombre
emoticones.add("\uD83D\uDC69"); // Mujer
emoticones.add("\uD83D\uDC66"); // Niño
emoticones.add("\uD83D\uDC67"); // Niña
emoticones.add("\uD83D\uDC74"); // Hombre mayor
emoticones.add("\uD83D\uDC75"); // Mujer mayor
emoticones.add("\uD83D\uDC76"); // Hombre con esmoquin
emoticones.add("\uD83D\uDC77"); // Mujer con vestido de noche
emoticones.add("\uD83E\uDD70"); // Persona con barba
emoticones.add("\uD83D\uDC68\uD83C\uDFFB"); // Hombre: tono de piel claro
emoticones.add("\uD83C\uDF54"); // Torta
emoticones.add("\uD83C\uDF5F"); // Cupcake
emoticones.add("\uD83C\uDF66"); // Helado
emoticones.add("\uD83C\uDF6C"); // Palomitas de maíz
emoticones.add("\uD83C\uDF72"); // Frutas tropicales
emoticones.add("\uD83C\uDF70"); // Sandía
emoticones.add("\uD83C\uDF5E"); // Dona
emoticones.add("\uD83C\uDF61"); // Manzana
emoticones.add("\uD83C\uDF63"); // Naranja
emoticones.add("\uD83C\uDF68"); // Helado de cono
emoticones.add("\uD83D\uDC36"); // Perro
emoticones.add("\uD83D\uDC31"); // Gato
emoticones.add("\uD83D\uDC2E"); // Rana
emoticones.add("\uD83D\uDC3A"); // Elefante
emoticones.add("\uD83D\uDC39"); // Tigre
emoticones.add("\uD83D\uDC10"); // Mono
emoticones.add("\uD83D\uDC30"); // Canguro
emoticones.add("\uD83D\uDC22"); // Tortuga
emoticones.add("\uD83D\uDC35"); // Pez
emoticones.add("\uD83D\uDC23"); // Ratón

        // Agrega más emoticones aquí...

        emojiComboBox = new JComboBox<>(emoticones.toArray(new String[0]));
        emojiComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emoji = (String) emojiComboBox.getSelectedItem();
                messageField.setText(messageField.getText() + emoji);
            }
        });

        JButton sendButton = new JButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensajeServidor("Servidor dice: " + messageField.getText() + "\n");
                messageField.setText("");
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.add(messageField);
        inputPanel.add(emojiComboBox);
        inputPanel.add(sendButton);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        listaSalidaClientes = new ArrayList<>();

       setSize(400, 400);// Ajustar el tamaño del JFrame automáticamente
        setLocationRelativeTo(null); // Centrar el JFrame en la pantalla
        setVisible(true);

        new Thread(this::iniciarServidor).start();
    }

    private void iniciarServidor() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Puerto del servidor
            chatArea.append("Servidor esperando conexiones...\n");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                chatArea.append("Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress() + "\n");

                BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                PrintWriter salidaCliente = new PrintWriter(clienteSocket.getOutputStream(), true);
                listaSalidaClientes.add(salidaCliente);

                String nombreCliente = entradaCliente.readLine(); // Leer el nombre del cliente
                chatArea.append(nombreCliente + " se ha unido al chat.\n");

                String mensajeEntrada;
                while ((mensajeEntrada = entradaCliente.readLine()) != null) {
                    final String mensajeFinal = nombreCliente + " dice: " + mensajeEntrada + "\n";
                    chatArea.append(mensajeFinal);
                    enviarMensajeTodos(mensajeFinal, salidaCliente);
                }

                chatArea.append(nombreCliente + " se ha desconectado.\n");
                listaSalidaClientes.remove(salidaCliente);
                clienteSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensajeTodos(String mensaje, PrintWriter origen) {
        for (PrintWriter salidaCliente : listaSalidaClientes) {
            if (salidaCliente != origen) {
                salidaCliente.println(mensaje);
            }
        }
    }

    private void enviarMensajeServidor(String mensaje) {
        chatArea.append(mensaje);
        enviarMensajeTodos(mensaje, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Servidor());
    }
}
