package champ.cards;

import champ.powers.LastStandPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static champ.ChampMod.loadJokeCardImage;

import java.util.ArrayList;
import java.util.Arrays;

public class LastStand extends AbstractChampCard {
    public final static String ID = makeID("LastStand");

    public LastStand() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 6;
        loadJokeCardImage(this, "LastStand.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;
                if (p.currentHealth * 2 < p.maxHealth) {
                    addToTop(new SFXAction("MONSTER_CHAMP_CHARGE"));
                    addToTop(new ShoutAction(p, getLimitBreak(), 2.0F, 3.0F));
                    addToTop(new VFXAction(p, new InflameEffect(p), 0.25F));
                    addToTop(new VFXAction(p, new InflameEffect(p), 0.25F));
                    addToTop(new VFXAction(p, new InflameEffect(p), 0.25F));
                    addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
                    addToTop(new RemoveDebuffsAction(p));
                } else {
                    addToTop(new ApplyPowerAction(p, p, new LastStandPower(magicNumber), magicNumber));
                }
            }
        });
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractDungeon.player.currentHealth * 2 < AbstractDungeon.player.maxHealth ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public String getLimitBreak() {
        ArrayList<String> derpy = new ArrayList<>(Arrays.asList(EXTENDED_DESCRIPTION));
        if (!derpy.isEmpty())
            return derpy.get(AbstractDungeon.cardRandomRng.random(derpy.size() - 1));
        return "ERROR";
    }

    public void upp() {
        upgradeMagicNumber(3);
        //upgradeBaseCost(0);
    }
}