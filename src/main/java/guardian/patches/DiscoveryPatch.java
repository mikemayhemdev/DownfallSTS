/*
package guardian.patches;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import com.evacipated.cardcrawl.modthespire.lib.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import basemod.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import guardian.GuardianMod;
import guardian.cards.*;
import guardian.characters.GuardianCharacter;
import guardian.ui.EnhanceBonfireOption;


@SpirePatch(clz = CardRewardScreen.class, method = "discoveryOpen", paramtypez = {})
public class DiscoveryPatch {
    private static Method ref_placeCards;
    private static float CARD_TARGET_Y = (float)Settings.HEIGHT * 0.45F;
    private static final CardStrings cardStrings;
    private static String TEXT;

    @SpirePrefixPatch
    public static SpireReturn Prefix(CardRewardScreen obj) {
        GuardianMod.logger.info("Discovery patch hit");
        SingingBowlButton ref_bowlButton = null;
        SkipCardButton ref_skipButton = null;

        if (GuardianMod.discoveryOverride) {

            GuardianMod.logger.info("Discovery patch override hit");
            GuardianMod.discoveryOverride = false;
            try {
                ref_placeCards = CardRewardScreen.class.getDeclaredMethod("placeCards", CardRewardScreen.class, float.class, float.class);

                ref_bowlButton = (SingingBowlButton) ReflectionHacks.getPrivate(obj, CardRewardScreen.class, "bowlButton");
                ref_skipButton = (SkipCardButton) ReflectionHacks.getPrivate(obj, CardRewardScreen.class, "skipButton");

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            obj.rItem = null;
            ReflectionHacks.setPrivate(obj, CardRewardScreen.class, "codex", false);
            ReflectionHacks.setPrivate(obj, CardRewardScreen.class, "discovery", true);
            obj.discoveryCard = null;
            ReflectionHacks.setPrivate(obj, CardRewardScreen.class, "draft", false);
            obj.codexCard = null;

            ref_bowlButton.hide();
            ref_skipButton.show();
            obj.onCardSelect = true;
            AbstractDungeon.topPanel.unhoverHitboxes();


                AbstractDungeon.isScreenUp = true;
                AbstractDungeon.screen = AbstractDungeon.CurrentScreen.CARD_REWARD;
                AbstractDungeon.dynamicBanner.appear(TEXT);
                AbstractDungeon.overlayMenu.showBlackScreen();

                try {
                    ref_placeCards.invoke(obj, (float) Settings.WIDTH / 2.0F, CARD_TARGET_Y);


                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }



                Iterator var7 = obj.rewardGroup.iterator();

                while (var7.hasNext()) {
                    tmp = (AbstractCard) var7.next();
                    UnlockTracker.markCardAsSeen(tmp.cardID);
                }


                return SpireReturn.Return(null);
            }
        } else {
            return SpireReturn.Continue();
        }
        return SpireReturn.Continue();

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(CompilePackage.ID);
        TEXT = cardStrings.EXTENDED_DESCRIPTION[0];
    }
}


*/