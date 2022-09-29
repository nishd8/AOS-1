import Client.*;
import Server.ServerRunnable;

import java.net.ServerSocket;
import java.util.*;
import java.io.*;

public class Main {
    public static void initializeInputChannels(HashMap<Integer,Nodes> nodesHashMap, HashMap<Integer,Thread> nodeInputChannels) throws InterruptedException {
        for(Map.Entry<Integer,Nodes> entry : nodesHashMap.entrySet()){
            Nodes value = entry.getValue();
            System.out.println("Creating Input Channel for Node : " + value.getNodeId());
            Thread t = new Thread(new ServerRunnable(value.getHost()+".utdallas.edu",value.getPort()));
            t.start();
            nodeInputChannels.put(value.getNodeId(),t);
            Thread.sleep(2000);
        }
    }
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
    public  static  void initializeOutputChannel(HashMap<Integer,Nodes> nodesHashMap, HashMap<Integer,HashMap<Integer,Client>> nodeOutput){
        for(Map.Entry<Integer,Nodes> entry : nodesHashMap.entrySet()){
            Nodes value = entry.getValue();
            System.out.println("Creating Output Channels for Node : " + value.getNodeId());
            String [] neighbours = value.getNeighbours();
            HashMap<Integer,Client> neighbourChannels = new HashMap<>();
            for(int i =0; i<neighbours.length;i++){
                neighbourChannels.put(Integer.parseInt(neighbours[i]),
                        new Client(nodesHashMap.get(Integer.parseInt(neighbours[i])).getHost()+".utdallas.edu",nodesHashMap.get(Integer.parseInt(neighbours[i])).getPort()));
            }
            nodeOutput.put(value.getNodeId(),neighbourChannels);
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {

        int nodes = 0, maxPerActive, minPerActive, minSendDelay;
        HashMap<Integer,Nodes> nodeArray = new HashMap<Integer, Nodes>();
        HashMap<Integer, Thread> nodeInputChannel = new HashMap<Integer,Thread>();
        HashMap<Integer,HashMap<Integer,Client>> nodeOutputChannels = new HashMap<Integer,HashMap<Integer,Client>>();

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

        initializeInputChannels(nodeArray,nodeInputChannel);
        System.out.println();
        initializeOutputChannel(nodeArray,nodeOutputChannels);




        // Default Start Case
        int currentNodeKey = 0;
        String[] neigh = nodeArray.get(currentNodeKey).getNeighbours();
        int randomNeighKey = Integer.parseInt(neigh[new Random().nextInt(neigh.length)]);
        int randMsgCount = new Random().nextInt((maxPerActive-minPerActive)+1) + minPerActive;



          Client client = null;

        while (checkIsMaxed(nodeArray,maxPerActive)){
            if(nodeArray.get(currentNodeKey).getSendCount()<=maxPerActive){

            Thread.sleep(minSendDelay);
            client = nodeOutputChannels.get(currentNodeKey).get(randomNeighKey);
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

        for(Map.Entry<Integer,HashMap<Integer,Client>> entry : nodeOutputChannels.entrySet()){
            HashMap<Integer,Client> value = entry.getValue();
            for(Map.Entry<Integer,Client> entry2 : value.entrySet()){
                entry2.getValue().sendMessage("Over");
                entry2.getValue().closeConnection();
            }
        }
    }
}