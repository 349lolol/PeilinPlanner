/**
 * [Assets.java]
 * process assets
 * @author Perry Xu & Patrick Wei
 * @version 1.1
 * 01/09/24
 */

package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Assets {
    private final Map<String, byte[]> assets;

    public Assets() throws IOException {
        this.assets = new HashMap<>();

        List<Path> paths = Files.walk(Paths.get("frontend"))
            .filter(Files::isRegularFile)
            .collect(Collectors.toList());


        List<String> pathNames = paths.stream()
            .map(path -> path.toString())
            .map(pathString -> pathString.substring(pathString.indexOf("\\")))
            .map(pathString -> pathString.replace("\\", "/"))
            .collect(Collectors.toList());
        
        List<byte[]> pathContent = paths.stream()
            .map(path -> {
                try {
                    return Files.readAllBytes(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .collect(Collectors.toList());

        for (int i = 0; i < pathNames.size(); i++) {
            this.assets.put("/frontend" + pathNames.get(i), pathContent.get(i));
        }
        
        System.out.println(assets.keySet());
    }

    public Map<String, byte[]> getAssets() {
        return this.assets;
    }
}
