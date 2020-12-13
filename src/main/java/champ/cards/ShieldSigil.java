package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldSigil extends AbstractChampCard {

    public final static String ID = makeID("ShieldSigil");

    //stupid intellij stuff skill, self, common

    public ShieldSigil() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.TECHNIQUE);
        //tags.add(ChampMod.OPENER);
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
//        defenseOpen();
        if (dcombo()) {
            this.exhaust = true;
            for (int i = 0; i < magicNumber; i++) {
                techique();
            }
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