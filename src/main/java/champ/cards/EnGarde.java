package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnGarde extends AbstractChampCard {

    public final static String ID = makeID("EnGarde");

    //stupid intellij stuff skill, self, common

    private static final int MAGIC = 3;

    public EnGarde() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        if (upgraded) applyToSelf(new CounterPower(magicNumber));
        if (dcombo()) atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (p.hasPower(CounterPower.POWER_ID)) {
                    int x = p.getPower(CounterPower.POWER_ID).amount;
                    att(new ApplyPowerAction(p, p, new CounterPower(x), x));
                }
            }
        });
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {

        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}