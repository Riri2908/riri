package riri.components;

import riri.components.page.BasePanel;
import riri.components.table.TablePanel;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.regex.Pattern;


public class SearchPanel extends BorderPanel {

    private final JTextField searchField;
    private final TablePanel table;
    private final String PLACEHOLDER = "Search...";
    private final Icon searchIcon = new ImageIcon(BasePanel.createImageLogo(getClass(),"sidebar/search",22,22)) ;
    private final JLabel searchLabel = new JLabel(searchIcon);

    public SearchPanel(TablePanel table) {
        super();
        this.table = table;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0,70));
        setMaximumSize(new Dimension(Integer.MAX_VALUE,70));
        setBorder(new EmptyBorder(13,15,13,15));

        BorderPanel searchPanel = new BorderPanel(16,Color.WHITE,0,0,new Color(221, 221, 221),1);
        searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.X_AXIS));

        searchField= new JTextField();

        showPlaceholder();
        searchField.setOpaque(false);
        searchField.setBorder(BorderFactory.createEmptyBorder());
        searchField.setFont(new Font("Arial",Font.PLAIN,15));

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                searchPanel.setBorder(new Color(31, 95, 255),2);
                if (isPlaceholder()) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                searchPanel.setBorder(new Color(221, 221, 221),1);
                if (searchField.getText().isEmpty()) {
                    showPlaceholder();

                }
            }
        });

        search();
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchLabel);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchField);

        add(searchPanel,BorderLayout.CENTER);

    }
    private void showPlaceholder() {
        searchField.setText(PLACEHOLDER);
        searchField.setForeground(new Color(174,174,174));
    }

    private boolean isPlaceholder() {
        return searchField.getText().equals(PLACEHOLDER);
    }
    private void search() {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }
            private void search(){
                String text = searchField.getText();

                if(text.isEmpty() || isPlaceholder()){
                    sorter.setRowFilter(null);
                }else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ Pattern.quote(text)));
                }
            }
        });
    }
}