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

        columnSize = rect_width / 12;

        // Construct HTML
        setHeader();
        setBody();
        setFooter();
    }

    private void setBody() {
        List<String> body = new ArrayList<String>();
        int tablvl = 0;
        for (int i = 0; i < rects.size(); i++) {
            int col = rects.get(i).width / columnSize;
            body.add(lineBuilder(tablvl, "<div class=\"col-md-"+col+"\"></div>"));
        }
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
            util.zipCompress("zippedTemplateLmao.zip");
            DownloadTemplate dt = new DownloadTemplate();
            File temp = new File("zippedTemplateLmao.zip");
            dt.setFile(temp.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(HyperTextBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void copyFile(File from, File to) throws IOException {
        Files.copy(from.toPath(), to.toPath());
    }

}
