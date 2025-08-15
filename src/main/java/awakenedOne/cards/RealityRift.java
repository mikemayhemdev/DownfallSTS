package awakenedOne.cards;

import awakenedOne.actions.GetDimensionCardsAction;
import awakenedOne.cards.altDimension.*;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.*;

@NoPools
public class RealityRift extends AbstractAwakenedCard {
    public final static String ID = makeID(RealityRift.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public RealityRift() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CardColor.COLORLESS);
        loadJokeCardImage(this, makeBetaCardPath(RealityRift.class.getSimpleName() + ".png"));
        exhaust = true;
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        Wiz.atb(new GetDimensionCardsAction(magicNumber, secondMagic));
        addToBot(new MakeTempCardInDiscardAction(new VoidCard(), 2));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;

                for (AbstractCard c:AbstractDungeon.player.hand.group){
                    if (c instanceof Crusher){
                        ((Crusher) c).enabled = true;
                        return;
                    }
                }
            }
        });
    }


    public void upp() {
        upgradeMagicNumber(2);
       // upgradeSecondMagic(1);
    }
}