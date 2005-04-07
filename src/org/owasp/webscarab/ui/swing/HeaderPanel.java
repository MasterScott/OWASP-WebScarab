/*
 * HeaderPanel.java
 *
 * Created on 05 April 2005, 02:57
 */

package org.owasp.webscarab.ui.swing;

import org.owasp.webscarab.model.NamedValue;

import javax.swing.table.AbstractTableModel;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author  rogan
 */
public class HeaderPanel extends javax.swing.JPanel {
    
    private final static NamedValue[] NO_HEADERS = new NamedValue[0];
    
    private boolean _editable = false;
    private boolean _modified = false;
    
    private HeaderTableModel _htm;
    private List _headers = new ArrayList();
    
    /** Creates new form HeaderPanel */
    public HeaderPanel() {
        _htm = new HeaderTableModel();
        initComponents();
    }
    
    public void setEditable(boolean editable) {
        _editable = editable;
        if (editable) {
            add(buttonPanel, java.awt.BorderLayout.EAST);
            headerTable.setBackground(new java.awt.Color(255,255,255));
        } else {
            remove(buttonPanel);
            headerTable.setBackground(new java.awt.Color(204,204,204));
        }
        // revalidate();
    }
    
    public boolean isEditable() {
        return _editable;
    }
    
    public boolean isModified() {
        if (headerTable.isEditing()) {
            headerTable.getCellEditor().stopCellEditing();
        }
        return _modified;
    }
    
    public void setHeaders(NamedValue[] headers) {
        _headers.clear();
        if (headers != null && headers.length > 0) {
            for (int i=0; i<headers.length; i++)
                _headers.add(headers[i]);
        }
        _modified = false;
        _htm.fireTableDataChanged();
    }
    
    public NamedValue[] getHeaders() {
        _modified = false;
        return (NamedValue[]) _headers.toArray(NO_HEADERS);
    }
    
    public void insertRow(int row) {
        _headers.add(row, new NamedValue("Header", "value"));
        _modified = true;
        _htm.fireTableRowsInserted(row, row);
    }
    
    public void removeRow(int row) {
        _headers.remove(row);
        _modified = true;
        _htm.fireTableRowsDeleted(row, row);
    }
        
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        buttonPanel = new javax.swing.JPanel();
        insertButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        headerTable = new javax.swing.JTable();

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        insertButton.setText("Insert");
        insertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        buttonPanel.add(insertButton, gridBagConstraints);

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        buttonPanel.add(deleteButton, gridBagConstraints);

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 50));
        headerTable.setModel(_htm);
        jScrollPane1.setViewportView(headerTable);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int rowIndex = headerTable.getSelectedRow();
        if (rowIndex > -1) {
            removeRow(rowIndex);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void insertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertButtonActionPerformed
        int rowIndex = headerTable.getSelectedRow();
        if (rowIndex > -1) {
            insertRow(rowIndex);
        } else {
            insertRow(_htm.getRowCount());
        }
    }//GEN-LAST:event_insertButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTable headerTable;
    private javax.swing.JButton insertButton;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
    private class HeaderTableModel extends AbstractTableModel {
        
        private String[] _names = new String[] { "Header", "Value"};
        
        public String getColumnName(int column) {
            return _names[column];
        }
        
        public int getColumnCount() {
            return 2;
        }
        
        public int getRowCount() {
            return _headers.size();
        }
        
        public Object getValueAt(int row, int column) {
            if (row > _headers.size()-1) return "ERROR";
            NamedValue nv = (NamedValue) _headers.get(row);
            if (column == 0) return nv.getName();
            return nv.getValue();
        }
        
        public void setValueAt(Object aValue, int row, int col) {
            if (_editable && aValue instanceof String) {
                NamedValue nv = (NamedValue) _headers.get(row);
                if (col == 0) {
                    _headers.set(row, new NamedValue((String)aValue, nv.getValue()));
                } else {
                    _headers.set(row, new NamedValue(nv.getName(), (String) aValue));
                }
                _modified = true;
                fireTableCellUpdated(row, col);
            }
        }
        
        public boolean isCellEditable(int row, int column) {
            return _editable;
        }
        
    }
    
}
