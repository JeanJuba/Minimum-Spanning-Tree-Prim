/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primmintreegenerator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Tree;

/**
 *
 * @author Usuario
 */
public class PrimMinTreeGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tree t=  new Tree();
        try {
            t.readFile();
            t.prepareData();
            t.pritMap();
            t.findTree();
        } catch (IOException ex) {
            Logger.getLogger(PrimMinTreeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
