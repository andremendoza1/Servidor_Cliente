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
