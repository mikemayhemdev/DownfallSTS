package champ.cards;

import champ.ChampMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class ViciousMockery extends AbstractChampCard {

    public final static String ID = makeID("ViciousMockery");

    //stupid intellij stuff skill, enemy, uncommon

    public ViciousMockery() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
        tags.add(ChampMod.COMBODEFENSIVE);
    }

    @Override
    public void applyPowers() {
        rawDescription = EXTENDED_DESCRIPTION[0];
        if (bcombo()) rawDescription += "[#5ebf2a]";
        else rawDescription += "*";
        rawDescription += EXTENDED_DESCRIPTION[1];
        if (dcombo()) rawDescription += "[#5ebf2a]";
        else rawDescription += "*";
        if (upgraded) {
            rawDescription += EXTENDED_DESCRIPTION[3];
        } else {
            rawDescription += EXTENDED_DESCRIPTION[2];
        }
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("VO_CHAMP_2A"));
        atb(new TalkAction(true, getTaunt(), 2.0F, 2.0F));
        if (dcombo()) {
            applyToSelf(new DexterityPower(p, magicNumber));
            applyToSelf(new LoseDexterityPower(p, magicNumber));
        }
        if (bcombo()) {
            applyToSelf(new StrengthPower(p, magicNumber));
            applyToSelf(new LoseStrengthPower(p, magicNumber));
        }
    }

    private String getTaunt() {
        ArrayList<String> derp = new ArrayList<>();
        derp.add(Champ.DIALOG[0]);
        derp.add(Champ.DIALOG[1]);
        derp.add(Champ.DIALOG[2]);
        derp.add(Champ.DIALOG[3]);
        return derp.get(MathUtils.random(derp.size() - 1));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = ((bcombo() || dcombo())) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }


    public void upp() {
        upgradeMagicNumber(2);
    }
}