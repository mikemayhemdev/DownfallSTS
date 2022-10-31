package collector.util.DuoUtils;

import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Iterator;

public class RelicHelper {
    static ArrayList<String> strikes = null;

    public static void removeStrikeTip(AbstractRelic relic) {
        if (strikes == null) {
            strikes = new ArrayList<>();
            for (String s : GameDictionary.STRIKE.NAMES) {
                strikes.add(s.toLowerCase());
            }
        }

        for (Iterator<PowerTip> it = relic.tips.iterator(); it.hasNext(); ) {
            String s = it.next().header.toLowerCase();
            if (strikes.contains(s)) {
                it.remove();
                break;
            }
        }
    }
}
