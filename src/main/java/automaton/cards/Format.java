package automaton.cards;

import automaton.actions.AddToFuncAction;
import automaton.actions.EasyXCostAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Format extends AbstractBronzeCard {

    public final static String ID = makeID("Format");

    //stupid intellij stuff attack, all, rare

    private static final int DAMAGE = 6;

    private static final int BLOCK = 6;


    public Format() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        cardsToPreview = new FormatEncoded();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);

        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                AbstractCard c = new FormatEncoded();
                if (upgraded) c.upgrade();
                addToTop(new AddToFuncAction(c, null));
            }
            return true;
        }, magicNumber));
        if (upgraded) {
            AbstractCard c = new FormatEncoded();
            if (upgraded) c.upgrade();
            addToTop(new AddToFuncAction(c, null));
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}