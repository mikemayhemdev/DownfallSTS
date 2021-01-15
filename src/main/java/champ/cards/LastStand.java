package champ.cards;

import champ.ChampChar;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import java.util.ArrayList;

public class LastStand extends AbstractChampCard {

    public final static String ID = makeID("LastStand");

    //stupid intellij stuff skill, self, rare

    public LastStand() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("MONSTER_CHAMP_CHARGE"));
        atb(new ShoutAction(p, this.getLimitBreak(), 2.0F, 3.0F));
        atb(new VFXAction(p, new InflameEffect(p), 0.25F));
        atb(new VFXAction(p, new InflameEffect(p), 0.25F));
        atb(new VFXAction(p, new InflameEffect(p), 0.25F));
        atb(new RemoveDebuffsAction(p));
        applyToSelf(new StrengthPower(p, 6));
        finisher();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.currentHealth < p.maxHealth / 2) {
            return super.canUse(p, m);
        }
        cantUseMessage = ChampChar.characterStrings.TEXT[28];
        return false;
    }

    private String getLimitBreak() {
        ArrayList<String> derpy = new ArrayList<>();
        derpy.add(EXTENDED_DESCRIPTION[0]);
        derpy.add(EXTENDED_DESCRIPTION[1]);
        return derpy.get(AbstractDungeon.cardRandomRng.random(derpy.size() - 1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}