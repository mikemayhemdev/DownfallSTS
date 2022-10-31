package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.GremlinMod;
import gremlin.cards.AbstractGremlinCard;

public class BangPower extends AbstractGremlinPower implements ConditionalModifyBlockPower{
    public static final String POWER_ID = getID("Bang");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/bang.png"));

    public BangPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1]);
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if(card instanceof AbstractGremlinCard){
            if(((AbstractGremlinCard) card).wizardry){
                // Does not consume stacks
                return;
            }
        }
        if (card.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, WizPower.POWER_ID));
        }
    }

    @Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage + this.amount;
        }
        return damage;
    }

    @Override
    public void stackPower(int stackAmount) {
        return;
    }

    @Override
    public float conditionalModifyBlock(float blockAmount, AbstractCard card) {
        if(card instanceof AbstractGremlinCard){
            if(((AbstractGremlinCard) card).sorcery){
                return blockAmount + this.amount;
            }
        }
        return blockAmount;
    }
}
