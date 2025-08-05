package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Procession extends AbstractAwakenedCard {
    public final static String ID = makeID(Procession.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Procession() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseSecondMagic = secondMagic = 3;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(Procession.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(AbstractDungeon.player, secondMagic));
        atb(new MakeTempCardInDrawPileAction(new VoidCard(), this.magicNumber, true, true));
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    public void upp() {
        upgradeSecondMagic(1);
    }
}