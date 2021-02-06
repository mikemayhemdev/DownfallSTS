package gremlin.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GremlinBomb extends AbstractGremlinRelic {
    public static final String ID = getID("GremlinBomb");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.BOSS;
    private static final String IMG = "relics/gremlin_bomb.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.SOLID;

    private static final int DMG_AMT = 40;

    public GremlinBomb() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + DMG_AMT + strings.DESCRIPTIONS[1];
    }

    @Override
    public void onGremlinDeath()
    {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
                DamageInfo.createDamageMatrix(DMG_AMT, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));    }
}

