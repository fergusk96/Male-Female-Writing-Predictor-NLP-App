package main.java;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.SparseInstance;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

public class main {


    public static void main(String[] args) throws Exception {
        String input = "";
        Scanner A = new Scanner(System.in);
        System.out.println("Please enter text..");
        input = A.nextLine();
        ArrayList<Attribute> attributeList = new ArrayList<>();
        for(int i = 0 ; i < 150; i++){
            attributeList.add(new Attribute("word"+Integer.toString(i)));
        }
        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement("male");
        fvClassVal.addElement("female");
        Attribute Class = new Attribute("class", fvClassVal);
        FastVector fvWekaAttributes = new FastVector(150);
        for(Attribute att: attributeList){
        fvWekaAttributes.add(att);
        }
        fvWekaAttributes.addElement(Class);
        Instances dataset = new Instances("whatever", fvWekaAttributes, 0);
        double[] attValues = findFrequenciesOfEachCommonWordInText(input);
        dataset.add(new SparseInstance(1.0, attValues));
        dataset.setClassIndex(dataset.numAttributes()-1);
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("rf.model"));
        FilteredClassifier rf = (FilteredClassifier) ois.readObject();
        ois.close();

        if(rf.classifyInstance(dataset.instance(0)) == 1.0){
            System.out.println("Male");
        }
        else{
            System.out.println("Female");
        }


  /*
        BufferedReader br = null;
        int numFolds = 10;
    //    createCSVFromString("Splitting a string and get tokens out of it, is not a very un-common task for java programmers specially working on web layer. In web layer, there are plenty of techniques for arising data from view layer to controller, and unfortunately many times we have to pass data in CSV format only () or separated based on some other separator such $,# or another character.");
    //    Convert("temp.csv", "template.arff");
        br = new BufferedReader(new FileReader("result150punctuationstop.arff"));

        Instances trainData = new Instances(br);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        br.close();
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("rf.model"));
        FilteredClassifier rf = (FilteredClassifier) ois.readObject();

        ois.close();


        rf.buildClassifier(trainData);
        Evaluation evaluation = new Evaluation(trainData);
        evaluation.evaluateModel(rf, trainData);





        System.out.println(evaluation.toClassDetailsString());
        System.out.println("Results For Class -1- ");
        System.out.println("Precision=  " + evaluation.precision(0));
        System.out.println("Recall=  " + evaluation.recall(0));
        System.out.println("F-measure=  " + evaluation.fMeasure(0));
        System.out.println("Results For Class -2- ");
        System.out.println("Precision=  " + evaluation.precision(1));
        System.out.println("Recall=  " + evaluation.recall(1));
        System.out.println("F-measure=  " + evaluation.fMeasure(1));
*/

    }

    public static double[] findFrequenciesOfEachCommonWordInText(String text){

        ArrayList<String>  resultList = new ArrayList<>();
        text = text.toLowerCase();
        text = text.replaceAll("[,.!?;:]", " $0 ");
        ArrayList<String> commonWords = CSVTools.csvFileReader("commonWords.csv");
        double[] freqs = new double[150];

        StringTokenizer st = new StringTokenizer(text);
        while (st.hasMoreTokens()) {
            resultList.add(st.nextToken().toString());
        }



        for (int j = 0; j < commonWords.size(); j++) {
            int freq = Collections.frequency(resultList, commonWords.get(j).replaceAll(",",""));
            if (freq > 0) {
                freqs[j] = (double) freq;
            } else {
                freqs[j] = 0.1;
            }

        }
        return freqs;
    }


}




