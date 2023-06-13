package jovami.util.net;

import org.apache.commons.lang3.SystemUtils;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

/**
 * NetTools
 */
public final class NetTools {

    private static String openCmd() {
        var os = SystemUtils.OS_NAME.toLowerCase();

        if (os.contains("win"))
            return "rundll32 url.dll,FileProtocolHandler";
        else if (os.contains("mac"))
            return "open";
        else
            return "xdg-open";
    }

    public static void browseURL(URI uri) throws IOException {
        Desktop desktop;

        if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.BROWSE))
            desktop.browse(uri);
        else
            Runtime.getRuntime().exec(new String[] { openCmd(), uri.toString() });
    }
}
