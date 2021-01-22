package automaton.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class DecaBeam extends AbstractBronzeCard {
    public final static String ID = makeID("DecaBeam");

    public DecaBeam() {
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseBlock = block = 16;
        thisEncodes();
        magicNumber = baseMagicNumber = 4;
        baseAuto = auto = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();

    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            applyToSelf(new PlatedArmorPower(AbstractDungeon.player, magicNumber));
            shuffleIn(new Dazed(), auto);
        }
    }


    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}
