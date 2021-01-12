package champ.cards;

import champ.ChampMod;
import champ.stances.BerserkerStance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SwordSigil extends AbstractChampCard {

    public final static String ID = makeID("SwordSigil");

    //stupid intellij stuff skill, self, common

    public SwordSigil() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(ChampMod.TECHNIQUE);
        //  tags.add(ChampMod.OPENER);
        baseMagicNumber = magicNumber = 2;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //gladOpen();
        techique();
        for (int i = 0; i < magicNumber; i++) {
            techique();
        }
    }


    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID)) {
            this.myHpLossCost = BerserkerStance.amount() * magicNumber;
        } else {
            this.myHpLossCost = 0;
        }
    }


    public void upp() {
        // rawDescription = UPGRADE_DESCRIPTION;
        // initializeDescription();
        upgradeMagicNumber(2);
    }
}