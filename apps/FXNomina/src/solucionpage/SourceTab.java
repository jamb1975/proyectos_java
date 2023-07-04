package solucionpage;

import fxnomina.FXNomina;
import fxnomina.SolucionInfo.URL;
import util.Utils;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.regex.Pattern;

/**
 * Source code tab - Shows Syntax highlighted source if web view is available if
 * not just plain text.
 */
class SourceTab extends Tab {

    public SourceTab(URL sourceURL) {
        super(sourceURL.getName());
        String url = sourceURL.getURL();
        String ext = url.substring(url.lastIndexOf('.')).toLowerCase();
        switch (ext) {
            case ".java":
            case ".css":
            case ".fxml":
                String source = Utils.loadFile(getClass().getResource(url));
                if (FXNomina.IS_EMBEDDED || FXNomina.IS_IOS || FXNomina.IS_ANDROID || !Platform.isSupported(ConditionalFeature.WEB)) {
                    // TODO: Convert to TextFlow
                    //                    TextFlow textFlow = TextFlowBuilder.create()
                    //                            .build();
                    //
                    //                    Reader r = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(url)));
                    TextArea textArea = new TextArea(source);
                    textArea.setStyle("-fx-font-family: 'Courier New';");
                    textArea.setEditable(false);
                    setContent(textArea);
                } else {
                    String html = convertToHTML(source);
                  
                }
                break;
            case ".jpg":
            case ".png":
                ImageView imageView = new ImageView(new Image(url));
                StackPane stackPane = new StackPane(imageView);
                ScrollPane scrollPane = new ScrollPane(stackPane);
                scrollPane.setFitToHeight(true);
                scrollPane.setFitToWidth(true);
                setContent(scrollPane);
                break;
        }
        setTooltip(new Tooltip(url));
    }

    private static final Pattern JAVA_DOC_PATTERN = Pattern.compile("(^\\s+\\*$\\s)?^\\s+\\*\\s+@.*$\\s", Pattern.MULTILINE);
    private static String shCoreJs;
    private static String shBrushJScript;
    private static String shCoreDefaultCss;

    private static String convertToHTML(String source) {
        // load syntax highlighter
        if (shCoreJs == null) {
            shCoreJs = Utils.loadFile(FXNomina.class.getResource("syntaxhighlighter/shCore.js")) + ";";
        }
        if (shBrushJScript == null) {
            shBrushJScript = Utils.loadFile(FXNomina.class.getResource("syntaxhighlighter/shBrushJava.js"));
        }
        if (shCoreDefaultCss == null) {
            shCoreDefaultCss = Utils.loadFile(FXNomina.class.getResource("syntaxhighlighter/shCoreDefault.css")).replaceAll("!important", "");
        }
        // split copy right and source
        String[] parts = source.split("\\*/", 2);
        String copyRight = null;
        if (parts.length > 1) {
            copyRight = parts[0] + "*/";
            source = parts[1];
        }
        // remove JavaDoc @xxxx lines
        source = JAVA_DOC_PATTERN.matcher(source).replaceAll("");
        // escape < & >
        source = source.replaceAll("&", "&amp;");
        source = source.replaceAll("<", "&lt;");
        source = source.replaceAll(">", "&gt;");
        source = source.replaceAll("\"", "&quot;");
        source = source.replaceAll("\'", "&apos;");
        // create content
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append("    <head>\n");
        html.append("    <script type=\"text/javascript\">\n");
        html.append(shCoreJs);
        html.append('\n');
        html.append(shBrushJScript);
        html.append("    </script>\n");
        html.append("    <style>\n");
        html.append(shCoreDefaultCss);
        html.append('\n');
        html.append("        .syntaxhighlighter {\n");
        html.append("			overflow: visible;\n");
        if (FXNomina.IS_MAC) {
            html.append("			font: 12px Ayuthaya !important; line-height: 150% !important; \n");
            html.append("		}\n");
            html.append("		code { font: 12px Ayuthaya !important; line-height: 150% !important; } \n");
        } else {
            html.append("			font: 12px monospace !important; line-height: 150% !important; \n");
            html.append("		}\n");
            html.append("		code { font: 12px monospace !important; line-height: 150% !important; } \n");
        }
        html.append("		.syntaxhighlighter .preprocessor { color: #060 !important; }\n");
        html.append("		.syntaxhighlighter .comments, .syntaxhighlighter .comments a  { color: #009300 !important; }\n");
        html.append("		.syntaxhighlighter .string  { color: #555 !important; }\n");
        html.append("		.syntaxhighlighter .value  { color: blue !important; }\n");
        html.append("		.syntaxhighlighter .keyword  { color: #000080 !important; }\n");
        html.append("		.hidden { display: none; }\n");
        html.append("           .showing { display: block; }\n");
        html.append("           .button {\n");
        html.append("               font: 12px \"Consolas\", \"Bitstream Vera Sans Mono\", \"Courier New\", Courier, monospace !important;\n");
        html.append("               color: #009300 !important;\n");
        html.append("               text-decoration: underline;\n");
        html.append("               display: inline;\n");
        html.append("               cursor:pointer;\n");
        html.append("           }\n");
        html.append("        body {background-color: #f4f4f4;}\n");
        html.append("    </style>\n");
        html.append("    </head>\n");
        html.append("<body>\n");
        if (copyRight != null) {
            html.append("    <div onclick='document.getElementById(\"licenceText\").className = \"showing\";document.getElementById(\"licenseBtn\").className = \"hidden\";' id=\"licenseBtn\" class=\"button\">/* ....Show License.... */</div>\n");
            html.append("    <div id=\"licenceText\"class=\"hidden\">\n");
            html.append("    <pre class=\"brush: java; gutter: false; toolbar: false; quick-code: false;\">\n");
            html.append(copyRight);
            html.append('\n');
            html.append("    </pre>\n");
            html.append("    </div>\n");
        }
        html.append("    <pre class=\"brush: java; gutter: false; toolbar: false; quick-code: false;\">\n");
        html.append(source);
        html.append('\n');
        html.append("    </pre>\n");
        html.append("    <script type=\"text/javascript\"> SyntaxHighlighter.all(); </script>\n");
        html.append("</body>\n");
        html.append("</html>\n");

//        System.out.println("------------------------------------------------------------");
//        System.out.println(html);
//        System.out.println("------------------------------------------------------------");
        return html.toString();
    }
}
