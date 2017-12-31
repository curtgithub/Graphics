package JmeOrbit;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * test
 *
 * @author normenhansen
 */
public class JOrbit extends SimpleApplication {

    public static final SimpleDateFormat SDF_UTC
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    public static final SimpleDateFormat SDF_EST5EDT
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    private final Date startDate;

    private String simTimeString;
    private BitmapText simTimeBitmapText;
    private Date utcDate = null;
    private String utcDateString;
    private BitmapText utcDateBitmapText;
    private String localDateString;
    private BitmapText localDateBitmapText;

    public JOrbit() {
        SDF_UTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        SDF_EST5EDT.setTimeZone(TimeZone.getTimeZone("EST5EDT"));

        this.startDate = new Date(System.currentTimeMillis());

        this.utcDate = new Date(this.startDate.getTime());

        this.simTimeString = null;
        this.simTimeBitmapText = null;

        this.utcDateString = null;
        this.utcDateBitmapText = null;

        this.localDateString = null;
        this.localDateBitmapText = null;
        for (String s : SimpleTimeZone.getAvailableIDs()) {
            System.out.println("TimeZoneID = " + s);
        }
    }

    private void updateCurrentTime() {


        /*
         double secs = (this.currTime - this.startTime) / 1000d;
         DecimalFormat myFormatter = new DecimalFormat("###,###.0");
         this.simTimeString = myFormatter.format(secs);
         this.simTimeBitmapText.setText(this.simTimeString);
         */
        this.utcDate.setTime(System.currentTimeMillis());
        this.utcDateString = SDF_UTC.format(this.utcDate);
        this.utcDateBitmapText.setText(this.utcDateString);

        double secs = (this.utcDate.getTime() - this.startDate.getTime()) / 1000d;
        DecimalFormat myFormatter = new DecimalFormat("###,###.0");
        this.simTimeString = myFormatter.format(secs);
        this.simTimeBitmapText.setText(this.simTimeString);

        this.localDateString = SDF_EST5EDT.format(this.utcDate);
        this.localDateBitmapText.setText(this.localDateString);
    }

    public static void main(String[] args) {
        JOrbit app = new JOrbit();
        app.start();
    }

    @Override
    public void simpleInitApp() {

//        BitmapFont fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        this.simTimeBitmapText = new BitmapText(guiFont, false);
        simTimeBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        simTimeBitmapText.setColor(ColorRGBA.Magenta);
        simTimeBitmapText.setText("000000000000.0");
        simTimeBitmapText.setBox(new Rectangle(settings.getWidth() - simTimeBitmapText.getLineWidth(),
                settings.getHeight(),
                simTimeBitmapText.getLineWidth(), simTimeBitmapText.getLineHeight()));
        simTimeBitmapText.setAlignment(BitmapFont.Align.Right);
        simTimeBitmapText.setLocalTranslation(0, 0, 0);
        guiNode.attachChild(simTimeBitmapText);

        this.utcDateBitmapText = new BitmapText(guiFont, false);
        utcDateBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        utcDateBitmapText.setColor(ColorRGBA.Orange);
        utcDateBitmapText.setText("yyyy-MM-dd HH:mm:ss zzz");
        utcDateBitmapText.setBox(new Rectangle(settings.getWidth() - utcDateBitmapText.getLineWidth(),
                settings.getHeight(),
                utcDateBitmapText.getLineWidth(), utcDateBitmapText.getLineHeight()));
        utcDateBitmapText.setAlignment(BitmapFont.Align.Right);
        utcDateBitmapText.setLocalTranslation(0, -simTimeBitmapText.getLineHeight(), 0);
        guiNode.attachChild(utcDateBitmapText);

        this.localDateBitmapText = new BitmapText(guiFont, false);
        localDateBitmapText.setSize(guiFont.getPreferredSize() * 1f);
        localDateBitmapText.setColor(ColorRGBA.Orange);
        localDateBitmapText.setText("yyyy-MM-dd HH:mm:ss zzz");
        localDateBitmapText.setBox(new Rectangle(settings.getWidth() - localDateBitmapText.getLineWidth(),
                settings.getHeight(),
                localDateBitmapText.getLineWidth(), localDateBitmapText.getLineHeight()));
        localDateBitmapText.setAlignment(BitmapFont.Align.Right);
        localDateBitmapText.setLocalTranslation(0,
                -(this.simTimeBitmapText.getLineHeight() + this.localDateBitmapText.getLineHeight()), 0);
        guiNode.attachChild(localDateBitmapText);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        //System.out.println("tpf = " + tpf);
        //System.out.println("System.currentTimeMillis = " + System.currentTimeMillis());
        this.updateCurrentTime();

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
