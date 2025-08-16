package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static champ.ChampMod.loadJokeCardImage;

public class CalledShot extends AbstractChampCard {

    public final static String ID = makeID("CalledShot");

    //stupid intellij stuff power, self, uncommon

    public CalledShot() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(ChampMod.COMBOBERSERKER);
        tags.add(CardTags.HEALING);
        postInit();
        loadJokeCardImage(this, "CalledShot.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (bcombo()){
            this.exhaust = true;
            if (upgraded) {
                atb(new GainEnergyAction(3));
            } else {
                atb(new GainEnergyAction(2));
            }
        }
        if (dcombo()){
            atb(new DrawCardAction(magicNumber));
        }
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = (dcombo() || bcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        upgradeMagicNumber(1);
        initializeDescription();
        tags.add(SneckoMod.BANNEDFORSNECKO);

    }
}
