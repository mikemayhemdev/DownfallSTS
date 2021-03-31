package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import gremlin.GremlinMod;
import gremlin.cards.AbstractGremlinCard;

public class MakeshiftArmorPower extends AbstractGremlinPower {
    public static final String POWER_ID = getID("MakeshiftArmor");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/makeshift_armor.png"));

    private int artifact;

    public MakeshiftArmorPower(AbstractCreature owner, int artifact) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = 7;
        this.artifact = artifact;
        this.updateDescription();
    }

    public void updateDescription()
    {
        if(this.amount == 1) {
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1] + this.artifact);
        } else{
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[2] + this.artifact);
        }
        this.description += strings.DESCRIPTIONS[3];
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.amount -= 1;
            if (this.amount == 0) {
                flash();
                this.amount = 7;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
                        new ArtifactPower(this.owner, this.artifact), this.artifact));
            }
            updateDescription();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.artifact += stackAmount;
        updateDescription();
    }
}
