package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RageSigil extends AbstractChampCard {

    public final static String ID = makeID("RageSigil");

    //stupid intellij stuff skill, self, common

    public RageSigil() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(ChampMod.TECHNIQUE);
        // tags.add(ChampMod.OPENER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        atb(new DrawCardAction(1));
        if (upgraded) blck();
    }

    public void upp() {
        this.block = this.baseBlock = 3;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}