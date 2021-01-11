package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;

public class BerserkersShout extends AbstractChampCard {

    public final static String ID = makeID("BerserkersShout");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 3;

    public BerserkersShout() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERBERSERKER);
        myHpLossCost = magicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        berserkOpen();
        fatigue(magicNumber);
        if (upgraded) upgradeAction(p,m);
    }

    public void upgradeAction(AbstractPlayer p, AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}