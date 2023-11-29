package bchk_perceptron;

import bchk_perceptron.ModelTools.History;
import bchk_perceptron.ModelTools.Model;
import bchk_perceptron.ModelTools.ModelConfiguration;
import bchk_perceptron.activationFunctions.InverseReLu;
import bchk_perceptron.activationFunctions.ReLu;
import bchk_perceptron.layers.FuzzyLayer;
import bchk_perceptron.layers.InputLayer;
import bchk_perceptron.layers.Perceptron;
import bchk_perceptron.layers.PerceptronLayer;
import bchk_perceptron.neurons.NeuronOperations;
import bchk_perceptron.preprocessingTools.DatasetTools;
import bchk_perceptron.preprocessingTools.PreprocessingTools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.module.Configuration;
import java.util.*;
import java.util.function.Function;

public class Main {


    public static void main(String[] args) {

     double[][] myset = DatasetTools.WineSet.readWineData();
     PreprocessingTools.shuffle(myset, (int) new Date().getTime());
     PreprocessingTools.normalize(myset, -1, 1);

     List<String> pam = new ArrayList<>();
     for (int i = 0; i < 3; i++) {
      pam.add(i, Integer.toString(i + 1));
     }


     //double[] res = {0.0, 0.0};


     Model model = new Model();
     ModelConfiguration mc = ModelConfiguration.prepareConfigurationForFuzzyTest(myset,pam);
     //model.createAndExecute(mc,false,res );

     List<Double> x = new ArrayList<>();
     List<Double> y = new ArrayList<>();
     List<Double> z = new ArrayList<>();
     for (int i = 1; i < 100; i +=5) {
      mc.setEpochs(i);

      Model models = new Model();

      double[] res = {0.0, 0.0};
      models.createAndExecute(mc, true, res);
      x.add((double)i);
      y.add(res[1]);
      z.add(res[0]);
     }
     System.out.println(x.size());
     System.out.println(y.size());
     for (int i = 0; i < x.size(); i++)
     {
        System.out.println(x.get(i) + " Test Loss " + y.get(i) + " Train loss "+ z.get(i));
     }




    }
}
