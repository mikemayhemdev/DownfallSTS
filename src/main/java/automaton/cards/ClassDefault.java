package automaton.cards;

import automaton.FunctionHelper;
import automaton.powers.DefaultPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class ClassDefault extends AbstractBronzeCard {

    public final static String ID = makeID("ClassDefault");

    //stupid intellij stuff power, self, uncommon

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public ClassDefault() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
       // this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DefaultPower(FunctionHelper.held.getBottomCard(), magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (FunctionHelper.isSequenceEmpty()) {
            cantUseMessage = masterUI.TEXT[3];
            return false;
        }
        return super.canUse(p, m);
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}