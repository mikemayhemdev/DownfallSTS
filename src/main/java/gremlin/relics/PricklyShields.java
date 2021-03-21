package gremlin.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PricklyShields extends AbstractGremlinRelic {
    public static final String ID = getID("PricklyShields");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.RARE;
    private static final String IMG = "relics/prickly_shields.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.SOLID;

    private static final int DMG_AMT = 2;

    public PricklyShields() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + DMG_AMT + strings.DESCRIPTIONS[1];
    }

    @Override
    public int onPlayerGainedBlock(float blockAmount)
    {
        if(blockAmount > 0){

            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, DMG_AMT, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        return MathUtils.floor(blockAmount);
    }
}

