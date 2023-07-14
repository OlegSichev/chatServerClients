import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    static Scanner scanStr = new Scanner(System.in);

    public static final int PORT = 8089;

    public static void main(String[] args) {
        try {
            String nameAdminServer;
            String nameUser;

            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!\nВведите имя администратора сервера:");
            nameAdminServer = scanStr.nextLine();
            System.out.println("Имя администратора сервера: " + nameAdminServer + "\nОжидаем подключение клиента...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился!");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            Scanner in = new Scanner(clientSocket.getInputStream());

            out.println(nameAdminServer);
            out.println("Добро пожаловать в чат. Имя администратора чата: " + nameAdminServer + "\nНапишите свое имя" +
                    " для регистрации =)\nЕсли захотите выйти из чата, введите \"exit\"");
            out.flush(); //Метод для отправки сообщения. Без него сообщение не отправится

            System.out.println("Приветсивие отправлено!");

            nameUser = in.nextLine();

            String message;
            String answerForClient;

            while (true) {
                if (in.hasNextLine()) {
                    message = in.nextLine();
                    if (message.equals("exit")) {
                        out.println("-1"); //TODO Сервер выключается, а клиент продолжает работать
                        out.flush();
                        break;
                    }
                    System.out.println("Сообщение от " + nameUser + ": " + message + "\nНапишите ваш ответ:\n");
                }

                answerForClient = scanStr.nextLine();
                out.println(answerForClient);
                out.flush(); //Метод для отправки сообщения. Без него сообщение не отправится
                System.out.println("Сообщение для " + nameUser + " отправлено"); //оповещение о том, что сообщение отправлено
            }

            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
