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

public class SetATrap extends AbstractChampCard {

    public final static String ID = makeID("SetATrap");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 2;
    // private static final int UPG_MAGIC = 3;

    public SetATrap() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        baseBlock = block = 6;
        baseMagicNumber = magicNumber = MAGIC;
     //   tags.add(ChampMod.OPENER);
       // this.tags.add(SneckoMod.BANNEDFORSNECKO);
     //   this.tags.add(ChampMod.OPENERDEFENSIVE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        blck();
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;

                for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
                    if (!m2.isDead && !m2.isDying) {
                        if (m2.hasPower(WeakPower.POWER_ID)) {
                            blck();
                        }
                    }

                }
            }
        });

        if (dcombo()) {

            for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
                if (!m2.isDead && !m2.isDying) {
                    applyToEnemy(m2, autoWeak(m, magicNumber));
                }

            }
        }
     //   defenseOpen();

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