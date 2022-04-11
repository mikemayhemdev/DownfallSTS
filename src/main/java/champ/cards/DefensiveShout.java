package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefensiveShout extends AbstractChampCard {

    public final static String ID = makeID("DefensiveShout");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 4;

    public DefensiveShout() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        //tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        // myHpLossCost = magicNumber;
        postInit();
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