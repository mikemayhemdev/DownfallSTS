package collector.cards.CollectorCards.Skills;

import collector.CollectorChar;
import collector.actions.AddAggroAction;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FelsteelAegis extends AbstractCollectorCard {
    public final static String ID = makeID("FelsteelAegis");

    public FelsteelAegis() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        exhaust = true;
        selfRetain = true;
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.getLivingTorchHead() != null) {
            atb(new AddAggroAction(magicNumber));
        }
        atb(new SelectCardsInHandAction(magicNumber, ExhaustAction.TEXT[0],true,true, card ->true, Cards->{
            if (Cards.size() > 0){
                atb(new ExhaustSpecificCardAction(Cards.get(0), AbstractDungeon.player.hand));
                AbstractCard copy = this.makeStatEquivalentCopy();
                atb(new MakeTempCardInDiscardAction(copy,1));
            }
        }));
    }
    @Override
    public void upp() {
        initializeDescription();
    }
}
