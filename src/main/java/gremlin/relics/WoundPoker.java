package gremlin.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class WoundPoker extends AbstractGremlinRelic {
    public static final String ID = getID("WoundPoker");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.UNCOMMON;
    private static final String IMG = "relics/wound_poker.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public static final int OOMPH = 6;

    public WoundPoker() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + OOMPH + strings.DESCRIPTIONS[1];
    }

    public void onPlayerEndTurn() {
        this.flash();
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                if (mo.hasPower(WeakPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(AbstractDungeon.player,
                            OOMPH, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
                }
            }
        }
    }
}
