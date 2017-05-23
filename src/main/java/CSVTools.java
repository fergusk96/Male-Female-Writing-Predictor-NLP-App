package main.java;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by fergusk on 20/05/2017.
 */
public class CSVTools {

    public CSVTools() {
    }
    public static void createCSVFromDataString(String text) {
        ArrayList<String> resultList = new ArrayList<>();
        text = text.toLowerCase();
        text = text.replaceAll("[,.!?;:]", " $0 ");
        ArrayList<String> commonWords = csvFileReader("commonWords.csv");
        Double[] freqs = new Double[150];

        StringTokenizer st = new StringTokenizer(text);
        while (st.hasMoreTokens()) {
            resultList.add(st.nextToken().toString());
        }



        for (int j = 0; j < commonWords.size(); j++) {
            int freq = Collections.frequency(resultList, commonWords.get(j));
            if (freq > 0) {
                freqs[j] = (double) freq;
            } else {
                freqs[j] = 0.1;
            }


        }
        try {
            PrintWriter pw = new PrintWriter(new File("temp.csv"));
            StringBuilder sb = new StringBuilder();
            for(int number = 0; number < freqs.length; number++){

                sb.append("word"+Integer.toString(number)+",");}
            sb.append("class");
            sb.append("\n");
            for(double freq: freqs){
                sb.append(freq+",");}
            sb.append("?");
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    public static void Convert(String sourcepath,String destpath) throws Exception
    {
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(sourcepath));
        Instances data = loader.getDataSet();

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(destpath));
        //saver.setDestination(new File("template2.arff"));
        saver.writeBatch();
    }


    public static ArrayList<String> csvFileReader(String location) {

        String csvFile = location;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<String> words = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                words.add(line);


            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }
}
