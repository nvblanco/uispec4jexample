/*
 * TestUtils
 */
import java.awt.*;
import javax.swing.*;

public class TestUtils {

    static int counter;

    public static Component getChildNamed(Component parent, String name) {

        if (name.equals(parent.getName())) { return parent; }

        if (parent instanceof Container) {
            Component[] children = (parent instanceof JMenu) ?
                    ((JMenu)parent).getMenuComponents() :
                    ((Container)parent).getComponents();

            for (Component aChildren : children) {
                Component child = getChildNamed(aChildren, name);
                if (child != null) {
                    return child;
                }
            }
        }

        return null;
    }

    public static Component getChildIndexed(
            Component parent, String klass, int index) {
        counter = 0;

        // Step in only owned windows and ignore its components in JFrame
        if (parent instanceof Window) {
            Component[] children = ((Window)parent).getOwnedWindows();

            for (Component aChildren : children) {
                // take only active windows
                if (aChildren instanceof Window &&
                        !((Window) aChildren).isActive()) {
                    continue;
                }

                Component child = getChildIndexedInternal(
                        aChildren, klass, index);
                if (child != null) {
                    return child;
                }
            }
        }

        return null;
    }

    private static Component getChildIndexedInternal(
            Component parent, String klass, int index) {

        if (parent.getClass().toString().endsWith(klass)) {
            if (counter == index) { return parent; }
            ++counter;
        }

        if (parent instanceof Container) {
            Component[] children = (parent instanceof JMenu) ?
                    ((JMenu)parent).getMenuComponents() :
                    ((Container)parent).getComponents();

            for (Component aChildren : children) {
                Component child = getChildIndexedInternal(
                        aChildren, klass, index);
                if (child != null) {
                    return child;
                }
            }
        }

        return null;
    }
}
