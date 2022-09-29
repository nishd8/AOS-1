import java.util.ArrayList;

public class LocalSnapshot {
    private int nodeId;
    private ArrayList<String> sentMessages;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public ArrayList<String> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(ArrayList<String> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public LocalSnapshot(int nodeId, ArrayList<String> sentMessages) {
        this.nodeId = nodeId;
        this.sentMessages = sentMessages;
    }
}
