package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import sneckomod.SneckoMod;

public class ViciousMockery extends AbstractChampCard {

    public final static String ID = makeID("ViciousMockery");

    //stupid intellij stuff skill, enemy, uncommon

    public ViciousMockery() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
       // tags.add(ChampMod.TECHNIQUE);
        baseMagicNumber = magicNumber = 1;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
        tags.add(ChampMod.COMBODEFENSIVE);
        exhaust = true;
    }

    @Override
    public void applyPowers() {
        rawDescription = EXTENDED_DESCRIPTION[0];;
        if (bcombo()) rawDescription += "[#5ebf2a]";
        else rawDescription += "*";
        rawDescription += EXTENDED_DESCRIPTION[1];
        if (dcombo()) rawDescription += "[#5ebf2a]";
        else rawDescription += "*";
        if (upgraded) {
            rawDescription += EXTENDED_DESCRIPTION[3];
        }else {
            rawDescription += EXTENDED_DESCRIPTION[2];
        }
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //techique();
        {
            for (AbstractPower p2:m.powers){
                if (p2.type == AbstractPower.PowerType.DEBUFF){
                    if (dcombo()) {
                        applyToSelf(new DexterityPower(p, magicNumber));
                    }
                    if (bcombo()) {
                        applyToSelf(new StrengthPower(p, magicNumber));
                    }
                    break;
                }
            }

        }
        exhaust = true;
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = (bcombo() || dcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }



    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}