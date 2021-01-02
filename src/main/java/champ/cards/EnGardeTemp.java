package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class EnGardeTemp extends AbstractChampCard {

    public final static String ID = makeID("EnGarde");

    //stupid intellij stuff skill, self, common

    private static final int MAGIC = 8;

    public EnGardeTemp() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
      //  tags.add(ChampMod.COMBO);
      //  tags.add(ChampMod.COMBODEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        blck();

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {

        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}