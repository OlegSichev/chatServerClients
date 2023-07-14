import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    static Scanner scanStr = new Scanner(System.in);
    public static final String LOCALHOST = "localhost";
    public static final int PORT = 8089;

    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket(LOCALHOST, PORT);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());

            String nameAdminServer = in.nextLine(); //Получаем имя админа сервера (сервер его отправляет первым)
            String welcome = in.nextLine();
            System.out.println(welcome);
            String answerForServer;
            AtomicBoolean isRunning = new AtomicBoolean(true);

            new Thread(() -> {
                String message;
                while (isRunning.get()) {
                    if (in.hasNextLine()) {
                        message = in.nextLine();
                        if (message.equals("-1")){
                            isRunning.set(false); //TODO Сервер выключается, а клиент выключается только после еще
                            //TODO одного сообщения (один цикл выполняется после завершения работы)
                        } else {
                            System.out.println("Сообщение от " + nameAdminServer + ": " + message + "\nНапишите ваш" +
                                    " ответ:\n");
                        }
                    }
                }
            }).start();

            while (isRunning.get()) {
                answerForServer = scanStr.nextLine();
                out.println(answerForServer);
                out.flush();
                System.out.println("Сообщение для " + nameAdminServer + " отправлено");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
