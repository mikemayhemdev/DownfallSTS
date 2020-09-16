package champ.cards;

import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import champ.util.BerserkerTechniqueMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.EtherealMod;

public class Defend extends AbstractChampCard {

    public final static String ID = makeID("Defend");

    //stupid intellij stuff SKILL, SELF, BASIC

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = BLOCK;
        tags.add(CardTags.STARTER_DEFEND);
        CardModifierManager.addModifier(this, new BerserkerTechniqueMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        blck();
        berserkerStance();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}