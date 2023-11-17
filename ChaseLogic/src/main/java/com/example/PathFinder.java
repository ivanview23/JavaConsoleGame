package com.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PathFinder {
    private MapCreator mapCreator;
    private Pair<Integer, Integer>[][] parent;

    public PathFinder(MapCreator mapCreator) {
        this.mapCreator = mapCreator;
        parent = new Pair[mapCreator.getMap().length][mapCreator.getMap()[0].length];
    }

    public Pair<Integer, Integer> getNextStep(Pair<Integer, Integer> startPosPair, char charDestination, List<Character> listNotPass) throws IllegalArgumentException {
        int[][] matrix = mapCreator.getMap();
        Pair<Integer, Integer> endPosPair = findCharacterPosition(matrix, charDestination);
        List<Pair<Integer, Integer>> foundedPath = findShortestPath(matrix, startPosPair, endPosPair, listNotPass);
        mapCreator.clearVisitedMap();
        if (foundedPath.contains(endPosPair)) {
            System.out.println("Enemy found player");
            return endPosPair;
        }

        return foundedPath.get(foundedPath.size() - 1);
    }

    private List<Pair<Integer, Integer>> findShortestPath(int[][] matrix, Pair<Integer, Integer> startPosPair, Pair<Integer, Integer> endPosPair, List<Character> listNotPass) throws IllegalArgumentException {
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        boolean visited[][] = new boolean[matrix.length][matrix[0].length];
        visited[startPosPair.getLeft()][startPosPair.getRight()] = true;
        mapCreator.setVisitedMap(startPosPair.getLeft(), startPosPair.getRight());
        queue.offer(startPosPair);
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> current = queue.poll();
            List<Pair<Integer, Integer>> listWays = findPossibleWays(current, listNotPass);
            for (Pair<Integer, Integer> way : listWays) {
                queue.offer(way);
                visited[way.getLeft()][way.getRight()] = true;
                mapCreator.setVisitedMap(way.getLeft(), way.getRight());
                if (way.getLeft() == endPosPair.getLeft() && way.getRight() == endPosPair.getRight()) {
                    List<Pair<Integer, Integer>> path = getShortestPath(startPosPair, endPosPair);
                    for (Pair<Integer, Integer> pair : path) {
                        mapCreator.setVisitedMap(pair.getLeft(), pair.getRight());
                    }

                    return getShortestPath(startPosPair, endPosPair);
                }
            }
        }
        throw new IllegalArgumentException("NO WAY FOUND");
    }

    private List<Pair<Integer, Integer>> getShortestPath(Pair<Integer, Integer> startPosPair, Pair<Integer, Integer> endPosPair) {
        List<Pair<Integer, Integer>> result = new LinkedList<>();
        Pair<Integer, Integer> currentPair = parent[endPosPair.getLeft()][endPosPair.getRight()];

        if (currentPair.equals(startPosPair)) {
            result.add(endPosPair);
            return result;
        }

        while (!startPosPair.equals(currentPair)) {
            result.add(currentPair);
            currentPair = parent[currentPair.getLeft()][currentPair.getRight()];
        }
        return result;
    }

    public List<Pair<Integer, Integer>> findPossibleWays(Pair<Integer, Integer> current, List<Character> listCanPass) {
        List<Pair<Integer, Integer>> result = new LinkedList<>();
        int[][] matrix = mapCreator.getMap();
        boolean[][] visited = mapCreator.getVisitedMap();

        if (current.getLeft() > 0 && isWayCanPass(matrix, current.getLeft() - 1, current.getRight(), listCanPass) && !visited[current.getLeft() - 1][current.getRight()]) {
            result.add(new Pair<>(current.getLeft() - 1, current.getRight()));
            parent[current.getLeft() - 1][current.getRight()] = new Pair<>(current.getLeft(), current.getRight());
        }

        if (current.getLeft() < matrix.length - 1 && isWayCanPass(matrix, current.getLeft() + 1, current.getRight(), listCanPass) && !visited[current.getLeft() + 1][current.getRight()]) {
            result.add(new Pair<>(current.getLeft() + 1, current.getRight()));
            parent[current.getLeft() + 1][current.getRight()] = new Pair<>(current.getLeft(), current.getRight());
        }

        if (current.getRight() > 0 && isWayCanPass(matrix, current.getLeft(), current.getRight() - 1, listCanPass) && !visited[current.getLeft()][current.getRight() - 1]) {
            result.add(new Pair<>(current.getLeft(), current.getRight() - 1));
            parent[current.getLeft()][current.getRight() - 1] = new Pair<>(current.getLeft(), current.getRight());
        }

        if (current.getRight() < matrix[0].length - 1 && isWayCanPass(matrix, current.getLeft(), current.getRight() + 1, listCanPass) && !visited[current.getLeft()][current.getRight() + 1]) {
            result.add(new Pair<>(current.getLeft(), current.getRight() + 1));
            parent[current.getLeft()][current.getRight() + 1] = new Pair<>(current.getLeft(), current.getRight());
        }
        return result;
    }

    private boolean isWayCanPass(int[][] matrix, int x, int y, List<Character> listCanPass) {
        for (Character character : listCanPass) {
            if (matrix[x][y] == character) {
                return false;
            }
        }
        return true;
    }

    private Pair<Integer, Integer> findCharacterPosition(int[][] matrix, char character) throws IllegalArgumentException {
        Pair<Integer, Integer> result = null;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == character) {
                    result = new Pair<>(i, j);
                    return result;
                }
            }
        }
        throw new IllegalArgumentException("Character not found");
    }

    public void printMap() {
        int[][] map = mapCreator.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print((char) map[i][j]);
            }
            System.out.println();
        }
    }

    public void printParent() {
        for (int i = 0; i < parent.length; i++) {
            for (int j = 0; j < parent[i].length; j++) {
                System.out.print(parent[i][j] + " ");
            }
            System.out.println();
        }
    }
}
