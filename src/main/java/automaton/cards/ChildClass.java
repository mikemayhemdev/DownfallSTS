package automaton.cards;

import automaton.cardmods.PlayMeTwiceCardmod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class ChildClass extends AbstractBronzeCard {

    public final static String ID = makeID("ChildClass");

    //stupid intellij stuff skill, self, uncommon

    public ChildClass() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        thisEncodes();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        CardModifierManager.addModifier(function, new PlayMeTwiceCardmod());
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}