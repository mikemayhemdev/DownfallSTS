package automaton.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class Goto extends AbstractBronzeCard {

    public final static String ID = makeID("Goto");

    //stupid intellij stuff skill, self, basic

    public Goto() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            applyToSelf(new DrawCardNextTurnPower(AbstractDungeon.player, magicNumber));
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}