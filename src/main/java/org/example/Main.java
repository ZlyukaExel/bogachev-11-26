package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Выберите файл для форматирования: ");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            List<String> paragraphs = ReadFile(file.getAbsolutePath());
            System.out.print(file.getAbsolutePath() +
                    "\nМаксимальная ширина текста (в символах): ");
            int width = sc.nextInt();

            System.out.println(FormatText(paragraphs, width));
        }
        else {
            System.out.println("Файл не был выбран!");
        }
    }

    private static String FormatText(List<String> paragraphs, int width) {
        StringBuilder result = new StringBuilder();

        for(String paragraph : paragraphs){
            result.append(FormatParagraph(paragraph, width));
        }

        return result.toString();
    }

    public static String FormatParagraph(String paragraph, int width){
        StringBuilder result = new StringBuilder("      ");
        int lineWidth = 6;
        String[] words = paragraph.split(" ");
        for(String word : words){
            int len = word.length();
            if (lineWidth + len <= width){
                lineWidth += len + 1;
                result.append(word).append(" ");
            }
            else {
                result.append("\n").append(word).append(" ");
                lineWidth = len + 1;
            }
        }
        return result + "\n";
    }

    public static List<String> ReadFile(String directory) {
        List<String> output = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(directory));
            String line;
            while ((line = br.readLine()) != null) {
                output.add(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }
}