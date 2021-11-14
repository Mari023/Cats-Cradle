package de.mari_023.android.catscradle;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

public class exportStrings {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File("F:/IdeaProjects/Cats-Cradle/app/src/main/res/values/strings.xml"));
        NodeList nodeList = document.getElementsByTagName("string");
        HashMap<String, String> strings = new HashMap<>();
        for(int x = 0, size = nodeList.getLength(); x < size; x++) {
            String id = nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue();
            String value = nodeList.item(x).getFirstChild().getNodeValue();
            strings.put(id, value);
        }
        for(int i = 0; i < 102; i++) {
            String question = strings.get("question" + i);
            String answerA = strings.get("answer" + i + "a");
            String answerB = strings.get("answer" + i + "b");
            String answerC = strings.get("answer" + i + "c");
            String answerD = strings.get("answer" + i + "d");
            System.out.println();
            System.out.println(i);
            System.out.println(question);
            System.out.println(answerA);
            System.out.println(answerB);
            System.out.println(answerC);
            System.out.println(answerD);
        }
    }
}
