package com.elle.ProjectManager.presentation;

import com.elle.ProjectManager.database.DBConnection;
import com.elle.ProjectManager.database.ModifiedTableData;
import com.elle.ProjectManager.logic.ColumnPopupMenu;
import static com.elle.ProjectManager.logic.ITableConstants.TASKFILES_TABLE_NAME;
import static com.elle.ProjectManager.logic.ITableConstants.TASKNOTES_TABLE_NAME;
import static com.elle.ProjectManager.logic.ITableConstants.TASKS_TABLE_NAME;
import com.elle.ProjectManager.logic.Tab;
import com.elle.ProjectManager.logic.TableFilter;
import com.elle.ProjectManager.logic.Validator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * AddRecordsWindow
 *
 * @author Louis W.
 * @author Carlos Igreja
 * @since June 10, 2015
 * @version 0.6.3
 */
public class AddRecordsWindow extends JFrame {

    // attributes
    private String[] columnNames;
    private int numRowsAdded;           // number of rows added counter
    private Map<String, Tab> tabs;       // used to update the records label
    private Statement statement;

    // components
    private ProjectManagerWindow projectManager;
    private LogWindow logWindow;
    private DefaultTableModel model;

    private Color defaultSelectedBG;

    private ArrayList<Integer> rowsNotEmpty; // only includes rows that have data

    private TableCellPopupWindow tableCellPopupWindow;

    // used to notify if the tableSelected is editing
    // the tableSelected.isEditing method has issues from the tableModelListener
    private boolean isEditing;

    /**
     * Creates new form AddRecordsWindow
     */
    public AddRecordsWindow() {

        rowsNotEmpty = new ArrayList<>();
        isEditing = false;

        // initialize components
        initComponents();
        projectManager = ProjectManagerWindow.getInstance();
        logWindow = projectManager.getLogWindow();
        tabs = projectManager.getTabs();
        statement = projectManager.getStatement();

        // set the selected tableSelected name
        table.setName(projectManager.getSelectedTabName());

        // get default selected bg color
        defaultSelectedBG = table.getSelectionBackground();

        // create a new empty tableSelected
        createEmptyTable();

        // sets the keyboard focus manager
        setKeyboardFocusManager();

        // add listeners
        addTableListeners();

        // submit button does not start enabled because the tableSelected is empty
        btnSubmit.setEnabled(false);

        // set the label header
        this.setTitle("Add Records to " + table.getName());

        // set the size for AddRecord window
        this.setPreferredSize(new Dimension(1137, 120));
        this.setMinimumSize(new Dimension(1137, 120));

        // set the tableSelected cell popup window
        tableCellPopupWindow = new TableCellPopupWindow(this);
        if (!tableCellPopupWindow.getWindowPopup()) {
            tableCellPopupWindow.setTableListener(table, this);
        }

        // set this window to appear in the middle of Analyster
        this.setLocationRelativeTo(projectManager);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                projectManager.setDisableProjecetManagerFunction(true);

            }
        });
        this.pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        scrollpane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnAddRow = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(550, 200));
        setSize(new java.awt.Dimension(894, 560));

        scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setMaximumSize(new java.awt.Dimension(260, 100));
        scrollpane.setMinimumSize(new java.awt.Dimension(130, 20));

        table.setAutoCreateRowSorter(true);
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "symbol", "analyst", "priority", "dateAssigned", "note"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table.setMinimumSize(new java.awt.Dimension(75, 20));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableKeyPressed(evt);
            }
        });
        scrollpane.setViewportView(table);

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnAddRow.setText("+");
        btnAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddRow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSubmit)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddRow)
                    .addComponent(btnSubmit)
                    .addComponent(btnCancel))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * jSubmitActionPerformed This is performed when the submit button is
     * executed. Refactored by Carlos Igreja 7-28-2015
     *
     * @param evt
     */
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed

        projectManager.setDisableProjecetManagerFunction(true);

        submit();
        projectManager.setAddRecordsWindowShow(false);
    }//GEN-LAST:event_btnSubmitActionPerformed

    /**
     * submit This is used when the submit button is pressed or if the enter key
     * is pressed when the tableSelected is finished editing to submit the data
     * to the database.
     */
    private void submit() {

        Object cellValue = null;                 // store cell value
        int col = 0;                             // column index
        int row = 0;                             // row index

        // check if data is valid
        if (validateData()) {

            // once data checked, execute sql statement
            // first get the insert statement for the tableSelected
            String insertInto = "INSERT INTO " + table.getName() + " (";

            // this tableSelected should already not include the primary key
            for (col = 0; col < table.getColumnCount(); col++) {
                if (col != table.getColumnCount() - 1) {
                    insertInto += table.getColumnName(col) + ", ";
                } else {
                    insertInto += table.getColumnName(col) + ") ";
                }
            }

            numRowsAdded = 0; // reset numRowsAdded counter

            // Now get the values to add to the database
            String values = "";
            for (row = 0; row < table.getRowCount(); row++) {
                values = "VALUES (";  // start the values statement
                for (col = 0; col < table.getColumnCount(); col++) {

                    // get cell value
                    cellValue = table.getValueAt(row, col);

                    // format the cell value for sql
                    if (cellValue != null) {

                        // if cell is empty it must be null
                        if (cellValue.toString().equals("")) {
                            cellValue = null;
                        } // if the cell is not empty it must have single quotes
                        else {
                            cellValue = "'" + cellValue + "'";
                        }
                    }

                    // skip empty rows
                    // this must be after the format cell value so the "" => null
                    if (col == 0 && cellValue == null) {
                        break;
                    }

                    // add each value for each column to the values statement
                    if (col != table.getColumnCount() - 1) {
                        values += cellValue + ", ";
                    } else {
                        values += cellValue + ");";
                    }
                }

                try {
                    // execute the sql statement
                    if (!values.equals("VALUES (")) {      //skip if nothing was added
                        // open connection because might time out
                        DBConnection.open();
                        statement = DBConnection.getStatement();
                        statement.executeUpdate(insertInto + values);
                        numRowsAdded++;   // increment the number of rows added
                    }
                } catch (SQLException sqlException) {
                    try {
                        JOptionPane.showMessageDialog(null, "Upload failed!");

                        if (statement.getWarnings().getMessage() != null) {

                            String levelMessage = "2:" + statement.getWarnings().getMessage();
                            logWindow.addMessageWithDate(levelMessage);
//                            logWindow.
                            System.out.println(statement.getWarnings().getMessage());

                            System.out.println(levelMessage);//delete

                            statement.clearWarnings();
                        }
                        logWindow.addMessageWithDate("2:add record submit failed!");
                    } // end try-catch
                    catch (SQLException ex) {
                        // this should never be called
                        ex.printStackTrace();
                    }
                }
            }

            if (numRowsAdded > 0) {
                // update tableSelected and records label
                String tabName = projectManager.getSelectedTabName();              // tab name
                Tab tab = tabs.get(tabName);                                  // selected tab

                JTable table = tab.getTable();                                // selected tableSelected
                projectManager.loadTable(table);                              // load tableSelected data from database

                // reload new table data for modifiedTableData
                ModifiedTableData data = tab.getTableData();
                data.reloadData();

                TableFilter filter = tab.getFilter();                         // tableSelected filter
                filter.applyFilter();                                         // apply filter
                filter.applyColorHeaders();                                   // apply color headers

                ColumnPopupMenu ColumnPopupMenu = tab.getColumnPopupMenu();   // column popup menu 
                ColumnPopupMenu.loadAllCheckBoxItems();                       // refresh the data for the column pop up

                tab.addToTotalRowCount(numRowsAdded);                         // add the number of records added to the total records count
                JLabel recordsLabel = projectManager.getRecordsLabel();
                String recordsLabelText = tab.getRecordsLabel();              // store the records label string
                recordsLabel.setText(recordsLabelText);                       // update the records label text

                projectManager.setLastUpdateTime();                                // set the last update time from database

                JOptionPane.showMessageDialog(this,
                        numRowsAdded + " Add successfully!");                 // show dialog box that upload was successful
                createEmptyTable();                                           // create a new empty tableSelected with default 10 rows
            }
        }
    }

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked

    }//GEN-LAST:event_tableMouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
        projectManager.setAddRecordsWindowShow(false);
        projectManager.setDisableProjecetManagerFunction(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRowActionPerformed

        // add an empty row to the tableSelected
        model.addRow(new Object[]{});

    }//GEN-LAST:event_btnAddRowActionPerformed

    private void tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyPressed

    }//GEN-LAST:event_tableKeyPressed

    /**
     * setKeyboardFocusManager Sets the Keyboard Focus Manager
     */
    private void setKeyboardFocusManager() {

        /*
         No Tab key-pressed or key-released events are received by the key event listener. This is because the focus subsystem 
         consumes focus traversal keys, such as Tab and Shift Tab. To solve this, apply the following to the component that is 
         firing the key events 
         */
        table.setFocusTraversalKeysEnabled(false);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {// Allow to TAB-

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {

                if (e.getComponent() instanceof JTable) {

                    // this is called to either clear data or submit data
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && !table.isEditing()) {

                        // clear the row(s)
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            if (table.getSelectionBackground() == Color.RED) {
                                int[] rows = table.getSelectedRows();

                                if (rows != null) {
                                    for (int row : rows) {
                                        for (int col = 0; col < table.getColumnCount(); col++) {
                                            table.getModel().setValueAt("", row, col);
                                        }
                                    }
                                }
                                table.setSelectionBackground(defaultSelectedBG);

                                // check for empty rows/table
                                checkForEmptyRows();
                                if (rowsNotEmpty.isEmpty()) {
                                    btnSubmit.setEnabled(false);
                                } else {
                                    btnSubmit.setEnabled(true);
                                }
                            } // submit the data
                            else if (table.getSelectionBackground() != Color.RED) {
                                submit();
                            }
                        }
                    } // this toggles the red bg for clearing row data
                    else if (e.getKeyCode() == KeyEvent.VK_DELETE) {

                        if (e.getID() == KeyEvent.KEY_RELEASED) {
                            if (table.isEditing()) {
                                table.getCellEditor().stopCellEditing();
                            }

                            if (table.getSelectionBackground() == defaultSelectedBG) {
                                table.setSelectionBackground(Color.RED);
                            } else {
                                table.setSelectionBackground(defaultSelectedBG);
                            }
                        }
                    } // this is to tab and move to cells with arrow keys
                    else if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_LEFT
                            || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP
                            || e.getKeyCode() == KeyEvent.VK_DOWN) {

                        JTable tableSelected = (JTable) e.getComponent();

                        if (e.getID() == KeyEvent.KEY_RELEASED) {
                            //show popup Window by different table
                            popupWindowShowInRecordByDiffTable(tableSelected);

                        } else if (e.getID() == KeyEvent.KEY_PRESSED) {

                        } else {

                        }
                    }

                } // end table component condition
                // ctrl + D fills in the current date
                else if (e.getKeyCode() == KeyEvent.VK_D && e.isControlDown()) {
                    JTable table = (JTable) e.getComponent().getParent();
                    int column = table.getSelectedColumn();
                    if (table.getColumnName(column).toLowerCase().contains("date")) {
                        if (e.getID() != 401) {
                            return false;
                        } else {
                            JTextField selectCom = (JTextField) e.getComponent();
                            selectCom.requestFocusInWindow();
                            selectCom.selectAll();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            String today = dateFormat.format(date);
                            selectCom.setText(today);
                        }// default date input with today's date}
                    }
                }

                return false;
            }
        });
    }

    private void popupWindowShowInRecordByDiffTable(JTable tableSelected) {

        int column = tableSelected.getSelectedColumn();

        if (tableSelected.getName().equals(TASKS_TABLE_NAME)) {

            if (tableSelected.getColumnName(column).equals("title")
                    || tableSelected.getColumnName(column).equals("description")
                    || tableSelected.getColumnName(column).equals("instructions")) {

                // popup tableSelected cell edit window
                tableCellPopupWindow.getTableCellPopup(tableSelected, this);
            } else {
                tableCellPopupWindow.setTableCellPopupWindowVisible(false);
            }
        } else if (tableSelected.getName().equals(TASKFILES_TABLE_NAME)) {

            if (tableSelected.getColumnName(column).equals("files")
                    || tableSelected.getColumnName(column).equals("notes")
                    || tableSelected.getColumnName(column).equals("path")) {
                // popup tableSelected cell edit window
                tableCellPopupWindow.getTableCellPopup(tableSelected, this);
            } else {
                tableCellPopupWindow.setTableCellPopupWindowVisible(false);
            }
        } else if (tableSelected.getName().equals(TASKNOTES_TABLE_NAME)) {

            if (tableSelected.getColumnName(column).equals("status_notes")) {
                // popup tableSelected cell edit window
                tableCellPopupWindow.getTableCellPopup(tableSelected, this);
            } else {
                tableCellPopupWindow.setTableCellPopupWindowVisible(false);
            }
        }
    }

    /**
     * createEmptyTable creates an empty tableSelected with default 10 rows
     */
    private void createEmptyTable() {
        // get column names for selected Analyster tableSelected
        columnNames = projectManager.getTabs().get(table.getName()).getTableColNames();

        // we don't want the ID column 
        columnNames = Arrays.copyOfRange(columnNames, 1, columnNames.length);

        // set the tableSelected model - add 10 empty rows
        model = new DefaultTableModel(columnNames, 1);

        // add the tableSelected model to the tableSelected
        table.setModel(model);

        // get tableSelected column width format
        float[] widths = tabs.get(table.getName()).getColWidthPercent();
        widths = Arrays.copyOfRange(widths, 1, widths.length);

        projectManager.setColumnFormat(widths, table);
    }

    /**
     * addTableListeners This is called to add the listeners to the
     * tableSelected The listeners added are the TableModel listener the
     * MouseListener and the KeyListener
     */
    public void addTableListeners() {

        // add tableModelListener
        table.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {

                // isEditing is a class boolean triggered true on double click
                if (!isEditing) {
                    // if clearing row then do not validate
                    if (table.getSelectionBackground() != Color.RED) {
                        // check the cell for valid entry
                        int row = e.getFirstRow();            // row index
                        int col = e.getColumn();             // column index
                        validateCell(row, col);
                    }

                    // get value of cell
                    int row = e.getFirstRow();
                    int col = e.getColumn();
                    Object value = table.getValueAt(row, col);

                    // if cell value is empty
                    if (value == null || value.equals("")) {
                        // check to see if it was a deletion
                        if (!rowsNotEmpty.isEmpty() && rowsNotEmpty.contains(row)) {
                            checkForEmptyRows();
                        }
                    } // else add the row to the list as not empty
                    else {
                        rowsNotEmpty.add(row);
                    }

                    // if list is empty then the tableSelected is empty
                    if (!rowsNotEmpty.isEmpty() && !isEditing) {
                        btnSubmit.setEnabled(true);
                    }
                }

                // reset isEditing boolean
                isEditing = false;
            }

        });

        // add mouseListener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getClickCount() == 1) {
                    // if we click away the red delete should go away
                    if (table.getSelectionBackground() == Color.RED && !e.isControlDown()) {
                        table.setSelectionBackground(defaultSelectedBG);
                    }
                } // this enters edit mode
                else if (e.getClickCount() == 2) {
                    btnSubmit.setEnabled(false);
                    isEditing = true;
                    selectAllText(e);
                }
            }
        });
    }

    /**
     * selectAllText Select all text inside jTextField or a cell
     *
     * @param e
     */
    private void selectAllText(MouseEvent e) {

        JTable table = (JTable) e.getComponent();
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        if (column != -1) {
            table.getComponentAt(row, column).requestFocus();
            table.editCellAt(row, column);
            JTextField selectCom = (JTextField) table.getEditorComponent();
            if (selectCom != null) {
                selectCom.requestFocusInWindow();
                selectCom.selectAll();
            }
        }
    }

    /**
     * validateCell
     *
     * @param row
     * @param col
     * @return returns true if valid or false if error
     */
    public boolean validateCell(int row, int col) {

        String colName = table.getColumnName(col);           // column name
        Object cellValue = table.getValueAt(row, col);       // store cell value
        String errorMsg = "Error with " + colName
                + " in row " + (row + 1) + ".\n";            // error message
        boolean error = false;                               // error occurred

        switch (colName) {
            case "symbol":
                if (cellValue == null || cellValue.toString().equals("")) {
                    errorMsg += "Symbol cannot be null";
                    error = true;
                }
                break;
            case "analyst":
                break;
            case "priority":
                if (cellValue != null && !cellValue.toString().equals("")) {
                    if (!cellValue.toString().matches("[1-5]{1}")) {
                        errorMsg += "Priority must be an Integer (1-5)";
                        error = true;
                    }
                }
                break;
            case "dateAssigned":
                if (cellValue != null && !cellValue.toString().equals("")) {
                    if (!Validator.isValidDate("yyyy-MM-dd", cellValue.toString())) {
                        errorMsg += "Date format not correct: YYYY-MM-DD";
                        error = true;
                    }
                }
                break;
            case "dateDone":
                if (cellValue != null && !cellValue.toString().equals("")) {
                    if (!Validator.isValidDate("yyyy-MM-dd", cellValue.toString())) {
                        errorMsg += "Date format not correct: YYYY-MM-DD";
                        error = true;
                    }
                }
                break;
            case "notes":
                break;
            case "author":
                break;
            case "analysisDate":
                if (cellValue != null && !cellValue.toString().equals("")) {
                    if (!Validator.isValidDate("yyyy-MM-dd", cellValue.toString())) {
                        errorMsg += "Date format not correct: YYYY-MM-DD";
                        error = true;
                    }
                }
                break;
            case "path":
                break;
            case "document":
                break;
            case "notesL":
                break;
            default:
                break;

        }// end switch

        if (error) {
            JOptionPane.showMessageDialog(table, errorMsg);
            //btnSubmit.setEnabled(true); 
        }

        return !error;  // if there was an error, return false for failed
    }

    /**
     * validateData Validates all the data in the tableSelected to make sure it
     * is correct. This is used to validate the data before it is executed to
     * the server and the database so that there will not be any errors.
     *
     * @return returns true if the data is all valid and false if the is a
     * single error
     */
    public boolean validateData() {

        int col = 0;                    // column index
        boolean isCellValid = true;    // if cell is valid entry 

        // if tableSelected is empty
        if (!rowsNotEmpty.isEmpty()) {

            // check data
            for (int row : rowsNotEmpty) {

                // if there was an error stop
                if (!isCellValid) {
                    break;
                }

                for (col = 0; col < table.getColumnCount(); col++) {

                    // if there was an error stop
                    if (!isCellValid) {
                        break;
                    }

                    // begin error message
                    isCellValid = validateCell(row, col);

                }// end col for loop
            }// end row for loop

            return isCellValid;
        }

        return false; // tableSelected is empty
    }

    /**
     * checkForEmptyRows This should be used when data is removed or deleted
     */
    public void checkForEmptyRows() {

        ArrayList<Integer> arrayCopy = new ArrayList(rowsNotEmpty);
        rowsNotEmpty.clear();

        // check List for empty rows
        for (int row : arrayCopy) {
            boolean isNotEmpty = false;
            for (int col = 0; col < table.getColumnCount(); col++) {
                Object value = table.getValueAt(row, col);
                if (value != null && !value.equals("")) {
                    isNotEmpty = true;
                    break;
                }
            }
            if (isNotEmpty) {
                rowsNotEmpty.add(row);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddRow;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane scrollpane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

}
