import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        System.out.println("Server started at port 5023");
        System.out.println("all the logs will get created here  ");

        try {
//              STAGE 1 : SETTING UP THE PORT
            ServerSocket serversocket = new ServerSocket(5023);
            serversocket.setReuseAddress(true);
            //pizza deklivery port can accept multiple requests
            while (true) {
                Socket clientcall = serversocket.accept();
                System.out.println("Server has accepted the connection at this point ");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientcall.getInputStream()));
                PrintWriter out = new PrintWriter(clientcall.getOutputStream(), false);

                String requestcall = in.readLine();
                String responsecall;
                if (requestcall == null && requestcall.isEmpty()) {
                    responsecall = "HTTP/1.1 400 Bad Request\r\nContent-Length: 0\r\n\r\n";
                }
                else {
                    String reqParts[] = requestcall.split(" ");
                    if (reqParts.length < 2) {
                        responsecall = "HTTP/1.1 400 Bad Request\r\nContent-Length: 0\r\n\r\n";
                    } else {
//                    responsecall="HTTP/1.1 400 Bad Request\r\nContent-Length: 0\r\n\r\n";
                        //NOW EXTRACT THE MESSAGE
                        String breakup = reqParts[0];
                        String msgneeded = reqParts[1].split("//?")[0];

                        System.out.println("the orriginal messge is  :" + breakup);
                        System.out.println("the message that is needed :" + msgneeded);


                        if (breakup.equals("/")) { //if the client comtains "/" money or not thrn only deliver him the piszza
                            responsecall = "HTTP/1.1 200 OK\r\nContent-Length: 0\r\n\r\n";
                        } else {
                            responsecall = "HTTP/1.1 400 NOT FOUND\r\nContent-Length: 0\r\n\r\n";
                        }
                    }


                }
                System.out.println("Semnding response for the message that wa requested : "+responsecall.trim());
                out.print(responsecall);
                out.flush(); //message has been sent immediately

                in.close();
                out.close();
                clientcall.close();
                //all pconnection has been derailed signing off
                System.out.println("connection has been terminated for this call ");
            }
        } catch (IOException e) {
            System.out.println("IOEXCEPTION " + e.getMessage());
        }
    }

}