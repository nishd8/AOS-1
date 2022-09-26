import java.util.*;
import java.io.*;

public class Main {
    public static boolean checkIsMaxed(HashMap<Integer, Nodes> map, int maxPerActive){
        boolean isMaxed = false;
        for (Map.Entry<Integer, Nodes> entry : map.entrySet()) {
            Nodes value = entry.getValue();
            if(value.getSendCount() != maxPerActive){
                isMaxed = true;
                break;
            }
        }

        return isMaxed;
    }
    public static void main(String[] args) throws IOException, InterruptedException {

        int nodes = 0, maxPerActive, minPerActive, minSendDelay;
        HashMap<Integer,Nodes> nodeArray = new HashMap<Integer, Nodes>();

        File config = new File("src/config.txt");
        Scanner lineReader = new Scanner(config);
        String[] line;
        line = lineReader.nextLine().split(" ");

        nodes = Integer.parseInt(line[0]);
        minPerActive = Integer.parseInt(line[1]);
        maxPerActive = Integer.parseInt(line[2]);
        minSendDelay = Integer.parseInt(line[3]);

        lineReader.nextLine();

        for(int i=0;i<nodes;i++){
            line = lineReader.nextLine().split(" ");
            Nodes newNode = new Nodes(Integer.parseInt(line[0]),Integer.parseInt(line[2]),line[1]);
            if(i==0){
                newNode.setActive(true);
            }
            else{
                newNode.setActive(false);
            }
            nodeArray.put(Integer.parseInt(line[0]),newNode);
        }

        lineReader.nextLine();


        for(int i=0;i<nodes;i++) {
            line = lineReader.nextLine().split(" ");
            nodeArray.get(i).setNeighbours(line);
        }


        lineReader.close();


        // Default Start Case
        int currentNodeKey = 0;
        String[] neigh = nodeArray.get(currentNodeKey).getNeighbours();
        int randomNeighKey = Integer.parseInt(neigh[new Random().nextInt(neigh.length)]);
        int randMsgCount = new Random().nextInt((maxPerActive-minPerActive)+1) + minPerActive;

        int iteration =0;

        while (iteration<10){
            Thread t1 = new Thread(new ServerRunnable(nodeArray.get(randomNeighKey).getPort()));
            t1.start();


            Client client = new Client("localhost",nodeArray.get(randomNeighKey).getPort());

            if(nodeArray.get(currentNodeKey).getSendCount()<maxPerActive){
                for(int i =0; i<=randMsgCount;i++){
                    if(i==randMsgCount){
                        client.sendMessage("Over");
                        client.closeConnection();
                        t1.join();
                        Thread.sleep(minSendDelay);
                        nodeArray.get(currentNodeKey).setActive(false);
                        currentNodeKey = randomNeighKey;
                        neigh = nodeArray.get(currentNodeKey).getNeighbours();
                        randomNeighKey = Integer.parseInt(neigh[new Random().nextInt(neigh.length)]);
                        randMsgCount = new Random().nextInt((maxPerActive-minPerActive)+1) + minPerActive;
                    }
                    else {
                        client.sendMessage("Message : " + i);
                        nodeArray.get(currentNodeKey).incrementSendCount();
                    }
                }
            }
            iteration+=1;
        }



    }
}