package automaton.cards;

import automaton.actions.AddToFuncAction;
import automaton.actions.EasyXCostAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Format extends AbstractBronzeCard {

    public final static String ID = makeID("Format");

    //stupid intellij stuff attack, all, rare

    private static final int DAMAGE = 6;

    private static final int BLOCK = 6;


    public Format() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        cardsToPreview = new FormatEncoded();
        exhaust = true;
        baseMagicNumber = magicNumber = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //  addToBot(new GainBlockAction(p, block));
        //  dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);

        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect + params[0]; i++) {
                AbstractCard c = new FormatEncoded();
                addToTop(new AddToFuncAction(c, null));
            }
            return true;
        }, magicNumber));
        atb(new GainEnergyAction(1));
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}