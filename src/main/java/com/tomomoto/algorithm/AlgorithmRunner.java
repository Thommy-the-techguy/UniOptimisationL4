package com.tomomoto.algorithm;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmRunner {
    @SneakyThrows
    public static void main(String[] args) {
        Path pathToFile = Path.of("src", "main", "resources", "matrix.txt");
        List<String> rows = Files.readAllLines(pathToFile);
        List<String> finalRows = new ArrayList<>();
        rows.forEach(row -> finalRows.add(row.replace(" ", "")));

        int numberOfFunctions = finalRows.get(0).length();
        System.out.println(GreedyCoverageAlgorithm.getCoverage(finalRows, numberOfFunctions));
    }
}
