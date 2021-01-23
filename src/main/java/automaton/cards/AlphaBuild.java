package automaton.cards;

import automaton.AutomatonMod;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static sneckomod.SneckoMod.getRandomStatus;

public class AlphaBuild extends AbstractBronzeCard {

    public final static String ID = makeID("AlphaBuild");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 13;
    private static final int UPG_DAMAGE = 3;

    public AlphaBuild() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        thisEncodes();
        //tags.add(AutomatonMod.BAD_COMPILE);
        cardsToPreview = new BetaBuild();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        shuffleIn(SneckoMod.getRandomStatus());
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            AbstractCard q = new BetaBuild();
            shuffleIn(q, 1);
        }
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}