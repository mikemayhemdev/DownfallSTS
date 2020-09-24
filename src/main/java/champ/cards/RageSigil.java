package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RageSigil extends AbstractChampCard {

    public final static String ID = makeID("RageSigil");

    //stupid intellij stuff skill, self, common

    public RageSigil() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.TECHNIQUE);
       // tags.add(ChampMod.OPENER);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
      //  berserkOpen();
        if (bcombo()) {
            this.exhaust = true;
            for (int i = 0; i < magicNumber; i++) {
                techique();
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
     //   rawDescription = UPGRADE_DESCRIPTION;
       // initializeDescription();
        upgradeMagicNumber(1);
    }
}