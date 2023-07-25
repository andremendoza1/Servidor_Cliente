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
