package Server;

public class ServerRunnable implements Runnable{

    private int port;
    private String host;

    public ServerRunnable(String host, int port) {
        this.port = port;
        this.host = host;
    }

    @Override
    public void run() {
        Server server = new Server(host,port);
    }
}
