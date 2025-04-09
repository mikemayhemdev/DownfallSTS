package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class DefensiveShout extends AbstractChampCard {

    public final static String ID = makeID("DefensiveShout");

    public DefensiveShout() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        //tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        // myHpLossCost = magicNumber;
        postInit();
        loadJokeCardImage(this, "DefensiveShout.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // techique();
        defenseOpen();
        applyToSelf(new CounterPower(magicNumber));

      //  AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
        //if (upgraded) techique();
    }



    public void upp() {
     //   tags.add(ChampMod.TECHNIQUE);
    //    postInit();
     //   initializeDescription();
        upgradeMagicNumber(4);
    }
}