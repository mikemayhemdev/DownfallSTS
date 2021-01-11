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

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 4;

    public DefensiveShout() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERDEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        defenseOpen();
        applyToSelf(new CounterPower(magicNumber));
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