import Client.*;
import Server.ServerRunnable;

import java.net.ServerSocket;
import java.util.*;
import java.io.*;

public class Main {

    public static boolean checkIsMaxed(HashMap<Integer, Nodes> map, int maxPerActive){
        boolean isMaxed = false;
        for (Map.Entry<Integer, Nodes> entry : map.entrySet()) {
            Nodes value = entry.getValue();
            System.out.println("Node :" + entry.getKey() + " Send Count : " + value.getSendCount());
            if(value.getSendCount() < maxPerActive){
                isMaxed = true;
            }
        }

        return isMaxed;
    }

    public static Nodes findServerNode(HashMap<Integer, Nodes> map, String serverHost) {
        Nodes serverNode = new Nodes();
        for (Map.Entry<Integer, Nodes> entry : map.entrySet()) {
            if(entry.getValue().getHost().equals(serverHost)){
                serverNode = entry.getValue();
                break;
            }
        }
        return serverNode;
    }

    public  static  HashMap<Integer,Client> initializeOutputChannel(HashMap<Integer,Nodes> nodesHashMap, String serverHost){
        Nodes host = findServerNode(nodesHashMap,serverHost);
        String [] neighbours = host.getNeighbours();
        HashMap<Integer,Client> neighbourChannels = new HashMap<>();
        for(int i =0; i<neighbours.length;i++){
            neighbourChannels.put(Integer.parseInt(neighbours[i]),
                    new Client(nodesHashMap.get(Integer.parseInt(neighbours[i])).getHost()+".utdallas.edu",nodesHashMap.get(Integer.parseInt(neighbours[i])).getPort()));
        }
        return neighbourChannels;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        int nodes = 0, maxPerActive, minPerActive, minSendDelay;
        String serverHost = args[0];

        HashMap<Integer,Nodes> nodeArray = new HashMap<Integer, Nodes>();
        HashMap<Integer,Client> neighbourChannels;


        File config = new File("config.txt");
        Scanner lineReader = new Scanner(config);
        String[] line;
        line = lineReader.nextLine().split(" ");

        nodes = Integer.parseInt(line[0]);
        minPerActive = Integer.parseInt(line[1]);
        maxPerActive = Integer.parseInt(line[2]);
        minSendDelay = Integer.parseInt(line[3]);

        for(int i=0;i<nodes;i++){
            line = lineReader.nextLine().split("#")[0].split(" ");
            Nodes newNode = new Nodes(Integer.parseInt(line[0]),Integer.parseInt(line[2]),line[1]);
            if(i==0){
                newNode.setActive(true);
            }
            else{
                newNode.setActive(false);
            }
            nodeArray.put(Integer.parseInt(line[0]),newNode);
        }

        for(int i=0;i<nodes;i++) {
            line = lineReader.nextLine().split("#")[0].split(" ");
            nodeArray.get(i).setNeighbours(line);
        }


        lineReader.close();
        neighbourChannels = initializeOutputChannel(nodeArray,serverHost);

        // Default Start Case
        int currentNodeKey = 0;
        String[] neigh = nodeArray.get(currentNodeKey).getNeighbours();
        int randomNeighKey = Integer.parseInt(neigh[new Random().nextInt(neigh.length)]);
        int randMsgCount = new Random().nextInt((maxPerActive-minPerActive)+1) + minPerActive;

        Nodes serverNode = findServerNode(nodeArray,serverHost);
        Thread t = new Thread(new ServerRunnable(serverNode.getHost()+".utdallas.edu",serverNode.getPort()));
        t.start();


        Client client = null;

        while (checkIsMaxed(nodeArray,maxPerActive)){
            if(nodeArray.get(currentNodeKey).getSendCount()<=maxPerActive){

                Thread.sleep(minSendDelay);
                client = neighbourChannels.get(randomNeighKey);
                for(int i =0; i<=randMsgCount;i++){
                    if(i==randMsgCount){
                        nodeArray.get(currentNodeKey).setActive(false);
                        currentNodeKey = randomNeighKey;
                        neigh = nodeArray.get(currentNodeKey).getNeighbours();
                        randomNeighKey = Integer.parseInt(neigh[new Random().nextInt(neigh.length)]);
                        randMsgCount = new Random().nextInt((maxPerActive-minPerActive)+1) + minPerActive;
                        if(randMsgCount + nodeArray.get(currentNodeKey).getSendCount() >= maxPerActive){
                            randMsgCount =  maxPerActive - nodeArray.get(currentNodeKey).getSendCount();
                        }
                        break;
                    }
                    else {
                        client.sendMessage("Message from Node : " + currentNodeKey + " to Node " + randomNeighKey );
                        nodeArray.get(currentNodeKey).incrementSendCount();
                    }
                }
            }
        }

    }
}