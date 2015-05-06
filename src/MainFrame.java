import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.GroupLayout.Alignment.CENTER;

public class MainFrame extends JFrame {

    private DefaultListModel model;

    private JList list;

    private JButton remallbtn;

    private JButton addbtn;
    private JButton renbtn;
    private JButton delbtn;

    private MainFrame instance;

    public MainFrame() {
        initUI();
        instance = this;
    }

    private void createList() {

        model = new DefaultListModel();
        model.addElement("Miguel Callón");
        model.addElement("Santiago Iglesias");
        model.addElement("Nicolás Vila");

        list = new JList(model);
        list.setName("Lista");
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    Object item = model.getElementAt(index);
                    String text = JOptionPane.showInputDialog(instance, "Rename item", item);
                    String newitem = null;
                    if (text != null) {
                        newitem = text.trim();
                    } else {
                        return;
                    }

                    if (!newitem.isEmpty()) {
                        model.remove(index);
                        model.add(index, newitem);
                        ListSelectionModel selmodel = list.getSelectionModel();
                        selmodel.setLeadSelectionIndex(index);
                    }
                }
            }
        });
    }

    private void createButtons() {

        remallbtn = new JButton("Remove All");
        remallbtn.setName("Remove All");
        addbtn = new JButton("Add");
        addbtn.setName("Add");
        renbtn = new JButton("Rename");
        renbtn.setName("Rename");
        delbtn = new JButton("Delete");
        delbtn.setName("Delete");

        addbtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String text = JOptionPane.showInputDialog(instance, "Add a new item");
                String item = null;

                if (text != null) {
                    item = text.trim();
                } else {
                    return;
                }

                if (!item.isEmpty()) {
                    model.addElement(item);
                }
            }
        });

        delbtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                ListSelectionModel selmodel = list.getSelectionModel();
                int index = selmodel.getMinSelectionIndex();
                if (index >= 0) {
                    model.remove(index);
                }
            }

        });

        renbtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ListSelectionModel selmodel = list.getSelectionModel();
                int index = selmodel.getMinSelectionIndex();
                if (index == -1) {
                    return;
                }

                Object item = model.getElementAt(index);
                String text = JOptionPane.showInputDialog(instance, "Rename item", item);
                String newitem = null;

                if (text != null) {
                    newitem = text.trim();
                } else {
                    return;
                }

                if (!newitem.isEmpty()) {
                    model.remove(index);
                    model.add(index, newitem);
                }
            }
        });

        remallbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.clear();
            }
        });

    }

    private void initUI() {

        createList();
        createButtons();
        JScrollPane scrollpane = new JScrollPane(list);

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                        .addComponent(scrollpane)
                        .addGroup(gl.createParallelGroup()
                                .addComponent(addbtn)
                                .addComponent(renbtn)
                                .addComponent(delbtn)
                                .addComponent(remallbtn))
        );

        gl.setVerticalGroup(gl.createParallelGroup(CENTER)
                        .addComponent(scrollpane)
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(addbtn)
                                .addComponent(renbtn)
                                .addComponent(delbtn)
                                .addComponent(remallbtn))
        );

        gl.linkSize(addbtn, renbtn, delbtn, remallbtn);

        pack();

        setTitle("UISpec4j testing app");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public JList getList(){
        return list;
    }

    public JButton getDeleteButton(){
        return delbtn;
    }

    public JButton getRemoveAllButton(){
        return remallbtn;
    }

    public JButton getAddButton(){
        return addbtn;
    }

    public JButton getRenameButton() {
        return renbtn;
    }

}