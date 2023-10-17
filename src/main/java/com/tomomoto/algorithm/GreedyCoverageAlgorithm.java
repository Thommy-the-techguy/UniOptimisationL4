package com.tomomoto.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GreedyCoverageAlgorithm {
    private GreedyCoverageAlgorithm() {

    }

    public static List<String> getCoverage(List<String> rowsModules, int numberOfFunctions) {
        List<String> result = new ArrayList<>();
        List<String> functionsToCover = getListWithFunctionsToCover(numberOfFunctions);
        List<String> rowsModulesCopy = List.copyOf(rowsModules);
        int iterator = 0;
        while (!functionsToCover.isEmpty()) {
            String module = rowsModules.get(0);
            removeCoveredFunctions(functionsToCover, getConnectionIndexesPerRow(rowsModulesCopy.get(rowsModulesCopy.size() - rowsModules.size())));
            result.add(String.format("m%d", ++iterator));
            removeCoveredColumns(rowsModules, module);
            rowsModules.remove(0);
        }
        return result;
    }

    private static List<String> getListWithFunctionsToCover(int numberOfFunctions) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < numberOfFunctions; i++) {
            result.add(String.format("f%d", i + 1));
        }
        return result;
    }

    private static List<Integer> getConnectionIndexesPerRow(String moduleString) {
        List<Integer> result = new ArrayList<>();
        List<String> values = Arrays.stream(moduleString.split("")).toList();
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).equals("1")) {
                result.add(i + 1);
            }
        }
        return result;
    }

    private static void removeCoveredFunctions(List<String> functionsToCover, List<Integer> functionsToRemove) {
        try {
            for (int index : functionsToRemove) {
                functionsToCover.remove(String.format("f%d", index));
            }
        } catch (Exception ignore) {

        }
    }

    private static void removeCoveredColumns(List<String> rowsModules, String module) {
        List<Integer> connections = getConnectionIndexesPerRow(module);
        int minusCoefficient = 0;
        for (int connectionIndex : connections) {
            for (int j = 0; j < rowsModules.size(); j++) {
                String newString = rowsModules.get(j).substring(0, connectionIndex - 1 - minusCoefficient) + rowsModules.get(j).substring(connectionIndex - minusCoefficient);
                rowsModules.add(j, newString);
                rowsModules.remove(j + 1);
            }
            minusCoefficient++;
        }
    }
}
