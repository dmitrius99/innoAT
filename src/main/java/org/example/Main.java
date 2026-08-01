package org.example;

import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println(HomeWork.isEven(2));
        System.out.println(HomeWork.checkAccess(21));
        System.out.println(HomeWork.isPositive(21));
        System.out.println(HomeWork.getGrade(1000));
        System.out.println(HomeWork.blastOff(43));
        System.out.println(HomeWork.sumToN(22));
        System.out.println(HomeWork.hasBug(new String[]{"HaSe","blade","BUG"}));
        System.out.println(HomeWork.getEvenInRange(2,21));
        System.out.println(HomeWork.findMax(new int[]{10,66,12}));
        System.out.println(Arrays.toString(
                HomeWork.reverse(new String[]{"One", "Two", "Zero"})
        ));
        System.out.println(HomeWork.calcAverage(List.of(1,1,1)));
        System.out.println(HomeWork.removeSpecificName(List.of("Dima","Vlad","Vova"), "Vlad"));
    }
}

