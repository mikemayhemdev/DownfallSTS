package downfall.relics;

import basemod.abstracts.CustomRelic;
import champ.relics.PowerArmor;
import collector.CollectorCollection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.function.Predicate;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Enchiridion;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import downfall.actions.WaitForEscapeAction;
import downfall.downfallMod;
import gremlin.characters.GremlinCharacter;

import java.util.ArrayList;
import java.util.List;

public class BurdenOfKnowledge extends CustomRelic {

    public static final String ID = downfallMod.makeID("BurdenOfKnowledge");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/Burden.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/Burden.png"));
    int effectCount = 0;

    public BurdenOfKnowledge() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.decreaseMaxHealth(20);
        List<String> upgradedCards = new ArrayList();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {
                ++effectCount;
                if (effectCount <= 20) {
                    float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                    float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                }

                upgradedCards.add(c.cardID);
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
            }
        }


        //collector compat
        CardGroup group = CardGroup.getGroupWithoutBottledCards(CollectorCollection.collection);
        for (AbstractCard c : group.group) {
            if (c.canUpgrade()) {
                ++effectCount;
                if (effectCount <= 20) {
                    float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                    float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                }
                upgradedCards.add(c.cardID);
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
            }
        }


        AbstractDungeon.player.loseRelic(HeartBlessingRed.ID);
        AbstractDungeon.player.loseRelic(HeartBlessingBlue.ID);
        AbstractDungeon.player.loseRelic(HeartBlessingGreen.ID);
    }

    public void onPreviewObtainCard(AbstractCard c) {
        this.onObtainCard(c);
    }

    public void onObtainCard(AbstractCard c) {
        if (!c.upgraded) {
            c.upgrade();
        }
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MasterRealityPower(AbstractDungeon.player)));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -2), -2));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -2), -2));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.hasRelic(Enchiridion.ID);
        {
            this.addToBot(new ArmamentsAction(true));
        }
    }

    public AbstractRelic makeCopy() {
        return new BurdenOfKnowledge();
    }
}
