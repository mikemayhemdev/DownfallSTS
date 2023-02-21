package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;

import static champ.ChampMod.loadJokeCardImage;

public class SetATrap extends AbstractChampCard {
    public final static String ID = makeID("SetATrap");

    public SetATrap() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        baseBlock = block = 6;
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        postInit();
        loadJokeCardImage(this, "SetATrap.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (dcombo()) {
            for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
                if (!m2.isDead && !m2.isDying) {
                    applyToEnemy(m2, autoWeak(m2, magicNumber));
                }
            }
        }
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}