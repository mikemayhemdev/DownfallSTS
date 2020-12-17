package automaton.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChosenStrike extends AbstractBronzeCard {

    public final static String ID = makeID("ChosenStrike");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 3;

    public ChosenStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        thisEncodes();
        cardsToPreview = new Dazed();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        super.onCompile(function, forGameplay);
        if (function.cost > 0) {
            function.cost -= 1;
            function.costForTurn -= 1;
        }
        if (forGameplay) {
            if (upgraded) {
                atb(new MakeTempCardInDiscardAction(new Dazed(), 2));
            }
            else {
                shuffleIn(new Dazed(), 2);
            }
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}