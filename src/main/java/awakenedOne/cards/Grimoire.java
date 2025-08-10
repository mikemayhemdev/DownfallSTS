package awakenedOne.cards;

import awakenedOne.cards.tokens.spells.Thunderbolt;
import awakenedOne.powers.AphoticFountPower;
import awakenedOne.powers.GrimoirePower;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.*;

public class Grimoire extends AbstractAwakenedCard {
    public final static String ID = makeID(Grimoire.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 9, 1, , , 3, 1

    public Grimoire() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        baseMagicNumber = magicNumber = 4;
        loadJokeCardImage(this, makeBetaCardPath(Grimoire.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.makeInHand(new Thunderbolt());
        applyToSelf(new GrimoirePower(magicNumber));

    }

    public void upp() {
        //upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}