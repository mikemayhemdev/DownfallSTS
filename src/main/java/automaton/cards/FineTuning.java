package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static automaton.AutomatonMod.makeBetaCardPath;

public class FineTuning extends AbstractBronzeCard {

    public final static String ID = makeID("FineTuning");

    //stupid intellij stuff skill, self, rare

    private static final int MAGIC = 1;

    public FineTuning() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("FineTuning.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard q : FunctionHelper.held.group) {
            if (q instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) q).fineTune(true);
            }
        }
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}