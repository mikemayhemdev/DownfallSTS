package slimebound.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.exordium.GoopPuddle;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.DigOption;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;
import com.megacrit.cardcrawl.ui.campfire.TokeOption;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.evacipated.cardcrawl.modthespire.lib.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.*;

import basemod.*;

import com.megacrit.cardcrawl.daily.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;
import slimebound.relics.ScrapOozeRelic;
import slimebound.ui.ScrapBonfireOption;

@SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
public class CampfirePatch {
    public static void Prefix(CampfireUI obj) {

        final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate((Object)obj, (Class)CampfireUI.class, "buttons");

        if (AbstractDungeon.player.hasRelic(ScrapOozeRelic.ID)) {
            campfireButtons.add(new ScrapBonfireOption(!AbstractDungeon.player.masterDeck.getPurgeableCards().isEmpty()));

            /*
            if (AbstractDungeon.player.hasRelic(PeacePipe.ID)){
                AbstractDungeon.player.relics.remove(AbstractDungeon.player.getRelic(PeacePipe.ID));
            }
            */

        }
    }
}


