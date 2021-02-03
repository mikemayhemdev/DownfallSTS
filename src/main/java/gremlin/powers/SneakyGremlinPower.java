package gremlin.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class SneakyGremlinPower extends GremlinPower {
    static final String POWER_ID = getID("SneakyGremlin");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public SneakyGremlinPower(int amount) {
        super();
        this.pot = amount;
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = (strings.DESCRIPTIONS[0] + this.pot + strings.DESCRIPTIONS[1]);
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.pot, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }
}
