package ua.prog.kiev.lesson2.taskThree;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

public class Resources {
    private List<Resource> resources = new ArrayList<>();

    public Resources(List<Resource> resources) {
        this.resources = resources;
    }

    public Resources() {
    }

    public void addResource(Resource newResource){
        resources.add(newResource);
    }

    @Override
    public String toString() {
        return "Resources{" +
                "resources=" + resources +
                '}';
    }
}
