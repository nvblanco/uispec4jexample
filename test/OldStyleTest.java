import junit.framework.TestCase;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class OldStyleTest extends TestCase {

    private MainFrame f;
    private JList list;

    protected void setUp() throws Exception {
        super.setUp();
        f = new MainFrame();
        f.setVisible(true);
        list = f.getList();
    }

    protected void tearDown() throws Exception {
        f.dispose();
        super.tearDown();
    }

    public void testTitle() throws Exception {
        String title = f.getTitle();
        assertEquals(title, "UISpec4j testing app");
    }

    public void testList() throws Exception {
        assertTrue(((DefaultListModel) list.getModel()).contains("Miguel Callón"));
        assertTrue(((DefaultListModel) list.getModel()).contains("Santiago Iglesias"));
        assertTrue(((DefaultListModel) list.getModel()).contains("Nicolás Vila"));
    }

    public void testDelete() throws Exception {
        int x = 0;
        int y = 0;
        int numberOfClicks = 1;
        list.dispatchEvent(new MouseEvent(list, 501, 1L, MouseEvent.MOUSE_CLICKED, x, y, numberOfClicks, false));
        JButton delbtn = f.getDeleteButton();
        delbtn.doClick();
        assertFalse(((DefaultListModel) list.getModel()).contains("Miguel Callón"));
    }

    public void testDeleteNoSelection() throws Exception {
        f.getDeleteButton().doClick();
        assertEquals(list.getModel().getSize(), 3);
    }

    public void testRemoveAll() throws Exception {
        f.getRemoveAllButton().doClick();
        assertEquals(list.getModel().getSize(), 0);
    }

    public void testAdd() throws InterruptedException {
        final JButton popup = f.getAddButton();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                popup.doClick();
            }
        });
        JOptionPane pane = null;
        while (pane == null) {
            pane = (JOptionPane) TestUtils.getChildIndexed(f, "JOptionPane", 0);
            Thread.sleep(50);
        }
        pane.setInputValue("Paco");
        pane.setValue(JOptionPane.OK_OPTION);
        Thread.sleep(200);
        assertEquals(list.getModel().getSize(), 4);
    }

    public void testRename() throws Exception {
        int x = 0;
        int y = 0;
        int numberOfClicks = 1;
        list.dispatchEvent(new MouseEvent(list, 501, 1L, MouseEvent.MOUSE_CLICKED, x, y, numberOfClicks, false));
        final JButton popup = f.getRenameButton();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                popup.doClick();
            }
        });
        JOptionPane pane = null;
        while (pane == null) {
            pane = (JOptionPane) TestUtils.getChildIndexed(f, "JOptionPane", 0);
            Thread.sleep(50);
        }
        pane.setInputValue("Paco");
        pane.setValue(JOptionPane.OK_OPTION);
        Thread.sleep(200);
        assertEquals(list.getModel().getSize(), 3);
        assertFalse(((DefaultListModel) list.getModel()).contains("Miguel Callón"));
        assertTrue(((DefaultListModel) list.getModel()).contains("Paco"));
    }
}
