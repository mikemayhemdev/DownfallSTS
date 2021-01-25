package automaton.cards;

import automaton.AutomatonMod;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

import static sneckomod.SneckoMod.getRandomStatus;

public class BetaBuild extends AbstractBronzeCard {

    public final static String ID = makeID("BetaBuild");

    //stupid intellij stuff attack, enemy, rare
    private static final int BLOCK = 13;

    public BetaBuild() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = 1;
        thisEncodes();
        cardsToPreview = new FullRelease();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        shuffleIn(SneckoMod.getRandomStatus());
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            AbstractCard q = new FullRelease();
            shuffleIn(q, 1);
        }
    }

    public void upp() {
        upgradeBlock(5);
    }
}