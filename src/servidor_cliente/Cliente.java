package servidor_cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends JFrame {

    private JTextArea chatArea;
    private JTextField messageField;
    private PrintWriter salidaServidor;
    private String nombreCliente;
    private JComboBox<String> emojiComboBox;

    private List<String> emoticones = new ArrayList<>();

    public Cliente() {
        setTitle("Cliente Chat");
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
                String message = messageField.getText();
                sendMessage(message);
                messageField.setText("");
                chatArea.append("Tú dices: " + message + "\n"); // Mostrar los mensajes enviados por el cliente en el chat del cliente
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        JPanel emojiPanel = new JPanel(new BorderLayout());
        emojiPanel.add(emojiComboBox, BorderLayout.WEST);
        emojiPanel.add(sendButton, BorderLayout.EAST);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(emojiPanel, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        setSize(400, 400);
        setVisible(true);
        setLocationRelativeTo(null);

        nombreCliente = JOptionPane.showInputDialog("Ingresa tu nombre: ");
        chatArea.append("Te has unido al chat como " + nombreCliente + ".\n");

        conectarAlServidor();
    }

    private void conectarAlServidor() {
        String serverAddress = "172.16.20.100"; // Dirección IP del servidor
        int serverPort = 12345; // Puerto del servidor
        try {
            Socket clienteSocket = new Socket(serverAddress, serverPort);
            chatArea.append("Conectado al servidor.\n");

            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            salidaServidor = new PrintWriter(clienteSocket.getOutputStream(), true);

            salidaServidor.println(nombreCliente);

            Thread receiveThread = new Thread(() -> {
                try {
                    String mensajeEntrada;
                    while ((mensajeEntrada = entradaServidor.readLine()) != null) {
                        if (mensajeEntrada.startsWith("Servidor dice:")) {
                            chatArea.append(mensajeEntrada + "\n");
                        } else {
                            chatArea.append(mensajeEntrada + "\n"); // Mostrar los mensajes enviados por el cliente en el chat del cliente
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.setDaemon(true);
            receiveThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        salidaServidor.println(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cliente());
    }
}
