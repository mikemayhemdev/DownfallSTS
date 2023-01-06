package downfall.util;

import com.megacrit.cardcrawl.helpers.Prefs;

public class IndividualBetaArtEnablerPref extends Prefs { //To make character boss cards' beta art individually togglable
    public IndividualBetaArtEnablerPref(Prefs parent) {
        this.data = parent.data;
        this.filepath = parent.filepath;
    }

    @Override
    public boolean getBoolean(String key,boolean def) {
        if (isBossCard(key)) {
            return super.getBoolean(key.substring(18),def );
        }
        return super.getBoolean(key,def);
    }

    public boolean isBossCard(String key) {
        return key.contains("downfall_Charboss:");
    }

}
