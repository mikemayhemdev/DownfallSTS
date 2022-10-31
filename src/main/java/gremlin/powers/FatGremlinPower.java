package gremlin.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.HashSet;
import java.util.Set;

public class FatGremlinPower extends GremlinPower {
    static final String POWER_ID = getID("FatGremlin");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private Set<AbstractCreature> alreadyHit;

    public FatGremlinPower(int amount) {
        super();
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.pot = amount;
        this.updateDescription();
        this.alreadyHit = new HashSet<>();
    }

    public void updateDescription()
    {
        this.description = (strings.DESCRIPTIONS[0] + this.pot + strings.DESCRIPTIONS[1]);
    }

    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        this.alreadyHit = new HashSet<>(); // New card. Reset limits.
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(target.equals(AbstractDungeon.player)){
            return;
        }
        if(alreadyHit.contains(target)){
            return;
        }
        if(info.type == DamageInfo.DamageType.NORMAL){
            alreadyHit.add(target);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, owner,
                    new WeakPower(target, this.pot, false), this.pot));
        }
    }
}
