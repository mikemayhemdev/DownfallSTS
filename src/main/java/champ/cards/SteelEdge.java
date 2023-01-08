package champ.cards;

import automaton.actions.EasyXCostAction;
import champ.ChampChar;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import sneckomod.SneckoMod;

public class SteelEdge extends AbstractChampCard {

    public final static String ID = makeID("SteelEdge");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 8;
    private static final int BLOCK = 8;
    private int addBackVigor = 0;

    public SteelEdge() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
     //   this.tags.add(SneckoMod.BANNEDFORSNECKO);
        postInit();
    }

    public void applyPowers() {
        super.applyPowers();
        if(AbstractDungeon.player.hasPower(VigorPower.POWER_ID)){
            AbstractPower pr = AbstractDungeon.player.getPower( VigorPower.POWER_ID);
            this.addBackVigor = pr.amount;
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!(bcombo()) && !dcombo()) {
            this.cantUseMessage = ChampChar.characterStrings.TEXT[61];
            return false;
        }
        return super.canUse(p, m);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            if (bcombo()) {
                for (int i = 0; i < effect; i++) {
                    dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
                }
            }
            if (dcombo()) {
                for (int i = 0; i < effect; i++) {
                    blck();
                }
                if(!AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)){
                    if (this.addBackVigor>0) {
                        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, this.addBackVigor), this.addBackVigor));
                    }
                }
            }
            return true;
        }));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (bcombo() || dcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
        /*
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();

         */
    }
}