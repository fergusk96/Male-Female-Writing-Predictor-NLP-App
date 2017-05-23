package main.java;

import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.supervised.attribute.PartitionMembership;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;

/**
 * Created by fergusk on 18/05/2017.
 */
public class readModel {
    public static void main(String[] args) throws Exception {
        // deserialize model
        PartitionMembership pm = new PartitionMembership();
        FilteredClassifier fc = new FilteredClassifier();
        RandomForest rf = new RandomForest();
        Instances inst = new Instances(new BufferedReader(new FileReader("data.arff")));
        fc.setFilter(pm);
        fc.setClassifier(rf);
        inst.setClassIndex(inst.numAttributes() - 1);
        fc.buildClassifier(inst);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rf.model"));
        oos.writeObject(fc);
        oos.flush();
        oos.close();


    }
}