package DATA.DAO;

import MODEL.Police;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class FilesOfPoliceDAO {

    public List<Police> setFilePath(String filePath) {
        List<Police> policesOnFile;
        Path fileToProcess = Paths.get(filePath);
        try(Stream<String> dataFromFile= Files.lines(fileToProcess)){
            policesOnFile  = convertLinesToObjectsList(dataFromFile);
        }catch(IOException ex){
            policesOnFile = null ;
        }
        return policesOnFile;
    }

    private List<Police> convertLinesToObjectsList(Stream<String> dataFromFile) {
        List<Police> policesOnFile = new ArrayList<>();
        Iterator<String> it=dataFromFile.iterator();
        it.next();
        while(it.hasNext()){
            String line=it.next();
            StringTokenizer st = new StringTokenizer(line, "\",");
            Police police = new Police();
                police.setName(st.nextToken());
                police.setPolicePlateNumber(st.nextToken());
                police.setAge(Integer.parseInt(st.nextToken()));
                police.setDepartment(st.nextToken());
                police.setPhotoLink(st.nextToken());
            policesOnFile.add(police);
        }
        return policesOnFile;
    }


}



