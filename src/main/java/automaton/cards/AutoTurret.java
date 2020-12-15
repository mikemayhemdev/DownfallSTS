package automaton.cards;

import automaton.actions.EasyXCostAction;
import automaton.powers.AutoTurretPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.RefundFields;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AutoTurret extends AbstractBronzeCard {

    public final static String ID = makeID("AutoTurret");

    //stupid intellij stuff power, self, uncommon

    public AutoTurret() {
        super(ID, -1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new Peashooter();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (effect, params) -> {
            applyToSelfTop(new AutoTurretPower(effect));
            return true;
        }));
    }

    public void upp() {
        RefundFields.refund.set(this, 1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}