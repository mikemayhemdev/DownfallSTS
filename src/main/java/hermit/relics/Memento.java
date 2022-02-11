package hermit.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.HandOfGreed;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.PureWater;
import com.megacrit.cardcrawl.relics.QuestionCard;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hermit.HermitMod;
import hermit.cards.AbstractHermitCard;
import hermit.cards.MementoCard;
import hermit.util.TextureLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class Memento extends CustomRelic {

    // ID, images, text.
    public static final String ID = HermitMod.makeID("Momento");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("memento.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("memento.png"));

    private int TURNS = 0;

    public Memento() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
        this.tips.add(new CardPowerTip(new MementoCard()));
    }
    // Gain 1 Strength on on equip.

    @Override
    public void atBattleStart() {
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToTop(new MakeTempCardInHandAction(new MementoCard(), 1, false));
    }


    /*
    public void onObtainCard(AbstractCard card) {

        AbstractCard c;
        AbstractCard upgradeCard;
        ArrayList<AbstractCard> upgradableCards = new ArrayList();

        if (card.color == AbstractCard.CardColor.CURSE) {
            Iterator var9 = AbstractDungeon.player.masterDeck.group.iterator();

            while(var9.hasNext()) {
                c = (AbstractCard)var9.next();
                if (c.canUpgrade()) {
                    upgradableCards.add(c);
                }
            }

            Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
            if (!upgradableCards.isEmpty()) {
                upgradeCard = (AbstractCard)upgradableCards.get(0);
                upgradeCard.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradeCard);
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(upgradeCard.makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }

    }
    */

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
