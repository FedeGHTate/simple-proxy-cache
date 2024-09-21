package sharumaki.h.f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ArgsManager {
    HashMap<String, String> map = new HashMap<>();
    String[] argsNeeded = {"--p" , "--origin"};

    public ArgsManager(String[] args) {

        List<String> argList = new ArrayList<>(Arrays.asList(args));

        for (String anArg : argsNeeded) {
            int index = argList.indexOf(anArg);

            if(index != -1) {
                map.put(argList.get(index).substring(2),argList.get(index + 1));
                argList.remove(index + 1);
                argList.remove(index);
            }
        }
    }

    public boolean allArgsLoaded() {
        return argsNeeded.length != map.size();
    }

    public String getValue(String key) {
        return this.map.get(key);
    }
}
