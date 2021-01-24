package automaton.cards;

import automaton.cardmods.FullReleaseCardMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FullRelease extends AbstractBronzeCard {

    public final static String ID = makeID("FullRelease");

    //stupid intellij stuff attack, enemy, rare


    public FullRelease() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onCompileFirst(AbstractCard function, boolean forGameplay) {
        CardModifierManager.addModifier(function, new FullReleaseCardMod());
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}