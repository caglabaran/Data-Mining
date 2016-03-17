/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeinfogain;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author test
 */
public class DecisionTreeInfoGain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        CreateTree tree= new CreateTree();
        //Sutün sayısı ve dosya yolu
        tree.calculateDecisionTree(6,"data5.txt");
        
    }

}
