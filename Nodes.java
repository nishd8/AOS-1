import  java.io.*;
import java.util.*;

public class Nodes {

    private int nodeId;
    private int port;
    private String host;
    private int sendCount;
    private Boolean isActive;
    private String[] neighbours;

    public Nodes(int nodeId, int port, String host) {
        this.nodeId = nodeId;
        this.port = port;
        this.host = host;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void incrementSendCount() {
        this.sendCount = this.sendCount+1;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(String[] neighbours) {
        this.neighbours = neighbours;
    }
}
