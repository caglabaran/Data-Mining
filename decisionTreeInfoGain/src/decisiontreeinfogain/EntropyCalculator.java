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
public class EntropyCalculator {
    
    //Information Gain hesaplar
    public double Gain(int decisionResultNum, ArrayList<ArrayList<Integer>> DecisionRate,ArrayList<Integer> PartitionNum,int totalData,int totalYes,int totalNo)
    {
        //Info(D)-Info age (D)
        double infoD=Entropy(totalYes,totalNo);
        double infoAtt=Info(DecisionRate, PartitionNum, totalData);
        
        double gain=infoD-infoAtt;
        return gain;
    }
    
    //Entropy Hesaplar
    public double Entropy(double num1,double num2)
    {
        double sum=0;
        if(num1==0 || num2==0)
        {
            return 0;
        }
        
        sum=(-(num1/(num1+num2)))*(Math.log((num1/(num1+num2)))/Math.log(2)) + (-(num2/(num1+num2)))*(Math.log((num2/(num1+num2)))/Math.log(2));;
        
        //System.out.println("num1  " + num1 + "  num2  " + num2);
        //System.out.println("Entropy "+ sum);
        return sum;
    }
    
    //decisionResultNum yes ve no yani 2
    //DecisionRate her degiskene gore kac tane yes no oldugu youth 2,3 2 yes 3 no
    //PartitionNum icinde mesela age icinde bulunan youth medium ve senior ın toplam data icinde kaçar tane olduğu yer alıyor yani size ı 3. 
    //totalData toplam data sayısı
    
    public double Info( ArrayList<ArrayList<Integer>> DecisionRate,ArrayList<Integer> PartitionNum,int totalData)
    {
        double sum=0;
        
        for(int i=0; i< PartitionNum.size();i++)
        {
            sum+=((double)PartitionNum.get(i)/(double)totalData) * Entropy(DecisionRate.get(i).get(0), DecisionRate.get(i).get(1));
        }
        
        return sum;  
    }
    
}
