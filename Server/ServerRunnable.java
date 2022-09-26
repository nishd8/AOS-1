public class ServerRunnable implements Runnable{

    private int port;

    public ServerRunnable(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Server server = new Server(port);
    }
}
