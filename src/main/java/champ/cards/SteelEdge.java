package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class SteelEdge extends AbstractChampCard {

    public final static String ID = makeID("SteelEdge");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 9;
    private static final int BLOCK = 9;

    public SteelEdge() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = 1;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!(gcombo()) && !(bcombo()) && !dcombo()) {
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void applyPowers() {
        rawDescription = EXTENDED_DESCRIPTION[4];
        if (bcombo()) rawDescription += "[#5ebf2a]";
        else rawDescription += "*";
        rawDescription += EXTENDED_DESCRIPTION[0];
        if (dcombo()) rawDescription += "[#5ebf2a]";
        else rawDescription += "*";
        rawDescription += EXTENDED_DESCRIPTION[1];
        if (gcombo()) rawDescription += "[#5ebf2a]";
        else rawDescription += "*";
        rawDescription += upgraded ? EXTENDED_DESCRIPTION[3] : EXTENDED_DESCRIPTION[2];
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        rawDescription = upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (bcombo()) dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        if (dcombo()) blck();
        if (gcombo()) {
            atb(new DrawCardAction(magicNumber));
            atb(new GainEnergyAction(1));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (bcombo() || dcombo() || gcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}