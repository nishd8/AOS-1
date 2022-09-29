public class MarkerMessage {
    private int sentBy;
    private int sentDo;
    private int type;

    public int getSentBy() {
        return sentBy;
    }

    public void setSentBy(int sentBy) {
        this.sentBy = sentBy;
    }

    public int getSentDo() {
        return sentDo;
    }

    public void setSentDo(int sentDo) {
        this.sentDo = sentDo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MarkerMessage(int sentBy, int sentDo, int type) {
        this.sentBy = sentBy;
        this.sentDo = sentDo;
        this.type = type;
    }
}
