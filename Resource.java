package ua.prog.kiev.lesson2.taskThree;

import java.util.Date;

public class Resource {
    private String className;
    private String name;
    private String price;
    private String symbol;
    private String ts;
    private String type;
    private Date utctime;
    private int volume;

    public Resource() {
    }

    public Resource(String className, String name, String price, String symbol, String ts, String type, Date utctime, int volume) {
        this.className = className;
        this.name = name;
        this.price = price;
        this.symbol = symbol;
        this.ts = ts;
        this.type = type;
        this.utctime = utctime;
        this.volume = volume;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUtctime() {
        return utctime;
    }

    public void setUtctime(Date utctime) {
        this.utctime = utctime;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "\nResource{" +
                "className='" + className + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", symbol='" + symbol + '\'' +
                ", ts='" + ts + '\'' +
                ", type='" + type + '\'' +
                ", utctime=" + utctime +
                ", volume=" + volume +
                '}';
    }
}
