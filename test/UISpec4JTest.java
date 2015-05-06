import org.uispec4j.*;
import org.uispec4j.interception.MainClassAdapter;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

public class UISpec4JTest extends UISpecTestCase {

    private Window w;
    private ListBox listBox;

    protected void setUp() throws Exception {
        UISpec4J.init();
        setAdapter(new MainClassAdapter(Main.class));
        w = getMainWindow();
        listBox = w.getListBox();
    }

    public void testTitle() throws Exception {
        String title = w.getTitle();
        assertEquals(title, "UISpec4j testing app");
    }

    public void testList() throws Exception {
        assertTrue(listBox.contains("Miguel Callón"));
        assertTrue(listBox.contains("Santiago Iglesias"));
        assertTrue(listBox.contains("Nicolás Vila"));
    }

    public void testDelete() throws Exception {
        listBox.click(0);
        w.getButton("Delete").click();
        assertFalse(listBox.contains("Miguel Callón"));
        assertEquals(listBox.getSize(), 2);
    }

    public void testDeleteNoSelection() throws Exception {
        w.getButton("Delete").click();
        assertEquals(listBox.getSize(), 3);
    }

    public void testRemoveAll() throws Exception {
        w.getButton("Remove All").click();
        assertEquals(listBox.getSize(), 0);
    }

    public void testAdd() throws Exception {
        WindowInterceptor.init(w.getButton("Add").triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window dialog) {
                        dialog.getInputTextBox().setText("Paco");
                        return dialog.getButton("OK").triggerClick();
                    }
                })
                .run();
        assertEquals(listBox.getSize(), 4);
    }

    public void testAddEmpty() throws Exception {
        WindowInterceptor.init(w.getButton("Add").triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window dialog) {
                        return dialog.getButton("OK").triggerClick();
                    }
                })
                .run();
        assertEquals(listBox.getSize(), 3);
    }

    public void testRenameWithButton() throws Exception {
        listBox.click(0);
        WindowInterceptor.init(w.getButton("Rename").triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window dialog) {
                        dialog.getInputTextBox().setText("Paco Gonzalez");
                        return dialog.getButton("OK").triggerClick();
                    }
                })
                .run();
        assertTrue(listBox.contains("Paco Gonzalez"));
    }

    public void testRenameWithDoubleClick() throws Exception {
        WindowInterceptor.init(new Trigger() {
            public void run() throws Exception {
                listBox.doubleClick(0);
            }
        })
                .process(new WindowHandler() {
                    public Trigger process(Window dialog) {
                        dialog.getInputTextBox().setText("Pepe Pérez");
                        return dialog.getButton("OK").triggerClick();
                    }
                })
                .run();
        assertTrue(listBox.contains("Pepe Pérez"));
    }

    public void testRenameEmpty() throws Exception {
        listBox.click(0);
        WindowInterceptor.init(w.getButton("Rename").triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window dialog) {
                        return dialog.getButton("OK").triggerClick();
                    }
                })
                .run();
        assertTrue(listBox.contains("Miguel Callón"));
    }

}
