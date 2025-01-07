package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static champ.ChampMod.loadJokeCardImage;

public class GladiatorStyle extends AbstractChampCard {
    public final static String ID = makeID("GladiatorStyle");

    public GladiatorStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(ChampMod.COMBOBERSERKER);
        postInit();
        loadJokeCardImage(this, "GladiatorStyle.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (dcombo()) {
            applyToSelf(new DexterityPower(p, magicNumber));
        }
        if (bcombo()){
            applyToSelf(new StrengthPower(p, magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (bcombo() || dcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}