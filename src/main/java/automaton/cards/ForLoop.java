package automaton.cards;

import automaton.FunctionHelper;
import automaton.cardmods.CardEffectsCardMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForLoop extends AbstractBronzeCard {

    public final static String ID = makeID("ForLoop");

    //stupid intellij stuff skill, self, uncommon

    public ForLoop() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        super.onCompile(function, forGameplay);
        if (!lastCard()) {
            AbstractCard q = FunctionHelper.held.group.get(this.position + 1);
            CardModifierManager.addModifier(function, new CardEffectsCardMod(q)); //TODO: This may have odd effects
        }
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}