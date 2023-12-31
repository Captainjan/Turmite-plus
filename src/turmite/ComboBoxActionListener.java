package turmite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * This listens to the selected options from the ComboBox and implements the load and save methods.
 */
public class ComboBoxActionListener implements ActionListener {
    JComboBox<String> menu;
    Game game;

    ComboBoxActionListener(Game gameInput, JComboBox<String> boxInput) {
        menu = boxInput;
        game = gameInput;
    }

    /**
     * Loads, saves, creates a game based on the option that is selected. If its new game then it calls the
     * new game function of the Game class. If Load is selected then using an InputStream from grid.txt and turmite.txt
     * it loads the list of turmites and the grid. If Save is selected then by using an OutputStream it saves the
     * same things. It can do this since the said objects are serializable. Throws exceptions when an error occured.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (game.stopped) {
            String selectedOption = (String) menu.getSelectedItem();
            switch (selectedOption) {
                case "New game": {
                    game.newGame();
                }
                break;
                case "Load game": {
                    try {
                        FileInputStream f = new FileInputStream("grid.txt");
                        ObjectInputStream gridIn = new ObjectInputStream(f);

                        game.gameGrid = (Grid) gridIn.readObject();
                        game.reloadGrid();

                        FileInputStream g = new FileInputStream("turmite.txt");
                        ObjectInputStream turmiteIn = new ObjectInputStream(g);

                        game.turmiteList.clear();
                        game.turmiteList = (ArrayList<Turmite>) turmiteIn.readObject();

                        for (Turmite t : game.turmiteList) {
                            t.game = game;
                        }

                    } catch (ClassNotFoundException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
                case "Save game": {
                    try {
                        FileOutputStream f = new FileOutputStream("grid.txt");
                        ObjectOutputStream gridOut = new ObjectOutputStream(f);
                        gridOut.writeObject(game.gameGrid);
                        gridOut.close();

                        FileOutputStream g = new FileOutputStream("turmite.txt");
                        ObjectOutputStream turmiteOut = new ObjectOutputStream(g);
                        turmiteOut.writeObject(game.turmiteList);
                        turmiteOut.close();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
                default:
                    break;
            }
        }
    }
}
