package automaton.cards;

import automaton.AutomatonMod;
import automaton.cardmods.PlayMeTwiceCardmod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class Terminator extends AbstractBronzeCard {

    public final static String ID = makeID("Terminator");

    //stupid intellij stuff skill, self, common


    public Terminator() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        thisEncodes();
        //this.tags.add(SneckoMod.BANNEDFORSNECKO);
        tags.add(AutomatonMod.ADDS_NO_CARDTEXT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (lastCard()) {
            CardModifierManager.addModifier(function, new PlayMeTwiceCardmod());
        }
    }

    @Override
    public String getSpecialCompileText() {
        if (lastCard()) {
            return " - Function gains 'Play this again'.";
        }
        return "";
    }

    public void upp() {
       upgradeBaseCost(0);
    }
}