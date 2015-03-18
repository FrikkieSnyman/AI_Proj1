/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author frikkie
 */
public class BoardUI extends javax.swing.JFrame implements ActionListener{
    Integer n;
    Boolean selected;
    Integer selectedX;
    Integer selectedY;
    Integer currentX;
    Integer currentY;
    Board gameBoard;
    Game game;
    Integer boardSize;
    
    /**
     * Creates new form BoardUI
     */
    public BoardUI() {
        initComponents();
        n = 8;
        selected = false;
        selectedX = -1;
        selectedY = -1;
    }
    
    /**
     * Creates new form BoardUI
     */
    public BoardUI(Integer n, Game game) {
        initComponents();
        this.n = n;
        this.game = game;
        selected = false;
        selectedX = -1;
        selectedY = -1;
        
//        DefaultTableModel dtm = (DefaultTableModel) tblBoard.getModel();
//        dtm.setRowCount(n);
//        dtm.setColumnCount(n);
//        tblBoard.setModel(dtm);
//        tblBoard.setShowGrid(true);
//        for (int i = 0; i < n; ++i){
//            tblBoard.setRowHeight(i,520/n);
//        }
    }
    /**
     * 
     * @return 
     */
    public javax.swing.JPanel getPanel(){
        return this.gamePanel;
    }
    /**
     * 
     * @param gameBoard 
     */
    public void setBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }
    
//    public void setTable(javax.swing.JTable table){
//        tblBoard = table;
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gamePanel = new javax.swing.JPanel();

        gamePanel.setName("gamePanel"); // NOI18N

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 940, Short.MAX_VALUE)
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BoardUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BoardUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BoardUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BoardUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BoardUI().setVisible(true);
            }
        });
    }
    /**
     * 
     * @param boardSize
     * @return 
     */
    public javax.swing.JButton[][] createBTNS(Integer boardSize) {
        this.boardSize = boardSize;
        javax.swing.JButton[][] btns = new javax.swing.JButton[boardSize][boardSize];
        
        for (int x = 0; x < boardSize; ++x) {
            for (int y = 0; y < boardSize; ++y) {
                btns[y][x] = new javax.swing.JButton(y + "_" + x);
                btns[y][x].setText("");
//                btns[y][x].setFocusable(false);
                btns[y][x].addActionListener(this);

                gamePanel.add(btns[y][x]);
            }
        }
        
        return btns;
    }
    /**
     * 
     * @param e 
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
       setCurrent(e);
       
       if (selected) {
           moveCell(selectedX, selectedY, currentX, currentY);
       } else {
        setSelected();

        if (selected) {
            game.btns[selectedY][selectedX].setBackground(java.awt.Color.yellow);
        } else {
            if (selectedX != -1) {
                game.btns[selectedY][selectedX].setBackground(java.awt.Color.gray);
            }
        }
       }
    }
    /**
     * 
     * @param e 
     */
    public void setCurrent(java.awt.event.ActionEvent e) {
        for (int x = 0; x < boardSize; ++x) {
            for (int y = 0; y < boardSize; ++y) {
                if (e.getSource() == game.btns[y][x]) {
                    currentX = x;
                    currentY = y;

                    return;
                }
            }
        }
    }
    /**
     * 
     */
    public void setSelected() {
        if (game.board.getBlockType(currentY, currentX).equals(BlockType.BLUE_OCCUPIED) || game.board.getBlockType(currentY, currentX).equals(BlockType.RED_OCCUPIED)) {
            selected = true;
            selectedX = currentX;
            selectedY = currentY;
        } else {
            selected = false;
        }
    }
    
    public void moveCell(Integer startX, Integer startY, Integer stopX, Integer stopY) {
        System.out.println("here");
        if (game.board.blockType[startY][startX].equals(BlockType.BLUE_OCCUPIED)) {
            game.board.blockType[stopY][stopX] = BlockType.BLUE_OCCUPIED;
            game.board.blockType[startY][startX] = BlockType.EMPTY;
            for (int i = 0; i < game.bluePlayer.cellList.size(); ++i){
                if (game.bluePlayer.cellList.get(i).positionX == startX && game.bluePlayer.cellList.get(i).positionY == startY){
                    game.bluePlayer.cellList.get(i).setPositionX(stopX);
                    game.bluePlayer.cellList.get(i).setPositionY(stopY);
                }
            }
        } else {
            game.board.blockType[stopY][stopX] = BlockType.RED_OCCUPIED;
            game.board.blockType[startY][startX] = BlockType.EMPTY;
            for (int i = 0; i < game.redPlayer.cellList.size(); ++i){
                if (game.redPlayer.cellList.get(i).positionX == startX && game.redPlayer.cellList.get(i).positionY == startY){
                    game.redPlayer.cellList.get(i).setPositionX(stopX);
                    game.redPlayer.cellList.get(i).setPositionY(stopY);
                }
            }
        }
        
        game.board.blockType[startY][startX] = BlockType.EMPTY;
        
        selected = false;
        selectedX = -1;
        selectedY = -1;
        /*
        * UpdateCellList with new position of selected cell in all celllists
        * Recalculate influenced cells
        */
        for (int i = 0; i < game.allCells.size(); ++i){
            if (game.allCells.get(i).positionX == startX && game.allCells.get(i).positionY == startY){
                game.allCells.get(i).setPositionX(stopX);
                game.allCells.get(i).setPositionY(stopY);
            }
        }
        
        for (int i = 0; i < game.board.cellList.size(); ++i){
            if (game.board.cellList.get(i).positionX == startX && game.board.cellList.get(i).positionY == startY){
                game.board.cellList.get(i).setPositionX(stopX);
                game.board.cellList.get(i).setPositionY(stopY);
            }
        }
        
        game.redrawBoard();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel gamePanel;
    // End of variables declaration//GEN-END:variables
}
