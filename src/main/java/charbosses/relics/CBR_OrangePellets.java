package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.OrangePellets;
import com.megacrit.cardcrawl.relics.Strawberry;

public class CBR_OrangePellets extends AbstractCharbossRelic {
    public static final String ID = "OrangePellets";
    private static boolean SKILL = false;
    private static boolean POWER = false;
    private static boolean ATTACK = false;

    public CBR_OrangePellets() {
        super(new OrangePellets());

    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(7, true);
    }


    public void atTurnStart() {
        SKILL = false;
        POWER = false;
        ATTACK = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ATTACK = true;
        } else if (card.type == AbstractCard.CardType.SKILL) {
            SKILL = true;
        } else if (card.type == AbstractCard.CardType.POWER) {
            POWER = true;
        }

        if (ATTACK && SKILL && POWER) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(this.owner, this));
            this.addToBot(new RemoveDebuffsAction(this.owner));
            SKILL = false;
            POWER = false;
            ATTACK = false;
        }

    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_OrangePellets();
    }
}