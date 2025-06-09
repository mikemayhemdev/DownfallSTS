package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.actions.EchoACardAction;
import expansioncontent.expansionContentMod;

import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class DrawingDead extends AbstractAwakenedCard {
    public final static String ID = makeID(DrawingDead.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public DrawingDead() {
        super(ID, -2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
//        baseMagicNumber = magicNumber = 2;
//        baseSecondMagic = secondMagic = 1;
        tags.add(expansionContentMod.UNPLAYABLE);
        this.tags.add(AwakenedOneMod.DELVE);
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard card) {
        if (card.type == CardType.POWER && AbstractDungeon.player.hand.contains(this)) {
            this.flash();
            atb(new ConjureAction(false));
        }
    }


    @Override
    public void triggerWhenDrawn() {
        if(upgraded){
            this.flash();
            atb(new ConjureAction(false));
        }
    }


    public void upp() {
        //upgradeSecondMagic(1);
       // this.isEthereal = false;
    }
}