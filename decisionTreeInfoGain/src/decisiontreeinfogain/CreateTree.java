/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeinfogain;

import java.util.ArrayList;

/**
 *
 * @author test
 */
public class CreateTree {

    public static int counter = 5;

    public void calculateDecisionTree(int attributeNum, String path) {
        ArrayList<ArrayList<String>> dataTable = new ArrayList<ArrayList<String>>();
        ArrayList<String> attiributeName = new ArrayList<String>();
        FileReader read = new FileReader(path);

        read.getRecords(attributeNum, dataTable, attiributeName);

        System.out.println("Data Name   " + attiributeName);

        int count = 5;
        treeCreator(dataTable, attiributeName, count);

    }

    public void treeCreator(ArrayList<ArrayList<String>> dataTable, ArrayList<String> attiributeName, int count) {
        ArrayList<ArrayList<String>> attribute = new ArrayList<ArrayList<String>>();
        attribute = FindAttribute(dataTable);

        int yes = getTotalYes(dataTable, attribute);
        int no = getTotalNo(dataTable, attribute);

        EntropyCalculator ent = new EntropyCalculator();
        double entropy = ent.Entropy(yes, no);

        if (entropy == 0) {

            for (int i = 0; i < count + 6; i++) {
                System.out.print(" ");
            }
            System.out.println("RESULT     " + dataTable.get(0).get(attiributeName.size() - 1));

            return;
        } else {
            ArrayList<ArrayList<Integer>> DecisionRate = new ArrayList<ArrayList<Integer>>();
            ArrayList<Double> allGain = new ArrayList<Double>();
            int index = findMaxGainIndex(attribute, dataTable, DecisionRate, allGain);

            for (int i = 0; i < count; i++) {
                System.out.print(" ");
            }
            System.out.print(" ***** " + attiributeName.get(index) + "\n");
            count += 5;

            ArrayList<ArrayList<ArrayList<String>>> dataPartlist = new ArrayList<ArrayList<ArrayList<String>>>();
           
            dataPartlist = parseForGain(index, dataTable, attribute);
          
            for (int i = 0; i < dataPartlist.size(); i++) {
                for (int j = 0; j < count; j++) {
                    System.out.print(" ");
                }

                System.out.print(" +++++ " + dataPartlist.get(i).get(0).get(index) + "\n");
                treeCreator(dataPartlist.get(i), attiributeName, count);
            }

        }

    }

    public ArrayList<ArrayList<String>> FindAttribute(ArrayList<ArrayList<String>> dataTable) {
        ArrayList<ArrayList<String>> attribute = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < dataTable.get(0).size(); i++) {
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(dataTable.get(0).get(i));
            attribute.add(temp);
        }

        for (int i = 0; i < dataTable.size(); i++) {
            for (int j = 0; j < dataTable.get(i).size(); j++) {
                if (!attribute.get(j).contains(dataTable.get(i).get(j))) {
                    attribute.get(j).add(dataTable.get(i).get(j));
                }
            }
        }
        return attribute;
    }

    public ArrayList<ArrayList<ArrayList<String>>> parseForGain(int colomnIndex,
            ArrayList<ArrayList<String>> dataTable,
            ArrayList<ArrayList<String>> attribute) {

        ArrayList<ArrayList<ArrayList<String>>> dataPartlist = new ArrayList<ArrayList<ArrayList<String>>>();
        //youth middle_aged senior 
        for (int k = 0; k < attribute.get(colomnIndex).size(); k++) {
            //once youth ları 
            //sonra middle aged leri
            //sonra senior ları bir listede topla
            //bunları buyuk listenin elemanlarına ayrı ayrı ekle
            ArrayList<ArrayList<String>> tempPart = new ArrayList<ArrayList<String>>();

            for (int i = 0; i < dataTable.size(); i++) {
                if (dataTable.get(i).get(colomnIndex).equals(attribute.get(colomnIndex).get(k))) {
                    tempPart.add(dataTable.get(i));
                }
            }
            dataPartlist.add(tempPart);
        }

        return dataPartlist;
    }

    public ArrayList<ArrayList<Integer>> getDecisionRate(int index, ArrayList<ArrayList<String>> dataTable, ArrayList<ArrayList<String>> attribute) {

        ArrayList<ArrayList<Integer>> DecisionRate = new ArrayList<ArrayList<Integer>>();

        for (int k = 0; k < attribute.get(index).size(); k++) {
            int yesNum = 0;
            int noNum = 0;

            //System.err.println(attribute.get(attribute.size()-1).get(0));
            ArrayList<Integer> yesNo = new ArrayList<Integer>();
            for (int i = 0; i < dataTable.size(); i++) {
                if (dataTable.get(i).get(index).equals(attribute.get(index).get(k))) {
                    //Yes kontrolü
                    if (dataTable.get(i).get(attribute.size() - 1).equals(attribute.get(attribute.size() - 1).get(0))) {
                        yesNum++;
                        //No kontrolü
                    } else if (attribute.get(attribute.size() - 1).size() >1) {
                        if (dataTable.get(i).get(attribute.size() - 1).equals(attribute.get(attribute.size() - 1).get(1))) {
                            noNum++;
                        }
                    }
                    

                }
            }

            yesNo.add(yesNum);
            yesNo.add(noNum);
            DecisionRate.add(yesNo);
        }

        return DecisionRate;
    }

    public int getTotalYes(ArrayList<ArrayList<String>> dataTable, ArrayList<ArrayList<String>> attribute) {
        int num = 0;

        for (int i = 0; i < dataTable.size(); i++) {
            if (dataTable.get(i).get(dataTable.get(0).size() - 1).equals(attribute.get(attribute.size() - 1).get(0))) {
                num++;
            }
        }
        return num;
    }

    public int getTotalNo(ArrayList<ArrayList<String>> dataTable, ArrayList<ArrayList<String>> attribute) {
        int num = 0;
        for (int i = 0; i < dataTable.size(); i++) {
            if (attribute.get(attribute.size() - 1).size() == 1) {
                num = 0;
                return num;
            } else if (dataTable.get(i).get(dataTable.get(0).size() - 1).equals(attribute.get(attribute.size() - 1).get(1))) {
                num++;
            }
        }
        return num;
    }

    public ArrayList<Integer> getPartCount(int index, ArrayList<ArrayList<String>> dataTable, ArrayList<ArrayList<String>> attribute) {

        ArrayList<Integer> PartitionNum = new ArrayList<Integer>();
        for (int k = 0; k < attribute.get(index).size(); k++) {
            int num = 0;
            for (int i = 0; i < dataTable.size(); i++) {
                if (dataTable.get(i).get(index).equals(attribute.get(index).get(k))) {
                    num++;
                }
            }
            PartitionNum.add(num);
        }
        return PartitionNum;
    }

    public int findMaxGainIndex(ArrayList<ArrayList<String>> attribute, ArrayList<ArrayList<String>> dataTable, ArrayList<ArrayList<Integer>> DecisionRate, ArrayList<Double> allGain) {
        EntropyCalculator entropyCalculator = new EntropyCalculator();
        //Gelen Dataya Ait bütün gainleri hesaplar
        for (int i = 0; i < attribute.size() - 1; i++) {
            DecisionRate = new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> PartitionNum = new ArrayList<Integer>();

            PartitionNum = getPartCount(i, dataTable, attribute);
            DecisionRate = getDecisionRate(i, dataTable, attribute);
            int yes = getTotalYes(dataTable, attribute);
            int no = getTotalNo(dataTable, attribute);

            double gain = entropyCalculator.Gain(2, DecisionRate, PartitionNum, dataTable.size() - 1, yes, no);
            allGain.add(gain);

        }

        /*System.out.println("ALL GAIN");
        for (int i = 0; i < dataTable.size(); i++) {
            System.out.println(dataTable.get(i));
        }
        
        System.out.println(allGain);*/
        double max = allGain.get(0);
        int index = 0;
        for (int i = 0; i < allGain.size(); i++) {
            if (allGain.get(i) > max) {
                max = allGain.get(i);
                index = i;
            }
        }

        return index;
    }

}
