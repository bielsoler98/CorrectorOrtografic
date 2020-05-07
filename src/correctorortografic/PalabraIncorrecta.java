package correctorortografic;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author biels
 */
public class PalabraIncorrecta {
    
    private String content;
    private int minDistance;
    private ArrayList<String> nearestWords;

    public PalabraIncorrecta(String content) {
        this.content = content;
        this.minDistance = Integer.MAX_VALUE;
        this.nearestWords = new ArrayList();
    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
        this.nearestWords.clear();
    }

    public ArrayList<String> getNearestWords() {
        return nearestWords;
    }
    
    public void addWord(String nearWord){
        nearestWords.add(nearWord);
    }

    public String getContent() {
        return content;
    }
    
}
