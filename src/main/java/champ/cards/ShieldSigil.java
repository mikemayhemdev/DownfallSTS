package champ.cards;

import champ.ChampMod;
import champ.stances.BerserkerStance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldSigil extends AbstractChampCard {

    public final static String ID = makeID("ShieldSigil");

    //stupid intellij stuff skill, self, common

    public ShieldSigil() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.TECHNIQUE);
        //tags.add(ChampMod.OPENER);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        techique();
//        defenseOpen();
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

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        // rawDescription = UPGRADE_DESCRIPTION;
        //  initializeDescription();
        upgradeMagicNumber(1);
    }
}