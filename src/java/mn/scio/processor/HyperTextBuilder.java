package mn.scio.processor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import mn.scio.renders.DownloadTemplate;
import org.opencv.core.Rect;

/**
 *
 * @author lupino
 */
public class HyperTextBuilder {

    private String templateName;
    private List<String> lines = new ArrayList<String>(); // index file
    private Path file;
    private String templateDir;

    private boolean isTagClosed = true;
    private int tabIndentLevel = 4;
    private String tab = "   ";

    public static List<Rect> rects;
    public static int rect_width = 0;
    public static int rect_height = 0;

    private int columnSize = 0;
    private int rowSize = 0;
    private int rowNum = 0;
    private GridBox[] grids;

    public HyperTextBuilder(String templateName) {
        this.templateName = templateName;

        // Template directory with unique id
        this.templateDir = "hyperTextBuilder/"
                + this.templateName + "_" + System.currentTimeMillis();
    }

    public void initTemplate() {
        // Bootstrap directory
        // ServletContext context = getClass()
        String libBootstrap = "hyperTextLib/bootstrap";
        // String libBootstrap = context.getRealPath("/WEB-INF/hyperTextLib/bootstrap");

        //InputStream boo = HyperTextBuilder.class.getClass().getClassLoader().getResourceAsStream("");
        // Create subdirectory and boostrap files
        new File(templateDir + "/css").mkdirs();
        new File(templateDir + "/js").mkdirs();
        new File(templateDir + "/img").mkdirs();
        try {
            copyFile(new File(libBootstrap + "/css/bootstrap.min.css"),
                    new File(templateDir + "/css/bootstrap.min.css"));
            copyFile(new File(libBootstrap + "/css/bootstrap-theme.min.css"),
                    new File(templateDir + "/css/bootstrap-theme.min.css"));
            copyFile(new File(libBootstrap + "/js/bootstrap.min.js"),
                    new File(templateDir + "/js/bootstrap.min.js"));
            copyFile(new File(libBootstrap + "/css/style.css"),
                    new File(templateDir + "/css/style.css"));
        } catch (IOException ex) {
            Logger.getLogger(HyperTextBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.file = Paths.get(templateDir + "/index.html");

        
        // Construct HTML
        setHeader();
        setBody();
        setFooter();
    }
    
    /**
     * I know this part of my code is FUCKING AWFUL. I just need to test how it works
     * so relax! 
     */
    private void bootstrapGrid(List<GridBox> rect, int colSize){
        
        // lame ass bulle sort
        // sort by area
        GridBox temp;
        for (int i = 0; i < rect.size(); i++) {
            for (int j = 1; j < (rect.size() - i); j++) {
                if (rect.get(j - 1).area < rect.get(j).area) {
                    temp = rect.get(j - 1);
                    rect.set(j-1, rect.get(j));
                    rect.set(j, temp);
                }
            }
        }
        for (int i = 0; i < rect.size(); i++){
            System.out.println("y: "+rect.get(i).rect_y+"("+rect.get(i).height+") | x: "+rect.get(i).rect_x+"("+rect.get(i).width+") | Area: "+ rect.get(i).area);
        }
        
        System.out.println("------------------------------------\nSIZE:"+rect.size());
        
        for (int i = 0; i < rect.size()-1; i++){
            GridBox i2 = rect.get(i);
            if(!i2.used){
                for(int j = 1; j < rect.size(); j++){
                    GridBox j2 = rect.get(j);
                    if(i2.rect_x < j2.rect_x && 
                            (i2.rect_x+i2.width) > (j2.rect_x+j2.width)){
                        if(i2.rect_y < j2.rect_y &&
                                (i2.rect_y+i2.height) > (j2.rect_y+j2.height)){
                            rect.get(i).childs.add(new GridBox(rect.get(j)));
                            rect.get(j).used = true;
                        }
                    }
                }
            }
            if(!rect.get(i).childs.isEmpty()){
                colSize = i2.width / 12;
                int row = i2.height / (colSize/2);
                if(row == 0){ row = 1; }
                int rSize = i2.height / row;
                for(int k = 0; k < rect.get(i).childs.size(); k++){
                    rect.get(i).childs.get(k).x = Math.abs(rect.get(i).rect_x - rect.get(i).childs.get(k).rect_x) / colSize;
                    rect.get(i).childs.get(k).y = Math.abs(rect.get(i).rect_y - rect.get(i).childs.get(k).rect_y) / rSize;
                }
                bootstrapGrid(rect.get(i).childs, colSize);
            }
        }
        for(int i = 0; i < rect.size(); i++){
            if(rect.get(i).used){
                rect.remove(i);
                i--;
            }
        }
        
        
        for (int i = 0; i < rect.size(); i++){
            System.out.println("y: "+rect.get(i).y+" | x: "+rect.get(i).x+" | Area: "+ rect.get(i).area);
            if(!rect.get(i).childs.isEmpty()){
                for(int e = 0; e < rect.get(i).childs.size(); e++){
                    System.out.println("    [child] y: "+rect.get(i).childs.get(e).y+" | x: "+rect.get(i).childs.get(e).x+" | Area: "+ rect.get(i).childs.get(e).area);
                }
            }    
        }
    }

    private void setBody() {
        List<String> body = new ArrayList<String>();
        List<GridBox> gridx = new ArrayList<GridBox>();
        int tablvl = 0;
        
        columnSize = rect_width / 12;
        rowNum = rect_height / (columnSize/2);
        rowSize = rect_height / rowNum;
        
        grids = new GridBox[rects.size()];
        
        for(int i = 0; i < rects.size(); i++){
            Rect temp = rects.get(i);
            int disX = temp.x / columnSize;
            int disY = temp.y / rowSize;
            gridx.add(new GridBox(disX+1, disY+1, 
                    temp.width, temp.height, temp.x, temp.y));
        }
        
        bootstrapGrid(gridx, columnSize);
    }

    private String lineBuilder(int tabLevel, String code) {
        StringBuilder line = new StringBuilder();
        for (int e = 0; e < tabIndentLevel + tabLevel; e++) {
            line.append(tab);
        }
        line.append(code);
        return line.toString();
    }
    
    private void setHeader() {
        List<String> head = Arrays.asList(
                "<!DOCTYPE html>",
                "<html>",
                "   <head>",
                "       <meta charset='UTF-8'>",
                "       <meta author='Khronometer'>",
                "       <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\"/>",
                "       <link href=\"css/bootstrap-theme.min.css\" rel=\"stylesheet\" type=\"text/css\"/>",
                "       <link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>",
                "       <script src=\"js/bootstrap.min.js\" type=\"text/javascript\"></script>",
                "       <title>" + this.templateName + "</title>",
                "   </head>",
                "   <body>",
                "       <div class=\"bdy\">",
                "           <div class=\"container\" style=\"width: 100%\">"
        );
        lines.addAll(head);
    }

    private void setFooter() {
        List<String> foot = Arrays.asList(
                "           <div>",
                "       </div>",
                "   </body>",
                "</html>"
        );
        lines.addAll(foot);
    }

    // Write all lines to index file
    public void finalizeTemplate() {
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
            Utility util = new Utility();
            util.setZipSource(templateDir);
            util.zipCompress(templateName + ".zip");
            DownloadTemplate dt = new DownloadTemplate();
            File temp = new File(templateName + ".zip");
            dt.setFile(temp.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(HyperTextBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void copyFile(File from, File to) throws IOException {
        Files.copy(from.toPath(), to.toPath());
    }

}
